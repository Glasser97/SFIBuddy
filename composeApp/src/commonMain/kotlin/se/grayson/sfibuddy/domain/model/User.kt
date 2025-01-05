package se.grayson.sfibuddy.domain.model

data class User(
    val username: String,
    val token: String
)

fun User?.isLogin(): Boolean {
    return this != null && username.isNotEmpty() && token.isNotEmpty()
}