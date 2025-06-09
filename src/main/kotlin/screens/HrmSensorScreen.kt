package screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.ControlPanelCard
import components.HRMSensorReadingCard
import org.koin.mp.KoinPlatform.getKoin
import viewmodels.HrmViewModel
import viewmodels.SrmViewModel

@Composable
fun SettingsScreen(viewModel: HrmViewModel = remember { HrmViewModel() }) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly

        ){
        ControlPanelCard(viewModel)
        Spacer(modifier = Modifier.width(8.dp))
        HRMSensorReadingCard(viewModel)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.disconnect()
        }
    }
//    println(
//        "ProfileScreen: ${getKoin().getScope("SRM").get<SrmViewModel>().connectionStatus}"
//    )
}