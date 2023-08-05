package vn.base.mvvm.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.base.mvvm.data.local.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun multiUserDao(): UserDao

    private var instance: UsersDatabase? = null

    open fun getInMemoryDatabase(context: Context): UsersDatabase? {
        if (instance == null) {
            instance =
                inMemoryDatabaseBuilder(context.applicationContext, UsersDatabase::class.java)
                    .allowMainThreadQueries()
                    .build()
        }
        return instance
    }

    open fun destroyInstance() {
        instance = null
    }
}