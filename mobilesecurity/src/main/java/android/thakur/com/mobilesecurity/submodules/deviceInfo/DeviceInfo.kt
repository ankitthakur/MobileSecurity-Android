package android.thakur.com.mobilesecurity.submodules.deviceInfo

import android.os.Build
import android.os.Build.VERSION.RELEASE
import java.io.DataInputStream
import java.io.DataOutputStream


class DeviceInfo {

    val board = Build.BOARD
    val brand = Build.BRAND
    val device = Build.DEVICE
    val display = Build.DISPLAY
    val product = Build.PRODUCT

    val  details =  "VERSION.RELEASE : "+ RELEASE +
            "\nVERSION.INCREMENTAL : "+Build.VERSION.INCREMENTAL +
            "\nVERSION.SDK.NUMBER : "+Build.VERSION.SDK_INT +
            "\nBOARD : "+Build.BOARD +
            "\nBOOTLOADER : "+Build.BOOTLOADER +
            "\nBRAND : "+Build.BRAND +
            "\nSUPPORTED_ABIS : "+Build.SUPPORTED_ABIS +
            "\nDISPLAY : "+Build.DISPLAY +
            "\nFINGERPRINT : "+Build.FINGERPRINT +
            "\nHARDWARE : "+Build.HARDWARE +
            "\nHOST : "+Build.HOST +
            "\nID : "+Build.ID +
            "\nMANUFACTURER : "+Build.MANUFACTURER +
            "\nMODEL : "+Build.MODEL +
            "\nPRODUCT : "+Build.PRODUCT +
            "\nTAGS : "+Build.TAGS +
            "\nTIME : "+Build.TIME +
            "\nTYPE : "+Build.TYPE +
            "\nUNKNOWN : "+Build.UNKNOWN + "\nUSER : "+Build.USER

    fun initialize() : String{
        return ""

    }

}