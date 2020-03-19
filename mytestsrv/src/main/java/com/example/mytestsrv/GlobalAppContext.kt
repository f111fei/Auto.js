package com.example.mytestsrv

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import android.widget.Toast

/**
 * Created by Stardust on 2018/3/22.
 */

object GlobalAppContext {

    @SuppressLint("StaticFieldLeak")
    private var sApplicationContext: Context? = null
    private var sHandler: Handler? = null
    private var sApp: Application? = null

    fun set(a: Application) {
        sHandler = Handler(Looper.getMainLooper())
        sApplicationContext = a.applicationContext
        sApp = a
    }

    fun getApp(): Application {
        return sApp as Application;
    }

    fun get(): Context {
        checkNotNull(sApplicationContext) { "Call GlobalAppContext.set() to set a application context" }
        return sApplicationContext as Context
    }

    fun getString(resId: Int): String {
        return get().getString(resId)
    }

    fun getString(resId: Int, vararg formatArgs: Any): String {
        return get().getString(resId, *formatArgs)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getColor(id: Int): Int {
        return get().getColor(id)
    }

    fun toast(message: String) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(get(), message, Toast.LENGTH_SHORT).show()
            return
        }
        sHandler!!.post { Toast.makeText(get(), message, Toast.LENGTH_SHORT).show() }
    }

    fun toast(resId: Int) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(get(), resId, Toast.LENGTH_SHORT).show()
            return
        }
        sHandler!!.post { Toast.makeText(get(), resId, Toast.LENGTH_SHORT).show() }
    }

    fun toast(resId: Int, vararg args: Any) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(get(), getString(resId, *args), Toast.LENGTH_SHORT).show()
            return
        }
        sHandler!!.post { Toast.makeText(get(), getString(resId, *args), Toast.LENGTH_SHORT).show() }
    }

    fun post(r: Runnable) {
        sHandler!!.post(r)
    }

    fun postDelayed(r: Runnable, m: Long) {
        sHandler!!.postDelayed(r, m)
    }
}
