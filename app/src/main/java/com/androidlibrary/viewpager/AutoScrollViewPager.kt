package com.androidlibrary.viewpager

import android.os.Bundle
import android.view.View
import com.androidlibrary.R
import com.androidlibrary.dialog.InputDialog
import com.kotlinlib.activity.KotlinActivity
import com.kotlinlib.fragment.FragPagerEngine
import com.kotlinlib.other.LayoutId
import kotlinx.android.synthetic.main.activity_auto_scroll_view_pager.*
import kotlinx.android.synthetic.main.dialog_input.view.*

/**
    startAutoScroll() start auto scroll, delay time is getInterval().
    startAutoScroll(int) start auto scroll delayed.
    stopAutoScroll() stop auto scroll.

    Setting
    setInterval(long) set auto scroll time in milliseconds, default is DEFAULT_INTERVAL.
    setDirection(int) set auto scroll direction, default is RIGHT.
    setCycle(boolean) set whether automatic cycle when auto scroll reaching the last or first item, default is true.
    setScrollDurationFactor(double) set the factor by which the duration of sliding animation will change.
    setSlideBorderMode(int) set how to process when sliding at the last or first item, default is SLIDE_BORDER_MODE_NONE.
    setStopScrollWhenTouch(boolean) set whether stop auto scroll when touching, default is true.
    setBorderAnimation(boolean) set whether animating when auto scroll at the last or first item, default is true.

    You cannot combine with ViewPagerIndicator if setCycle(true).
    If you want infinite loop, please see AutoScrollViewPagerSingleDemo.java
    More: http://www.trinea.cn/android/auto-scroll-view-pager/
 */
@LayoutId(R.layout.activity_auto_scroll_view_pager)
class AutoScrollViewPager : KotlinActivity() {

    lateinit var dialog:InputDialog

    override fun init(bundle: Bundle?) {
        FragPagerEngine(this, vpTest, ImageFragment.newInstance(R.mipmap.img1),
            ImageFragment.newInstance(R.mipmap.img2),ImageFragment.newInstance(R.mipmap.img3))

        dialog = InputDialog(this)

        btnStart.limitClick {
            "开始翻页".toast()
            vpTest.startAutoScroll()
        }

        btnStop.limitClick {
            "结束翻页".toast()
            vpTest.stopAutoScroll()
        }

        btnStartDelay.showDialog {
            "延迟${it}秒开始翻页".toast()
            vpTest.startAutoScroll(it.toInt()*1000)
        }

        btnSetInterval.showDialog {
            "设置成功".toast()
            vpTest.interval = it.toInt()*1000.toLong()
        }


    }

    fun View.showDialog(getResult:(result:String)->Unit){
        limitClick {
            dialog.show()
            dialog.dialogView.btnInput.click {
                if(dialog.dialogView.etInput.textString.isEmpty()) return@click
                getResult.invoke(dialog.dialogView.etInput.textString)
                dialog.dismiss()
            }
        }
    }

}