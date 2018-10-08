package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
//import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.EVENTTYPE
import android.thakur.com.mobilesecurity.services.MSServices
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

        if (params!!.jobId == EVENTTYPE.LOCATION_CHANGE.value) {
            MSServices.sharedInstance.startJob(appContext, null)
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