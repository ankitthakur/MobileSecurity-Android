package android.thakur.com.mobilesecurity.services

import android.app.Activity
import android.content.Context
import android.os.Build
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.jobs.ScheduleJob
import java.util.*

internal class Services private constructor(){

    init {
        println("Singleton class invoked.")
    }

    private lateinit var appContext: Context
    private lateinit var appActivity: Activity
    private lateinit var startTime: Date
    private lateinit var endTime: Date
    private var lockDuration = 15*60*60*1000
    private lateinit var logger: Logger

    private lateinit var scheduleJob: ScheduleJob

    fun scheduleJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appContext = activity

        logger = Logger(appContext)

        scheduleJob = ScheduleJob()
        scheduleJob.scheduleJob(this.appContext, this.appActivity)
    }


    fun startJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appContext = activity

        if (this.startTime != null){
            val difference = Date().time - startTime.time

            if (difference > 10*60*60*1000){
                logger.log("new trigger is happening after 10 mins")
                startTime = Date()

                val board = Build.BOARD
                val brand = Build.BRAND
                val device = Build.DEVICE
                val display = Build.DISPLAY
                val product = Build.PRODUCT
            }
            else{
                logger.log("older trigger is still processing")
            }
        }

    }

    private object Holder {
        val instance = Services()
    }

    companion object {
        val sharedInstance: Services by lazy { Holder.instance }
    }


}