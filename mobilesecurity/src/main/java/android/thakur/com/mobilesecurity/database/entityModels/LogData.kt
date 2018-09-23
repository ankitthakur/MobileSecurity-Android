package android.thakur.com.mobilesecurity.database.entityModels

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "LogData")
data class LogData(@PrimaryKey @field:ColumnInfo(name = "timestamp") var logTime: Long,
                   @field:ColumnInfo(name = "data") var byteArray: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogData

        if (logTime != other.logTime) return false
        if (!Arrays.equals(byteArray, other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = logTime.hashCode()
        result = 31 * result + Arrays.hashCode(byteArray)
        return result
    }
}