package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.ControlPanelCard
import components.SensorsReadingCard
import viewmodels.SrmViewModel

@Preview()
@Composable
fun ProfileScreen(viewModel: SrmViewModel = remember { SrmViewModel() }) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        ControlPanelCard(viewModel)
        Spacer(modifier = Modifier.width(8.dp))
        SensorsReadingCard(viewModel)

    }
}