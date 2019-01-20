package com.kotlinlib.other

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import com.kotlinlib.activity.ContextUtils
import java.net.URL

object UpdateEngine:ContextUtils {

    fun update(act:Activity,url:String,pb:ProgressBar,tvProgress:TextView){
        act.reqPermission({
            UpdateUtils.download(act, URL(url),pb,tvProgress)
        },{

        }, Manifest.permission.REQUEST_INSTALL_PACKAGES,
                Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}