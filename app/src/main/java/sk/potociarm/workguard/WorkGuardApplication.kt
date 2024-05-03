package sk.potociarm.workguard

import android.app.Application
import sk.potociarm.workguard.data.AppContainer
import sk.potociarm.workguard.data.AppDataContainer

class WorkGuardApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
