package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.net.*
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.EVENTTYPE
import android.thakur.com.mobilesecurity.services.MSServices

class NetworkJobService:JobService() {

    private lateinit var logger: Logger
    private lateinit var appContext: Context
    private val connectivityManager:ConnectivityManager by lazy { this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                MSServices.sharedInstance.startJob(appContext, null)

            }

            override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                super.onLinkPropertiesChanged(network, linkProperties)

                MSServices.sharedInstance.startJob(appContext, null)

            }

            override fun onLost(network: Network?) {
                super.onLost(network)
                MSServices.sharedInstance.startJob(appContext, null)
            }

            override fun onUnavailable() {
                super.onUnavailable()

                MSServices.sharedInstance.startJob(appContext, null)

            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)

                MSServices.sharedInstance.startJob(appContext, null)

            }
        }
    }

    override fun onCreate() {
        this.appContext = this@NetworkJobService
        super.onCreate()
    }
    override fun onStartJob(params: JobParameters?): Boolean {

        logger = Logger(this.appContext)
        logger.log(logInfo = "NetworkJobService: onStartJob: $params")

        if (params!!.jobId == EVENTTYPE.NETWORK_CHANGE.value){
            MSServices.sharedInstance.startJob(appContext, null)
        }
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        logger = Logger(this.appContext)
        logger.log(logInfo = "NetworkJobService: onStopJob: $params")
        return true
    }

    override fun onLowMemory() {
        logger = Logger(this.appContext)
        logger.log(logInfo = "NetworkJobService: low memory trigged")
        super.onLowMemory()
    }
}