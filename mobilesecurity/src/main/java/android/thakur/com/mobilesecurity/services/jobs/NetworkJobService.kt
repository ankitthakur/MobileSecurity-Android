package android.thakur.com.mobilesecurity.services.jobs

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.net.*
import android.net.wifi.WifiManager
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.Services
import android.thakur.com.mobilesecurity.services.backgroundservices.BackgroundService
import android.thakur.com.mobilesecurity.services.backgroundservices.EVENT_TYPE

class NetworkJobService:JobService() {

    private var context: Context? = null
    private var logger: Logger? = null
    private lateinit var connectivityManager:ConnectivityManager

    private val wifiManager by lazy {
        if (context == null){
            context = Services.sharedInstance.appContext
        }
        context!!.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }
    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                if (context == null){
                    context = Services.sharedInstance.appContext
                }
                val intent = Intent(context!!, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context!!.startService(intent)

            }

            override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
                super.onLinkPropertiesChanged(network, linkProperties)

                if (context == null){
                    context = Services.sharedInstance.appContext
                }
                val intent = Intent(context!!, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context!!.startService(intent)

            }

            override fun onLost(network: Network?) {
                super.onLost(network)

                if (context == null){
                    context = Services.sharedInstance.appContext
                }
                val intent = Intent(context!!, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context!!.startService(intent)

            }

            override fun onUnavailable() {
                super.onUnavailable()

                if (context == null){
                    context = Services.sharedInstance.appContext
                }
                val intent = Intent(context!!, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context!!.startService(intent)

            }

            override fun onLosing(network: Network?, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)

                if (context == null){
                    context = Services.sharedInstance.appContext
                }
                val intent = Intent(context!!, BackgroundService::class.java)
                intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
                // this will call BackgroundService onHandle fun
                context!!.startService(intent)

            }
        }
    }

    override fun onStartJob(params: JobParameters?): Boolean {

        if(logger==null)logger = Logger(this)
        logger!!.log(logInfo = "NetworkJobService: onStartJob: $params")

        if (params!!.jobId == EVENT_TYPE.NETWORK_CHANGE.value){
            val intent = Intent(this, BackgroundService::class.java)
            intent.putExtra("eventType", EVENT_TYPE.NETWORK_CHANGE.value)
            // this will call BackgroundService onHandle fun
            startService(intent)
        }

        if (context == null){
            context = Services.sharedInstance.appContext
        }

        connectivityManager = context!!.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        if(logger==null)logger = Logger(this)
        logger!!.log(logInfo = "NetworkJobService: onStopJob: $params")
        return true
    }

    override fun onLowMemory() {
        if(logger==null)logger = Logger(this)
        logger!!.log(logInfo = "NetworkJobService: low memory trigged")
        super.onLowMemory()
    }

    fun registerNetworkEvent(context:Context){
        this.context = context
        connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }


}