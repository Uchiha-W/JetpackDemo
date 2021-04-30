package com.hwei.me.bean

/**
 * {"admin":false,"chapterTops":[],"coinCount":0,"collectIds":[],"email":"","icon":"","id":97651,"nickname":"hw123456789","password":"","publicName":"hw123456789","token":"","type":0,"username":"hw123456789"}
 */
data class UserBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)