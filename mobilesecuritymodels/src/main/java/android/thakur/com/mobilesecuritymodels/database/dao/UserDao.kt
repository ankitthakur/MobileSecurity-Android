package android.thakur.com.mobilesecurity.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.thakur.com.mobilesecurity.database.entityModels.UserData

@Dao
interface UserDao {

    @get:Query("SELECT * FROM UserData")
    val users: List<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(user: UserData): Long


}