import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import viewmodels.HrmViewModel
import viewmodels.SensorViewModel
import viewmodels.SrmViewModel
import viewmodels.TcpViewModel

val desktopModules : Module = module {
    single { SensorViewModel() }
    single { HrmViewModel() }
    single { SrmViewModel() }
}
