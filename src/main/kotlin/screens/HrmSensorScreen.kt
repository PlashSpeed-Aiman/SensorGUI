package screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.ControlPanelCard
import components.HRMSensorReadingCard
import org.koin.mp.KoinPlatform.getKoin
import viewmodels.HrmViewModel

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
}