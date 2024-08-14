package com.example.randomquotegenerator

import okhttp3.Route

sealed class Screen(val route: String) {

    object SplashScreen: Screen("splash")
    object QuoteScreen: Screen("quotescreen")
}