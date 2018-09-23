package android.thakur.com.mobilesecurity.database.entityModels

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "UserData")
data class UserData(@PrimaryKey @field:ColumnInfo(name = "timestamp") var logTime: Long,
                    @field:ColumnInfo(name = "data") var byteArray: ByteArray)