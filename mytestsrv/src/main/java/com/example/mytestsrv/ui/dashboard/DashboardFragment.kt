package com.example.mytestsrv.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mytestsrv.GlobalAppContext
import com.example.mytestsrv.MainActivity
import com.example.mytestsrv.R
import com.siranyang.fastautolib.AutojsHelper

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val btn :Button =  root.findViewById(R.id.btnSendScheme)
        btn?.setOnClickListener{
            /*//chicha://domain/path?params
            var intent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse("shq://zuhao.com?username=xxxx&password=xx"));

            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("chicha://domain/path?params"));
            var activities:List<ResolveInfo>  =  activity!!.getPackageManager().queryIntentActivities(intent, 0);
            var isValid: Boolean = !activities.isEmpty();
            if (isValid) {
                startActivity(intent);
            }*/
            //AutojsHelper.init(GlobalAppContext.getApp())
            AutojsHelper.runScript(MainActivity.activityInstance as Activity, "main");
        }

        return root
    }
}