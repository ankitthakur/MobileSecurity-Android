package android.thakur.com.mobilesecurity.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.thakur.com.mobilesecurity.database.entityModels.LogData

@Dao
interface LogDao {

    @get:Query(value = "SELECT * FROM LogData")
    val logs: List<LogData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(logData: LogData): Long


}