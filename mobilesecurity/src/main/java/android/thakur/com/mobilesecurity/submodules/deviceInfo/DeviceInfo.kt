package android.thakur.com.mobilesecurity.submodules.deviceInfo

import android.os.Build
import android.os.Build.VERSION.RELEASE


class DeviceInfo {

    fun payload() : HashMap<String, Any>{

        val abis = arrayListOf<String>()

        for (abi in Build.SUPPORTED_ABIS){
            abis.add(abi)
        }

        val version = hashMapOf<String, String>(
                "release" to RELEASE,
                "incremental" to Build.VERSION.INCREMENTAL,
                "sdkNumber" to Build.VERSION.SDK_INT.toString()
        )

        val data = hashMapOf<String, String>(
                "board" to Build.BOARD,
                "bootLoader" to Build.BOOTLOADER,
                "brand" to Build.BRAND,
                "supportedApis" to abis.toString(),
                "display" to Build.DISPLAY,
                "fingerprint" to Build.FINGERPRINT,
                "hardware" to Build.HARDWARE,
                "host" to Build.HOST,
                "buildId" to Build.ID,
                "manufacturer" to Build.MANUFACTURER,
                "model" to Build.MODEL,
                "product" to Build.PRODUCT,
                "tags" to Build.TAGS,
                "buildtime" to Build.TIME.toString(),
                "buildtype" to Build.TYPE,
                "unknown" to Build.UNKNOWN,
                "user" to Build.USER
                )

        val responseData = hashMapOf<String, Any>(
                "version" to version
        )
        responseData.putAll(data)

        return responseData

    }

}