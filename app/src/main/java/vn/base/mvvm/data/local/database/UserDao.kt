package vn.base.mvvm.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import vn.base.mvvm.data.local.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: UserEntity)

    @Query("SELECT * FROM UserEntity")
    suspend fun getUsers(): List<UserEntity>

    @Insert(onConflict = IGNORE)
    fun insertOrReplaceEmploy(vararg employees: UserEntity)

    @Update(onConflict = REPLACE)
    fun updateEmploy(employee: UserEntity)

    @Query("DELETE FROM UserEntity")
    fun deleteAll()

    @Query("SELECT * FROM UserEntity")
    fun getAllUserEntity(): List<UserEntity>
}