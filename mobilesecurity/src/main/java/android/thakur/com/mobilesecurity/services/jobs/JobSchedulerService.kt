package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TRIGGER
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TYPE

internal class JobSchedulerService:JobService() {

    var logger:Logger? = null

    override fun onStartJob(params: JobParameters?): Boolean {

        if (logger == null)logger = Logger()
        logger!!.log(logInfo = "JobSchedulerService: onStartJob: $params")

        if (params!!.jobId == EVENT_TYPE.LOCATION_CHANGE.value) {
            val intent = Intent(this, BackgroundService::class.java)
            intent.putExtra("JobSchedulerService: eventType", EVENT_TYPE.LOCATION_CHANGE.value)
            // this will call BackgroundService onHandle fun
            startService(intent)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {


        if (logger == null)logger = Logger()
        logger!!.log(logInfo = "JobSchedulerService: onStopJob: $params")


        return true
    }

    override fun onLowMemory() {

        var logger: Logger = Logger()
        logger.log(logInfo = "JobSchedulerService: low memory trigged")
        super.onLowMemory()
    }
}