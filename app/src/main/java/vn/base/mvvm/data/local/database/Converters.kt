package vn.base.mvvm.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vn.base.mvvm.data.local.database.entity.UserEntity

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<UserEntity>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<UserEntity> {
        val type = object : TypeToken<List<UserEntity>>() {}.type
        return gson.fromJson(json, type)
    }
}