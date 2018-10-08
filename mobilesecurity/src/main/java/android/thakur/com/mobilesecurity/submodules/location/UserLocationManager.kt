package android.thakur.com.mobilesecurity.submodules.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.checkSelfPermission
import android.thakur.com.mobilesecurity.loggerUtil.Logger
import android.thakur.com.mobilesecurity.model.UserLocation
import android.thakur.com.mobilesecurity.services.MODULE
import android.thakur.com.mobilesecurity.services.MSServices
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


// Please remember, the activity, which will call this Manager,
// will override callback for ACCESS_FINE_LOCATION permission.

internal class UserLocationManager {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var appActivity: Activity
    private lateinit var currentLocation: UserLocation

    private lateinit var logger:Logger


    fun initialize(activity:Activity, context: Context) {
        this.appActivity = activity
        logger = Logger(context)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appActivity)
    }

    fun startServices(){

        if (!(checkPermissions(this.appActivity))){
            requestPermissions()
        }
        else{
            getLatestLocation()
        }
    }

    private fun requestPermissions(){
        // if the user permission did not asked,
        // firstly prompt the end user to ask permission for location
        // This would also happen if the user denied the request previously,
        // but did not check the "Don't ask again" checkbox.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.appActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION)){
            startLocationPermissionRequest()

        }
        else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }

    private fun startLocationPermissionRequest() {
        logger.log("requestPermissions: ACCESS_FINE_LOCATION")

//        var request = LocationRequest()


        ActivityCompat.requestPermissions(this.appActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)
    }



    @SuppressLint("MissingPermission")
    private fun getLatestLocation() {

        fusedLocationProviderClient.lastLocation
                .addOnCompleteListener(this.appActivity) {
                    if (it.isSuccessful && it.result != null) {
                        this.currentLocation = UserLocation(currentLocation = it.result)
                        MSServices.sharedInstance.endJob(MODULE.LOCATION, currentLocation)
                    } else logger.log("getLastLocation:exception :"+ it.exception.toString())
                }
    }

    companion object {
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 101

        private fun checkPermissions(obj:Activity) : Boolean{
            return checkSelfPermission(obj, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }
}