package se.grayson.sfibuddy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform