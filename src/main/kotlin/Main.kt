import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.ControlPanelCard
import components.HRMSensorReadingCard
import components.SensorsReadingCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.coroutineContext
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import screens.ProfileScreen
import screens.SettingsScreen
import screens.TcpClientScreen
import services.TcpClientService
import viewmodels.TcpViewModel

// Define our screens
enum class Screen {
    Home,
    HRM,
    SRM,
    TCP
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    MaterialTheme {
        Column(modifier = Modifier.background(Color(0xFF3E605B)).fillMaxSize()) {
            // Navigation bar at the top
            NavigationBar(
                currentScreen = currentScreen,
                onScreenSelect = { screen -> currentScreen = screen }
            )

            // Content area
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                when (currentScreen) {
                    Screen.Home -> HomeScreen()
                    Screen.HRM -> {
                        SettingsScreen()
                    }
                    Screen.SRM -> ProfileScreen()
                    Screen.TCP -> TcpClientScreen()
                }
            }
        }
    }
}

@Composable
fun NavigationBar(
    currentScreen: Screen,
    onScreenSelect: (Screen) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Screen.values().forEach { screen ->
            Button(
                onClick = { onScreenSelect(screen) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (currentScreen == screen)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Text(screen.name)
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var settingHost by remember { mutableStateOf(host) }
    var settingPort by remember { mutableStateOf(port) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            shape = MaterialTheme.shapes.medium,
            value = settingHost,
            onValueChange = { settingHost = it; },
            label = { Text("Host") },
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = settingPort,
            shape = MaterialTheme.shapes.medium,
            onValueChange = {
                settingPort = it;
            },
            label = { Text("Port") },
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            shape = MaterialTheme.shapes.medium,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(contentColor = Color.White,
                containerColor = Color(0xFF2E8B8B)
            ),
            onClick = {
                host = settingHost
                port = settingPort
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Connection Settings Saved")
                }
            }
        ) {
            Text(
                fontSize = 16.sp,
                color = Color.White,
                text = "Set Connection Settings"
            )
        }


    }
    Row(){
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.fillMaxWidth(),

            )
    }

}


fun main() = application {
    val logFile = File("application.log")
    System.setOut(PrintStream(FileOutputStream(logFile, true)))

    startKoin() {
        modules(desktopModules)
    }
    Window(
        onCloseRequest = {
            System.out.flush()
            System.setOut(System.out)
            exitApplication()
        },
        title = "Multi-Page Compose App"
    ) {
    App()
    }
}


var host = "localhost";
var port = "8080";