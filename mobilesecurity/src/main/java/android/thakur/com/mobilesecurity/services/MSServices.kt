package android.thakur.com.mobilesecurity.services

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.thakur.com.mobilesecurity.database.entityModels.UserData
import android.thakur.com.mobilesecurity.database.helper.MobileSecurityDatabase
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.loggerUtil.Utils
import android.thakur.com.mobilesecurity.model.UserLocation
import android.thakur.com.mobilesecurity.services.jobs.BatteryReciever
import android.thakur.com.mobilesecurity.services.jobs.ScheduleJob
import android.thakur.com.mobilesecurity.submodules.deviceInfo.DeviceInfo
import android.thakur.com.mobilesecurity.submodules.location.UserLocationManager
import com.google.gson.Gson
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SealedObject

internal enum class MODULE(val value: Int) {
    LOCATION(5001),
    DEVICE_INFO(5002)
}

internal enum class EVENTTRIGGER{
    BACKGROUND, FOREGROUND
}

internal enum class EVENTTYPE(val value: Int){
    SCHEDULED(4001),
    NETWORK_CHANGE(4002),
    LOCATION_CHANGE(4003),
    LOCATION_VISIT(4004),
    REBOOTED(4005)
}

internal class MSServices private constructor() {

    init {
        println("Singleton class invoked.")
    }

    private lateinit var appContext: Context
    private lateinit var appActivity: Activity
    private lateinit var batteryReciever: BatteryReciever

    private var startTime: Date? = null
    private var endTime: Date? = null

    private var _isProcessStarted: Boolean = false
    private var isProcessStarted: Boolean
        get() = _isProcessStarted
        set(value) {
            _isProcessStarted = value
        }


    private lateinit var logger: Logger

    private lateinit var batterInfo: HashMap<String, Any>

    private lateinit var scheduleJob: ScheduleJob

    private var userLocationManager: UserLocationManager? = null

    private lateinit var database: MobileSecurityDatabase

    fun scheduleJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appActivity = activity



        logger = Logger(this.appContext)

        scheduleJob = ScheduleJob()
        scheduleJob.scheduleJob(this.appContext, this.appActivity)
    }

    internal fun recievedBatteryInformation(batteryData: HashMap<String, Any>) {
        this.batterInfo = batteryData
    }


    fun startJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appContext = activity

        batteryReciever = BatteryReciever()
        this.appActivity.registerReceiver(batteryReciever, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        logger = Logger(this.appContext)

        if (this.startTime != null) {
            val difference = Date().time - startTime!!.time

            if (difference > triggerDuration) {
                if (isProcessStarted) {
                    if (difference > lockDuration) {
                        logger.log("new trigger is happening after 10 mins")
                        this.startServices()
                    }
                } else {
                    logger.log("new trigger is happening after 10 mins")
                    this.startServices()
                }
            } else {
                if (isProcessStarted){
                    logger.log("older trigger is still processing")
                }
                else{
                    logger.log("older trigger happens in less than trigger duration : $difference/1000 secs ago")
                }
            }
        } else {
            logger.log("new trigger is happening after 10 mins")
            logger.log("first capture")
            this.startServices()
        }

    }

    fun endJob(module: MODULE, value: Any) {

        logger = Logger(this.appContext)
        if (module == MODULE.LOCATION) {
            val userLocation = value as UserLocation
            val deviceInfo = DeviceInfo().payload()

            endTime = Date()

            val hashMap = hashMapOf(
                    "location" to userLocation,
                    "deviceInfo" to deviceInfo,
                    "batterInfo" to batterInfo,
                    "startTime" to startTime!!,
                    "endTime" to endTime!!)

            val gson = Gson()
            val response = gson.toJson(hashMap)

            logger.log("process is completed : $response")

            runBlocking {
                launch(Dispatchers.Default) {
                    val logTime = Date().time
                    val cipher: Cipher = Utils.generateCipher(logTime)
                    val sealedObject = SealedObject(response, cipher)


                    val byteArray = Utils.sealedObjectStream(sealedObject)
                    if (byteArray != null) {
                        database = MobileSecurityDatabase.getInstance(appContext)
                        val userData = UserData(logTime, byteArray)
                        database.userDao().insertUserData(userData)
                    }
                }
            }
            isProcessStarted = false
            this.scheduleJob(this.appContext, this.appActivity)
        }
        this.appActivity.unregisterReceiver(batteryReciever)
    }


    private fun startServices() {
        startTime = Date()
        userLocationManager = null
        userLocationManager = UserLocationManager()
        userLocationManager!!.initialize(this.appActivity, this.appContext)
        userLocationManager!!.startServices()
        isProcessStarted = true
    }



    private object Holder {
        val instance = MSServices()
    }

    companion object {
        val sharedInstance: MSServices by lazy { Holder.instance }
        const val lockDuration = 15 * 60 * 60 * 1000
        const val triggerDuration = 10 * 60 * 60 * 1000
    }


}