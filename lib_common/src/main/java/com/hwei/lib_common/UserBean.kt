package com.hwei.lib_common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * {"admin":false,"chapterTops":[],"coinCount":0,"collectIds":[],"email":"","icon":"","id":97651,"nickname":"hw123456789","password":"","publicName":"hw123456789","token":"","type":0,"username":"hw123456789"}
 */
@Parcelize
data class UserBean(
    val admin: Boolean,
    val chapterTops: List<String>,
    val coinCount: Int,
    val collectIds: List<String>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) : Parcelable {

    companion object {
        fun emptyUser(): UserBean {
            return UserBean(false, emptyList(), 0, emptyList(), "", "", 0, "", "", "", "", 0, "")
        }

        fun isEmpty(userBean: UserBean): Boolean {
            return userBean == emptyUser()
        }
    }
}