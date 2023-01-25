package com.sane4ek.zefirgg.utils

import android.content.Context
import android.net.ConnectivityManager
import java.math.RoundingMode

fun correctRoundScaleTo1(number: Double) : Double {
    return number.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
}

fun correctRoundScaleTo2(number: Double) : Double {
    return number.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
}

fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}