package se.grayson.sfibuddy.di

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        module { appModule }
    }
}