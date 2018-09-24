package android.thakur.com.mobilesecurity.loggerUtil

import android.content.Context
import android.thakur.com.mobilesecurity.appId
import android.thakur.com.mobilesecurity.database.entityModels.LogData
import android.thakur.com.mobilesecurity.database.helper.MobileSecurityDatabase
import android.thakur.com.mobilesecurity.services.Services
import android.util.Log
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SealedObject

internal class Logger {

    internal var context: Context

    internal var database: MobileSecurityDatabase

    init {
        this.context = Services.sharedInstance.appContext
        database = MobileSecurityDatabase.getInstance(this.context)
    }

    constructor()

    fun log(logInfo:String){

        runBlocking {
            launch(Dispatchers.Default){
                val logTime = Date().time
                val cipher:Cipher = Utils.generateCipher(logTime)
                val sealedObject = SealedObject(appId!! + " : " + logInfo, cipher)

                Log.w("logger", appId!! + " : " + logInfo)
                val byteArray = Utils.sealedObjectStream(sealedObject)
                if (byteArray != null)  {
                    val logData = LogData(logTime, byteArray)
                    database.logDao().insertLog(logData = logData)
                }
            }
        }

    }
}