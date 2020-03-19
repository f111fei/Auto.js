package com.siranyang.fastautolib.launch

import android.annotation.SuppressLint
import com.stardust.app.GlobalAppContext

@SuppressLint("StaticFieldLeak")
object GlobalProjectLauncher: AssetsProjectLauncher("scripts", GlobalAppContext.get())