package android.thakur.com.mobilesecurity.loggerUtil

import android.content.Context
import android.thakur.com.mobilesecurity.appId
import android.thakur.com.mobilesecurity.database.entityModels.LogData
import android.thakur.com.mobilesecurity.database.helper.MobileSecurityDatabase
import android.util.Log
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SealedObject

internal class Logger {

    internal lateinit var context: Context

    internal lateinit var database: MobileSecurityDatabase

    internal constructor(context:Context) : this() {
        this.context = context
        database = MobileSecurityDatabase.getInstance(this.context)
    }

    constructor()

    fun log(logInfo:String){

        runBlocking {
            launch(Dispatchers.Default){
                val logTime = Date().time


                val fullClassName: String = Thread.currentThread().stackTrace[3].className
                val className: String = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
                val methodName:String = Thread.currentThread().stackTrace[3].methodName
                val lineNumber:Int = Thread.currentThread().stackTrace[3].lineNumber

                Log.d("$className: $methodName(): $lineNumber ", logInfo)
                val cipher:Cipher = Utils.generateCipher(logTime)
                val sealedObject = SealedObject(appId!! + ": " + className + ": " + methodName + "(): " + lineNumber + ": " + logInfo, cipher)
                Log.w("logger", sealedObject.toString())
                Log.w("logger", cipher.toString())

                Log.w("logger", appId!! + ": " + className + ": " + methodName + "(): " + lineNumber + ": " + logInfo)
                val byteArray = Utils.sealedObjectStream(sealedObject)
                if (byteArray != null)  {
                    val logData = LogData(logTime, byteArray)
                    database.logDao().insertLog(logData = logData)
                }
            }
        }

    }
}