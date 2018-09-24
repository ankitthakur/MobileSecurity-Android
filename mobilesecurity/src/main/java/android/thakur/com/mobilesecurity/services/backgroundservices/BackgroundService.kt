package android.thakur.com.mobilesecurity.services.backgroundservices

import android.app.IntentService
import android.content.Intent
import android.thakur.com.mobilesecurity.database.helper.MobileSecurityDatabase
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.Services
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

internal enum class EVENT_TRIGGER{
    BACKGROUND, FOREGROUND
}

internal enum class EVENT_TYPE(val value: Int){
    SCHEDULED(4001),
    NETWORK_CHANGE(4002),
    LOCATION_CHANGE(4003),
    LOCATION_VISIT(4004),
    REBOOTED(4005)
}
internal class BackgroundService:IntentService("BackgroundService") {

    val logger = Logger()

    override fun onCreate() {
        logger.log("background service created")
        super.onCreate()
    }

    override fun onDestroy() {
        logger.log("background service destroyed")
        super.onDestroy()
    }

    override fun onHandleIntent(intent: Intent?) {

        val event:Int = intent!!.getIntExtra("eventType", -1)

        when (event){
            EVENT_TYPE.SCHEDULED.value ->
                startScheduled()
            EVENT_TYPE.NETWORK_CHANGE.value ->
                startNetworkChange()


        }
    }



    fun startScheduled(){
        logger.log("start scheduled event")
        Services.sharedInstance.startJob(null, null)
    }

    fun startNetworkChange(){
        logger.log("start network change event ")
        Services.sharedInstance.startJob(null, null)

        runBlocking(block = {
            launch(Dispatchers.Default) {
                val database = MobileSecurityDatabase.getInstance(this@BackgroundService)
                System.out.println("logs: " + database.logDao().logs.size)
            }
        })
    }
}