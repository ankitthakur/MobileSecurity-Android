package android.thakur.com.mobilesecurity.services.backgroundservices

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.services.MSServices
import android.util.Log
import java.lang.Exception

/**
 * Since location services are async, we cannot use IntentService, and so inheriting from Service
 */
class LocationBackgroundService:Service() {

    private lateinit var networkLocationListener: LocationListener
    private lateinit var gpsLocationListener: LocationListener
    private var locationManager: LocationManager? = null
    private lateinit var logger:Logger
    private lateinit var appContext: Context



    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification():Notification {
        val channel  = NotificationChannel("channel_01", "My Channel", NotificationManager.IMPORTANCE_NONE)
        val notificationManager:NotificationManager  = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
        val builder: Notification.Builder  = Notification.Builder(getApplicationContext(), "channel_01")
        return builder.build()
    }


    override fun onCreate() {
        super.onCreate()

        this.appContext = this@LocationBackgroundService
        logger = Logger(this.appContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(111, getNotification())
        }


        networkLocationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                startLocationChange(location)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {

                /**
                 * If location services are disabled, then we will point user to the settings panel
                 * to enable them. For that we are creating Intent here
                 */
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }

            override fun onProviderDisabled(provider: String) {
            }
        }

        gpsLocationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // Called when a new location is found by the network location provider.
                startLocationChange(location)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {

                /**
                 * If location services are disabled, then we will point user to the settings panel
                 * to enable them. For that we are creating Intent here
                 */
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }

            override fun onProviderDisabled(provider: String) {
            }
        }

    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val gps_enabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isNetworkEnabled)
        {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30*60*1000, 50.toFloat(), networkLocationListener)
        }
        else{
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30*60*1000, 50.toFloat(), gpsLocationListener)
        }

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        if (locationManager != null){
            locationManager!!.removeUpdates(networkLocationListener)
            locationManager!!.removeUpdates(gpsLocationListener)
            locationManager = null
        }
        super.onDestroy()
    }

    fun startLocationChange(location: Location){
        logger.log("start location change event:"+location.toString())
        MSServices.sharedInstance.startJob(null, null)
    }


}