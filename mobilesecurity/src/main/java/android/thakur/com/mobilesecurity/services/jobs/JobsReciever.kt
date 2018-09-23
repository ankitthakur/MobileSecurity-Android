@file:Suppress("NAME_SHADOWING")

package android.thakur.com.mobilesecurity.services.jobs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TYPE

internal class JobsReciever:BroadcastReceiver() {

    private lateinit var logger:Logger

    override fun onReceive(context: Context?, intent: Intent?) {
        logger = Logger(context!!)
        logger.log("recieved broadcast")
        val intent = Intent(context, BackgroundService::class.java)
        intent.putExtra("eventType", EVENT_TYPE.REBOOTED.value)
        // this will call BackgroundService onHandle fun
         context.startService(intent)
    }
}