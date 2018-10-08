package android.thakur.com.mobilesecurity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.thakur.com.mobilesecurity.services.MSServices

/**
 * This module fetches some parameters from users device and for that, we want user permissions:
 *
 * This model is designed to prevent data loss when a user is navigating between activities
 *
 * <h3>Permissions</h3>
 *
 * <p>
 *
 * When starting an Activity you can set {ACCESS_FINE_LOCATION
 * ACCESS_NETWORK_STATE} and {RECEIVE_BOOT_COMPLETED} .  This will grant the
 * module access to the specific permissions of the user
 * Add following parts in ApplicationManifest.xml
 *
 *  <pre class="prettyprint">
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
 *  </pre>
 *
 *
 */
internal var appId:String = "unknown"

class MobileSecurity {

    lateinit var appActivity: Activity
    lateinit var appContext: Context


    /**
     * This function will register this module for the main context and activity of the application.
     *
     * @param context The new Context object to return from getApplicationContext of the application.
     *
     * @param activity The main Activity object of the application.
     *
     * Also please implement onRequestPermissionsResult for user permissions for location fetch
     *
     * <pre class="prettyprint">
     *     override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
     *          super.onRequestPermissionsResult(requestCode, permissions, grantResults)
     *     }
     * </pre>
     */
    fun register(context: Context, activity: Activity){
        this.appContext =context
        this.appActivity =activity
        appId = this.appContext.packageName
        registerEvent()
    }

    /**
     * Getting Callback for the result from requesting permissions. This method
     * is invoked for every callback from MainActivity
     */
    fun requestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)){
            registerEvent()
        }
    }



    private fun registerEvent(){
        MSServices.sharedInstance.scheduleJob(this.appContext, this.appActivity)
    }




}