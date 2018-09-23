package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TRIGGER
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TYPE

internal class JobSchedulerService:JobService() {

    var logger: Logger = Logger(this)

    override fun onStartJob(params: JobParameters?): Boolean {

        logger.log(logInfo = "onStartJob: $params")

        if (params!!.jobId == EVENT_TYPE.LOCATION_CHANGE.value){
            val intent = Intent(this, BackgroundService::class.java)
            intent.putExtra("eventType", EVENT_TYPE.LOCATION_CHANGE.value)
            // this will call BackgroundService onHandle fun
            startService(intent)
        }

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        logger.log(logInfo = "onStopJob: $params")
        return true
    }

    override fun onLowMemory() {
        logger.log(logInfo = "low memory trigged")
        super.onLowMemory()
    }
}