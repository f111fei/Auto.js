package com.example.mytestsrv
import android.app.Application
import com.siranyang.fastautolib.AutoJs
import com.siranyang.fastautolib.AutojsHelper
import com.siranyang.fastautolib.GlobalKeyObserver


class App : Application() {

    @Override
    override fun onCreate() {
        super.onCreate()
        //GlobalAppContext.set(this)
        //AutoJs.initInstance(this)
        //GlobalKeyObserver.init()
        AutojsHelper.init(this)
    }
}
