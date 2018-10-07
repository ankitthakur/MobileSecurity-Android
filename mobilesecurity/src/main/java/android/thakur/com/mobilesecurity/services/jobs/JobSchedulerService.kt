package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TYPE
import android.util.Log

internal class JobSchedulerService:JobService() {

    private lateinit var logger:Logger
    private lateinit var appContext:Context

    override fun onCreate() {
        this.appContext = this@JobSchedulerService

        super.onCreate()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.w("TAG","JobSchedulerService: onStartJob: $params context:$this")

        logger = Logger(this.appContext)
        logger.log(logInfo = "JobSchedulerService: onStartJob: $params")

        if (params!!.jobId == EVENT_TYPE.LOCATION_CHANGE.value) {
            val intent = Intent(this, BackgroundService::class.java)
            intent.putExtra("JobSchedulerService: eventType", EVENT_TYPE.LOCATION_CHANGE.value)
            // this will call BackgroundService onHandle fun
            startService(intent)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        logger = Logger(this.appContext)
        logger.log(logInfo = "JobSchedulerService: onStopJob: $params")
        return true
    }

    override fun onLowMemory() {

        logger = Logger(this.appContext)
        logger.log(logInfo = "JobSchedulerService: low memory trigged")
        super.onLowMemory()
    }
}