package com.kotlinlib.view.base

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.kotlinlib.view.edittext.ETUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.text.TextUtils
import com.jakewharton.rxbinding2.widget.RxTextView



interface ViewUtils {

    val MP:Int get() = ViewGroup.LayoutParams.MATCH_PARENT
    val WC:Int get() = ViewGroup.LayoutParams.WRAP_CONTENT

    /**
     * 设置View为不可见
     */
    fun <T:View> T.hide():T {
        this.visibility=View.INVISIBLE
        return this
    }

    /**
     * 设置View为不可见
     */
    fun <T:View> T.gone():T {
        this.visibility=View.GONE
        return this
    }

    /**
     * 如果View不可见，则设置View设置为可见
     */
    fun <T:View> T.showIfNot():T {
        if(visibility!=View.VISIBLE)
            visibility=View.VISIBLE
        return this
    }

    /**
     * 设置View为可见
     */
    fun <T:View> T.show():T {
        this.visibility=View.VISIBLE
        return this
    }

    /**
     * View是否可见
     */
    fun <T:View> T.canSee():  Boolean{
        return visibility==View.VISIBLE
    }


    /**
     * 显示然后隐藏
     * @param act Activity
     * @param delay Long 显示时间
     * @return T
     */
    fun <T:View> T.showAndHide(act:Activity, delay:Long=1200):T {
        show()
        val view = this
        Thread{
            Thread.sleep(delay)
            act.runOnUiThread { view.hide() }
        }.start()
        return this
    }


    /**
     * 设置点击事件
     */
    fun <T:View> T.click(function: () -> Unit):T {
        setOnClickListener { function.invoke() }
        return this
    }

    /**
     * 设置点击事件
     */
    fun <T:View> T.click1(func: (View)->Unit):T{
        setOnClickListener(func)
        return this
    }

    fun RadioGroup.check(getId:(id:Int)->Unit){
        setOnCheckedChangeListener { group, checkedId ->
            getId.invoke(checkedId)
        }
    }

    /**
     * 使用函数类型作为返回值类型
     */
    fun getFunType():(Int,Int)->Int{
        return fun(a:Int,b:Int):Int{
            return a+b
        }
    }

    /**
     * 设置透明度
     */
    fun <T:View> T.alpha(a:Float):T{
        alpha = a
        return this
    }

    /**
     * 设置背景
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun <T:View> T.bg(b:Drawable):T{
        background = b
        return this
    }

    /**
     * 设置布局参数
     */
    fun <T:View> T.lp(params:ViewGroup.LayoutParams):T{
        layoutParams = params
        return this
    }

    /**
     * 寻找View
     */
    fun  <T:View> T.fV(childId:Int):T{
        return findViewById(childId)
    }

    /**
     * 寻找TextView
     */
    fun  <T:View> T.fTV(childId:Int):TextView{
        return findViewById(childId)
    }

    /**
     * 寻找ImageView
     */
    fun  <T:View> T.fIV(childId:Int):ImageView{
        return findViewById(childId)
    }

    /**
     * 寻找ViewGroup
     */
    fun  <T:View> T.fVG(childId:Int):ViewGroup{
        return findViewById(childId)
    }

    /**
     * 获取子控件
     */
    fun <T:ViewGroup> T.child(index:Int):View{
        return getChildAt(index)
    }

    /**
     * 设置ImageView
     */
    fun ImageView.sIR(id:Int):ImageView{
        setImageResource(id)
        return this
    }

    /**
     * 翻转可见性1
     */
    fun View.rvtVisibility1(): View {
        visibility = if(visibility==View.INVISIBLE){
            View.VISIBLE
        }else{
            View.INVISIBLE
        }
        return this
    }

    /**
     * 翻转可见性2
     */
    fun View.rvtVisibility2(){
        visibility = if(visibility==View.GONE){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    /**
     * 刷新RecyclerView
     * @receiver RecyclerView
     */
    fun RecyclerView.update(){
        this.adapter!!.notifyDataSetChanged()
    }

    /**
     * 判断文本是否为空
     */
    fun TextView.empty(): Boolean {
        return text.toString().isEmpty()
    }

    /**
     * 设置正则表达式
     * @receiver EditText
     * @param regex 正则表达式
     * @param maxLength 允许输入的最长长度值
     */
    fun EditText.regex(regex:String, maxLength:Int=18): EditText {
        ETUtils.setRegex(this, regex, maxLength)
        return this
    }

    /**
     * 限制输入的数字范围大小
     * @receiver EditText
     * @param min Number
     * @param max Number
     * @return EditText
     */
    fun EditText.region(min:Number, max:Number): EditText {
        ETUtils.setRegion(this, min.toDouble(), max.toDouble())
        return this
    }


    /**
     * 防止重复点击-2秒
     * expound：https://blog.csdn.net/hust_twj/article/details/78742453
     */
    fun View.limitClick(click:(v:View)->Unit){
        preventRepeatedClick(View.OnClickListener {
            click.invoke(it)
        })
    }

    /**
     * 防止重复点击
     * @receiver View
     * @param click (v:View)->Unit
     * @param time Long 间隔时间
     */
    fun View.limitClickByTime(click:(v:View)->Unit, time: Long=2){
        preventRepeatedClick(View.OnClickListener {
            click.invoke(it)
        }, time)
    }


    fun View.preventRepeatedClick(listener:View.OnClickListener, time:Long=2) {
        val target = this
        RxView.clicks(this).throttleFirst(time, TimeUnit.SECONDS).subscribe(object :Observer<Any>{
            override fun onError(e: Throwable) {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onComplete() {

            }

            override fun onNext(t: Any) {
                listener.onClick(target)
            }
        })
    }



}