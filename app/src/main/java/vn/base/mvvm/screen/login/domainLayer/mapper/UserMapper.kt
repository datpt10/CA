package vn.base.mvvm.screen.login.domainLayer.mapper

import vn.base.mvvm.core.mapper.Mapper
import vn.base.mvvm.data.local.LocalStorage
import vn.base.mvvm.data.local.database.entity.UserEntity
import vn.base.mvvm.screen.profile.data.local.UserInfo

class UserMapper(
    private val localStorage: LocalStorage
) : Mapper<UserEntity, UserInfo> {

    override fun convert(t: UserEntity) =
        UserInfo(
            userName = t.userName,
            fullName = t.fullName,
            avatar = t.avatar,
            roles = t.roles,
            nextAction = t.nextAction,
            typeView = t.typeView
        )
}