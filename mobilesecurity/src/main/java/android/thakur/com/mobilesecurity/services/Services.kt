package android.thakur.com.mobilesecurity.services

import android.app.Activity
import android.content.Context
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.model.UserLocation
import android.thakur.com.mobilesecurity.services.jobs.ScheduleJob
import android.thakur.com.mobilesecurity.submodules.deviceInfo.DeviceInfo
import android.thakur.com.mobilesecurity.submodules.location.UserLocationManager
import java.util.*

internal enum class MODULE(val value: Int){
    LOCATION(5001),
    DEVICE_INFO(5002)
}
internal class Services private constructor(){

    init {
        println("Singleton class invoked.")
    }

    internal lateinit var appContext: Context
    internal lateinit var appActivity: Activity
    private var startTime: Date? = null
    private var endTime: Date? = null
    private var lockDuration = 15*60*60*1000
    private lateinit var logger: Logger

    private lateinit var scheduleJob: ScheduleJob

    private var userLocationManager:UserLocationManager? = null

    fun scheduleJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appActivity = activity

        logger = Logger(this.appContext)

        scheduleJob = ScheduleJob()
        scheduleJob.scheduleJob(this.appContext, this.appActivity)
    }


    fun startJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appContext = activity

        if (this.startTime != null){
            val difference = Date().time - startTime!!.time

            if (difference > 10*60*60*1000){
                logger.log("new trigger is happening after 10 mins")
                startTime = Date()
                userLocationManager = null
                userLocationManager = UserLocationManager()
                userLocationManager!!.initialize(this.appActivity, this.appContext)
                userLocationManager!!.startServices()
            }
            else{
                logger.log("older trigger is still processing")
            }
        }
        else{
            logger.log("new trigger is happening after 10 mins")
            logger.log("first capture")
            startTime = Date()
            userLocationManager = null
            userLocationManager = UserLocationManager()
            userLocationManager!!.initialize(this.appActivity, this.appContext)
            userLocationManager!!.startServices()
        }

    }

    fun endJob(module: MODULE, value:Any){

        if (module == MODULE.LOCATION){
            var userLocation = value as UserLocation
            var deviceInfo = DeviceInfo().details

            endTime = Date()

            logger.log("process is completed")
            this.scheduleJob(this.appContext, this.appActivity)
        }
    }

    private object Holder {
        val instance = Services()
    }

    companion object {
        val sharedInstance: Services by lazy { Holder.instance }
    }


}