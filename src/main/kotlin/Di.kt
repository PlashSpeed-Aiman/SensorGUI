import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import viewmodels.SensorViewModel

val desktopModules : Module = module {
    single { SensorViewModel() }
}
