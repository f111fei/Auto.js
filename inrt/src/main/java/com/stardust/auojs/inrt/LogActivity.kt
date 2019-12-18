package com.stardust.auojs.inrt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        if (intent.getBooleanExtra(EXTRA_LAUNCH_SCRIPT, false)) {
            //GlobalProjectLauncher.launch(this)
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
