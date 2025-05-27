import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import components.ControlPanelCard
import components.HRMSensorReadingCard
import components.SensorsReadingCard
import screens.TcpClientScreen

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
                    Screen.HRM -> SettingsScreen()
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen")
        Text("Welcome to the application!")
    }
}

@Composable
fun SettingsScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        ControlPanelCard()
        Spacer(modifier = Modifier.width(8.dp))
        HRMSensorReadingCard()

    }
}

@Preview()
@Composable
fun ProfileScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        ControlPanelCard()
        Spacer(modifier = Modifier.width(8.dp))
        SensorsReadingCard()

    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Multi-Page Compose App",

    ) {
        App()
    }
}