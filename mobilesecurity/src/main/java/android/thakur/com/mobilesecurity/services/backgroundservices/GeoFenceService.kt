package android.thakur.com.mobilesecurity.services.backgroundservices

import android.app.IntentService
import android.content.Intent
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices

class GeoFenceService : IntentService("GeoFenceService") {

    private val logger = Logger(this@GeoFenceService)
    lateinit var geofencingClient: GeofencingClient


    override fun onCreate() {
        logger.log("background geo-fence service created")
        geofencingClient = LocationServices.getGeofencingClient(this@GeoFenceService)
        super.onCreate()
    }

    override fun onDestroy() {
        logger.log("background geo-fence service destroyed")
        super.onDestroy()
    }
    override fun onHandleIntent(intent: Intent?) {

    }
}