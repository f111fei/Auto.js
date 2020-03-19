package com.stardust.auojs.inrt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast

import com.stardust.auojs.inrt.autojs.AutoJs
import com.stardust.auojs.inrt.launch.GlobalProjectLauncher
import com.stardust.autojs.core.console.ConsoleView
import com.stardust.autojs.core.console.ConsoleImpl
import com.stardust.autojs.core.storage.LocalStorage

class LogActivity : AppCompatActivity() {
    var edt_acc: EditText? = null
    var edt_pswd: EditText? = null
    var str_acc: String? = ""
    var str_pswd: String? = ""
    var TAG: String = "LogActivity"
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

    public fun btnStartOnClick(v: View){
        //Toast.makeText(this@LogActivity,"请输入您的号码！",Toast.LENGTH_SHORT).show();
        if (str_acc == "" || str_pswd == "") {
            Toast.makeText(this@LogActivity,"帐号和密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        var storage = LocalStorage(this.applicationContext, "test_storage");
        storage.put("acc", "\"" + str_acc + "\"");
        storage.put("pswd", "\"" + str_pswd + "\"");

        Thread {
            try {
                GlobalProjectLauncher.launch(this)
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@LogActivity, e.message, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LogActivity, LogActivity::class.java))
                    AutoJs.instance!!.globalConsole.printAllStackTrace(e)
                }
            }
        }.start()
    }

    public fun btnStopOnClick(v: View){
        //Toast.makeText(this@LogActivity, "acc is:" + str_acc + ", pswd is:" + str_pswd,Toast.LENGTH_SHORT).show();
        GlobalProjectLauncher.stopScript();
    }

    private fun setupView() {
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val consoleView = findViewById<ConsoleView>(R.id.console)
        consoleView.setConsole(AutoJs.instance.globalConsole as ConsoleImpl)
        consoleView.findViewById<View>(R.id.input_container).visibility = View.GONE

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, SettingsActivity::class.java))
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    companion object {


        val EXTRA_LAUNCH_SCRIPT = "launch_script"
    }
}
