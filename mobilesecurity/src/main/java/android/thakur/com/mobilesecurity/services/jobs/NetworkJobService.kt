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

    private lateinit var context: Context
    private lateinit var logger: Logger
    private lateinit var connectivityManager:ConnectivityManager

    private val wifiManager by lazy { context.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                val intent = Intent(context, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context.startService(intent)

            }

            override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                super.onLinkPropertiesChanged(network, linkProperties)

                val intent = Intent(context, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context.startService(intent)

            }

            override fun onLost(network: Network?) {
                super.onLost(network)

                val intent = Intent(context, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context.startService(intent)

            }

            override fun onUnavailable() {
                super.onUnavailable()

                val intent = Intent(context, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context.startService(intent)

            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)

                val intent = Intent(context, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context.startService(intent)

            }
        }
    }

    override fun onStartJob(params: JobParameters?): Boolean {

        logger.log(logInfo = "onStartJob: $params")

        if (params!!.jobId == EVENT_TYPE.NETWORK_CHANGE.value){
            val intent = Intent(this, BackgroundService::class.java)
            intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
            // this will call BackgroundService onHandle fun
            startService(intent)
        }
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
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

    fun registerNetworkEvent(context:Context){
        this.context = context
        logger = Logger(context)
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }


}