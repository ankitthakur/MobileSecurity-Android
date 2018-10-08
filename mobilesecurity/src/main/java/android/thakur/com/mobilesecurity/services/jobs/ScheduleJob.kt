package android.thakur.com.mobilesecurity.services.jobs

import android.Manifest
import android.app.Activity
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.format.DateUtils
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.EVENTTYPE
import android.thakur.com.mobilesecurity.services.backgroundservices.LocationBackgroundService
import kotlinx.coroutines.experimental.runBlocking

internal class ScheduleJob {

    private lateinit var logger: Logger
    private lateinit var appContext: Context
    private lateinit var appActivity: Activity
   // private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun scheduleJob(context: Context?, activity: Activity?) {
        if (context != null) this.appContext = context
        if (activity != null) this.appActivity = activity


        logger = Logger(this.appContext)

        if (checkPermissions(this.appContext)){
            startLocationService(appContext)
        }
        else{
            startLocationPermissionRequest()
        }

        this.scheduleJob(this.appContext, EVENTTYPE.SCHEDULED)
        this.scheduleJob(this.appContext, EVENTTYPE.NETWORK_CHANGE)
    }

    private fun scheduleJob(context: Context, event:EVENTTYPE) {
        val component: ComponentName = if (event == EVENTTYPE.NETWORK_CHANGE)
        {
            ComponentName(context, NetworkJobService::class.java)
        }
        else{
            ComponentName(context, JobSchedulerService::class.java)
        }

        val info: JobInfo = JobInfo.Builder(event.value, component)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setOverrideDeadline(DateUtils.HOUR_IN_MILLIS/6)
                .build()

        val scheduler: JobScheduler = context.getSystemService(JobService.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            logger.log("scheduled success ")
        }
    }


    private fun startLocationService(context: Context) {

        val intent = Intent(context, LocationBackgroundService::class.java)
        intent.putExtra("eventType", EVENTTYPE.LOCATION_CHANGE.value)

        // this will call BackgroundService onHandle fun
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context,intent)
        }
        else{
            context.startService(intent)
        }
    }

    private fun checkPermissions(obj:Context) : Boolean{
        logger.log("check Permissions")
        return ActivityCompat.checkSelfPermission(obj, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        logger.log("requestPermissions: ACCESS_FINE_LOCATION")

        ActivityCompat.requestPermissions(this.appActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)

        runBlocking {
            if (checkPermissions(appContext)){
                startLocationService(appContext)
            }
        }
    }

}

const val REQUEST_PERMISSIONS_REQUEST_CODE = 101