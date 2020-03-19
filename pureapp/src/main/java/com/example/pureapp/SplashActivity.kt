package com.example.pureapp

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.siranyang.fastautolib.AutojsHelper
import java.text.SimpleDateFormat
import java.util.*

class SplashActivity : AppCompatActivity() {
    var edt_acc: EditText? = null
    var edt_pswd: EditText? = null
    var str_acc: String? = ""
    var str_pswd: String? = ""
    var TAG: String = "SplashActivityttttt"
    private var mSharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        Log.e(TAG, "before .date")
        val uri = intent?.data
        if (uri != null) {
            val url = uri.toString()
            Log.e(TAG, "oncreate url: $url")
        } else {
            Log.e(TAG, "oncreate has no uri");
        }
        if (uri != null) {
            // 完整的url信息
            val url = uri.toString()
            Log.e(TAG, "url: $uri")

            // scheme部分
            val scheme = uri.scheme
            Log.e(TAG, "scheme: $scheme")

            // host部分
            val host = uri.host
            Log.e(TAG, "host: $host")

            //port部分
            val port = uri.port
            Log.e(TAG, "port: $port")

            // 访问路劲
            val path = uri.path
            Log.e(TAG, "path: $path")

            // Query部分
            val query = uri.query
            Log.e(TAG, "query: $query")

            //获取指定参数值
            val type = uri.getQueryParameter("username")
            Log.e(TAG, "type: $type")
            val buffer = uri.getQueryParameter("password")
            Log.e(TAG, "buffer: $buffer")
            edt_acc!!.setText(type)
            edt_pswd!!.setText(buffer)
        } else {
            edt_acc!!.setText("406625769")
            edt_pswd!!.setText("yangliLI1991")
        }
        if (intent.getBooleanExtra(EXTRA_LAUNCH_SCRIPT, false)) {
            //GlobalProjectLauncher.launch(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        if (uri != null) {
            val url = uri.toString()
            Log.e(TAG, "url: $url")
        } else {
            Log.e(TAG, "has no uri");
        }
        if (uri != null) {
            // 完整的url信息
            val url = uri.toString()
            Log.e(TAG, "url: $uri")

            // scheme部分
            val scheme = uri.scheme
            Log.e(TAG, "scheme: $scheme")

            // host部分
            val host = uri.host
            Log.e(TAG, "host: $host")

            //port部分
            val port = uri.port
            Log.e(TAG, "port: $port")

            // 访问路劲
            val path = uri.path
            Log.e(TAG, "path: $path")

            // Query部分
            val query = uri.query
            Log.e(TAG, "query: $query")

            //获取指定参数值
            val type = uri.getQueryParameter("username")
            Log.e(TAG, "type: $type")
            val buffer = uri.getQueryParameter("password")
            Log.e(TAG, "buffer: $buffer")

            edt_acc!!.setText(type)
            edt_pswd!!.setText(buffer)
        }
    }

    fun LocalStorage(context: Context, name: String): SharedPreferences {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("autojs.localstorage." + name, Context.MODE_PRIVATE)
        }
        return mSharedPreferences!!;
    }

    public fun btnStartOnClick(v: View){
        //Toast.makeText(this@LogActivity,"请输入您的号码！",Toast.LENGTH_SHORT).show();
        if (str_acc == "" || str_pswd == "") {
            Toast.makeText(this@SplashActivity,"帐号和密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        var storage = LocalStorage(this.applicationContext, "test_storage");
        //storage.put("acc", "\"" + str_acc + "\"");
        //storage.put("pswd", "\"" + str_pswd + "\"");

        Thread {
            try {
                AutojsHelper.runShanghaoqi();
                //GlobalProjectLauncher.launch(this, "amd")
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@SplashActivity, e.message, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@SplashActivity, SplashActivity::class.java))
                    e.printStackTrace()
                }
            }
        }.start()
    }

    public fun btnStopOnClick(v: View){
        //Toast.makeText(this@LogActivity, "acc is:" + str_acc + ", pswd is:" + str_pswd,Toast.LENGTH_SHORT).show();
        //GlobalProjectLauncher.stopScript();

        try {
            Thread.sleep(10000);
        } catch (e:java.lang.Exception) {
            Log.e(TAG, e.toString())
        }

        listAllProcess();
        getForegroundApp1();

    }

    public fun listAllProcess() {
        var am:ActivityManager  =getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var processInfos:List<ActivityManager.RunningAppProcessInfo>  = am.getRunningAppProcesses()
        var mainProcessName:String  = getPackageName()
        var myPid:Int = android.os.Process.myPid()
        for (info in processInfos) {
            Log.i(TAG, "aa " + info.processName + info.pid )
        }

    }

    public fun getForegroundApp1() {
        //if sdk <= 20, can use getRunningTasks
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            var am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager;
            var runningTasks: List<ActivityManager.RunningTaskInfo>  = am.getRunningTasks(1);
            var runningTaskInfo:ActivityManager.RunningTaskInfo  = runningTasks.get(0);
            var packagename: String = runningTaskInfo.topActivity.getPackageName();

            Log.i(TAG, "top package is :" + packagename);
        } else {
            //if sdk >= 21,use UsageStatsManager
            if(!hasPermission() && !hasPermissionVer2()) {
                startActivityForResult(Intent (Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS)
            } else {
                getTopApp(this.applicationContext)
            }
        }
    }

    private fun hasPermission():Boolean {
        var checkResult = ContextCompat.checkSelfPermission(this.applicationContext, Settings.ACTION_USAGE_ACCESS_SETTINGS)
        Log.i(TAG, "checkselfPermissong result is:" + checkResult)
        var res = ( checkResult != PackageManager.PERMISSION_DENIED)
        return res
    }

    private fun hasPermissionVer2():Boolean {
        var appOps:AppOpsManager  = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager;
        var mode:Int  = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        Log.i(TAG, "checkselfPermissong Version 2 result is:" + mode)
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        Log.i(TAG, "requestCode is:" + requestCode + ", resultCode is :" + resultCode)
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS && resultCode == PackageManager.PERMISSION_DENIED) {
            //finish()
            Log.i(TAG, "no permission,not run")
        } else {
            if(!hasPermission() && !hasPermissionVer2()) {
                Log.i(TAG, "seconde check,rusult no permission,not run")
            } else {
                Log.i(TAG,"after open setting, permission is OK now.")
                getTopApp(this.applicationContext)
            }
        }
    }

    private fun getTopAppVer2(context:Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var m:UsageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            if (m != null) {
                var now = System.currentTimeMillis()
                //获取60秒之内的应用数据
                var stats:List<UsageStats> = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now)

                Log.i(TAG, "Running app number in last 60 seconds : " + stats.size);

                var topActivity:String = "";

                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    var j:Int = 0;
                    for(i in 0 until stats.size) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                        var pgname = stats.get(i).packageName
                        Log.i(TAG,"i is:" + i + ", app is:" + pgname + ", is inactive ?:" + m.isAppInactive(pgname)
                                + ", to string is:" + stats.get(i).toString())

                    }
                    topActivity = stats.get(j).getPackageName();
                }
                Log.i(TAG, "top running app is : "+topActivity);
            }
        }
    }

    public fun getDate2String(time:Long, pattern:String):String {
        var date:Date = Date(time);
        var format: SimpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    private fun getTopApp(context:Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var m:UsageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            if (m != null) {
                var now = System.currentTimeMillis()
                //获取60秒之内的应用数据
                var stats = m.queryEvents(now - 60 * 1000, now)

                //Log.i(TAG, "Running app number in last 60 seconds : " + stats.);

                var topActivity:String = "";
                var pattern:String = "yyyy-MM-dd HH:mm:ss"

                var event:UsageEvents.Event = UsageEvents.Event()

                var usageEvents:UsageEvents  = m.queryEvents(now - 60 * 1000, now);
                var i:Int = 0;
                while (usageEvents.hasNextEvent()) {
                    usageEvents.getNextEvent(event)

                    if (event.packageName == "com.tencent.tmgp.sgame") {
                        Log.i(TAG, "i is:" + i + ", app is:" + event.packageName + ", event is:" + event.eventType
                                + ",time is:" + getDate2String(event.timeStamp, pattern))
                    }

                    if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                        event.packageName
                    }
                    ++i
//监测app由前台转后台
// if (event.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
// result = event.getPackageName();
// }
                }
            }
        }
    }

    //利用UsageStatsManager，并且调用他的queryUsageStats方法来获得启动的历史记录，调用这个方法需要设置权限“Apps withusage access”。
    // 但是这个queryUsageStats只能查询一段时间内的使用状态，如果时间间隔较短，并且一段时间不使用手机，获得的列表就可能为空。
    //https://blog.csdn.net/chaoyu168/article/details/82744332


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun getForegroundApp(context: Context ): String? {
        /**
         * 此功能需要在设置的（允许查看使用情况的应用）里打开，在很多手机设置里没有发现这一入口。
        需要在代码里打开，注意要判断一下系统版本
         */
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        var isInit:Boolean = true;
        var usageStatsManager: UsageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager;
        var ts:Long = System.currentTimeMillis();
        var queryUsageStats: List<UsageStats>  = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
        var usageEvents: UsageEvents = usageStatsManager.queryEvents(if(isInit){0}else{ts-5000}, ts);
        if (usageEvents == null) {
            return null;
        }

        var event:UsageEvents.Event  = UsageEvents.Event();
        var lastEvent:UsageEvents.Event?  = null;
        while (usageEvents.getNextEvent(event)) {
            // if from notification bar, class name will be null
            if (event.getPackageName() == null || event.getClassName() == null) {
                continue;
            }

            if (lastEvent == null || lastEvent.getTimeStamp() < event.getTimeStamp()) {
                lastEvent = event;
            }
        }

        if (lastEvent == null) {
            return null;
        }
        return lastEvent.getPackageName();
    }

    private fun setupView() {
        setContentView(R.layout.activity_splash)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        edt_acc = findViewById<View>(R.id.edit_account) as EditText

        edt_acc!!.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
                str_acc = s.toString()
                //Toast.makeText(applicationContext, res, Toast.LENGTH_SHORT).show()
            }
        })

        edt_pswd = findViewById<View>(R.id.edit_password) as EditText

        edt_pswd!!.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }


            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }


            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
                str_pswd = s.toString();
            }
        })
    }

    companion object {
            private const val MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101
        val EXTRA_LAUNCH_SCRIPT = "launch_script"
    }
}
