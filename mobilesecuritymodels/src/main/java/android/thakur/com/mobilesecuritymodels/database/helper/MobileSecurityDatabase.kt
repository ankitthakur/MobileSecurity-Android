package android.thakur.com.mobilesecurity.database.helper

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.thakur.com.mobilesecurity.database.dao.LogDao
import android.thakur.com.mobilesecurity.database.dao.UserDao
import android.thakur.com.mobilesecurity.database.dbconfiguration.DB_NAME
import android.thakur.com.mobilesecurity.database.entityModels.LogData
import android.thakur.com.mobilesecurity.database.entityModels.UserData
import android.thakur.com.mobilesecurity.loggerUtil.SingletonHolder

@Database(entities = [LogData::class, UserData::class], version = 1, exportSchema = false)
abstract class MobileSecurityDatabase : RoomDatabase() {

    abstract fun logDao(): LogDao

    abstract fun userDao(): UserDao

    companion object : SingletonHolder<MobileSecurityDatabase, Context>({

        Room.databaseBuilder(it, MobileSecurityDatabase::class.java, DB_NAME)
                .addCallback(object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase){
                        System.out.println(""+db.path+":"+db.attachedDbs+":"+db.attachedDbs.size+":")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                    }
                })
                .build()
    })



//    companion object : SingletonHolder<MobileSecurityDatabase, Context>({
//        Room.databaseBuilder(it,
//                MobileSecurityDatabase::class.java, DB_NAME)
//                .addMigrations(MIGRATION_1_2)
//                .build()
//    }) {
//        @JvmField val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE user ADD COLUMN data BLOB NOT NULL DEFAULT \"\";")
//            }
//        }
//    }
}
