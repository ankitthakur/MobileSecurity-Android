package android.thakur.com.mobilesecurity.services.jobs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.thakur.com.mobilesecurity.services.Services


class BatteryReciever: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
            var hashMapPayload = hashMapOf<String, Any>(
                    "health" to intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0),
                    "level" to intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0),
                    "plugged" to intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0),
                    "present" to intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT),
                    "status" to intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0),
                    "technology" to intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY),
                    "temperature" to intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0),
                    "voltage" to intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0),
                    "chargePlug" to intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1),
                    "usbCharge" to (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) == BatteryManager.BATTERY_PLUGGED_USB),
                    "acCharge" to (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) == BatteryManager.BATTERY_PLUGGED_AC)
            )

            Services.sharedInstance.recievedBatteryInformation(hashMapPayload)



        }



    }


}