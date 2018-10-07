package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.net.*
import android.net.wifi.WifiManager
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TYPE

class NetworkJobService:JobService() {

    private lateinit var logger: Logger
    private lateinit var appContext: Context
    private val connectivityManager:ConnectivityManager by lazy { this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    private val wifiManager by lazy {
        this@NetworkJobService.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                val intent = Intent(this@NetworkJobService, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                this@NetworkJobService.startService(intent)

            }

            override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                super.onLinkPropertiesChanged(network, linkProperties)

                val intent = Intent(this@NetworkJobService, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                this@NetworkJobService.startService(intent)

            }

            override fun onLost(network: Network?) {
                super.onLost(network)

                val intent = Intent(this@NetworkJobService, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                this@NetworkJobService.startService(intent)

            }

            override fun onUnavailable() {
                super.onUnavailable()

                val intent = Intent(this@NetworkJobService, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                this@NetworkJobService.startService(intent)

            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)

                val intent = Intent(this@NetworkJobService, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                this@NetworkJobService.startService(intent)

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

        if (params!!.jobId == EVENT_TYPE.NETWORK_CHANGE.value){
            val intent = Intent(this.appContext, BackgroundService::class.java)
            intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
            // this will call BackgroundService onHandle fun
            startService(intent)
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