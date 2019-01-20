package com.androidlibrary.dialog

import android.content.Context
import com.androidlibrary.R
import com.kotlinlib.dialog.DialogInfo
import com.kotlinlib.other.KotlinDialog

@DialogInfo(280, 200, R.layout.dialog_input)
class InputDialog(ctx:Context):KotlinDialog(ctx)