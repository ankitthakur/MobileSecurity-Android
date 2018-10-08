@file:Suppress("NAME_SHADOWING")

package android.thakur.com.mobilesecurity.services.jobs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.MSServices

internal class JobsReciever:BroadcastReceiver() {

    private lateinit var logger:Logger

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            logger = Logger(context!!)
            logger.log("recieved broadcast")
            // this will call BackgroundService onHandle fun
            MSServices.sharedInstance.startJob(context, null)
        }

    }
}