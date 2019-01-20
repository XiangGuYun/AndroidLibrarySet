package com.kotlinlib.other

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.kotlinlib.dialog.DialogInfo

open class KotlinDialog(ctx:Context):Dialog(ctx) {

    var dialogView: View

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogInject = this::class.annotations[0] as DialogInfo
        dialogView = LayoutInflater.from(context).inflate(dialogInject.layoutId, null)
        setContentView(dialogView)
        val dialogWindow = window
        dialogWindow!!.setBackgroundDrawableResource(android.R.color.transparent)
        val lp = dialogWindow.attributes
        lp.width = DensityUtils.dip2px(ctx,dialogInject.width.toFloat())
        lp.height = DensityUtils.dip2px(ctx,dialogInject.height.toFloat())
        dialogWindow.attributes = lp
    }

}