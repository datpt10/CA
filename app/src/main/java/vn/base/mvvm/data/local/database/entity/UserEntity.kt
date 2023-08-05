package vn.base.mvvm.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("userName") val userName: String,
    @ColumnInfo("fullName") val fullName: String,
    @ColumnInfo("avatar") val avatar: String,
    @ColumnInfo("roles") val roles: String,
    @ColumnInfo("nextAction") val nextAction: String,
    @ColumnInfo("typeView") val typeView: String
) : Serializable