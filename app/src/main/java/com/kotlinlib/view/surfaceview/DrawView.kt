package com.kotlinlib.view.surfaceview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

abstract class DrawView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var mHolder: SurfaceHolder? = null
    private var canvas: Canvas? = null//画布
    private var isDrawing: Boolean = false

    protected val drawingSpeed: Int
        get() = 30

    init {
        init()
    }

    protected fun init() {
        mHolder = holder
        mHolder!!.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        isDrawing = true
        Thread(Runnable {
            while (isDrawing) {
                /**取得更新之前的时间 */
                /**取得更新之前的时间 */
                val startTime = System.currentTimeMillis()

                /**在这里加上线程安全锁 */

                /**在这里加上线程安全锁 */
                synchronized(holder) {
                    /**拿到当前画布 然后锁定 */
                    /**拿到当前画布 然后锁定 */
                    canvas = holder.lockCanvas()
                    if (canvas != null) {
                        drawing(canvas!!)
                        /**绘制结束后解锁显示在屏幕上 */
                        /**绘制结束后解锁显示在屏幕上 */
                        holder.unlockCanvasAndPost(canvas)
                    }
                }

                /**取得更新结束的时间 */
                /**取得更新结束的时间 */
                val endTime = System.currentTimeMillis()

                /**计算出一次更新的毫秒数 */
                /**计算出一次更新的毫秒数 */
                var diffTime = (endTime - startTime).toInt()

                /**确保每次更新时间为30帧 */

                /**确保每次更新时间为30帧 */
                while (diffTime <= drawingSpeed) {
                    diffTime = (System.currentTimeMillis() - startTime).toInt()
                    /**线程等待 */
                    /**线程等待 */
                    Thread.yield()
                }
            }
        }).start()
    }

    protected abstract fun drawing(canvas: Canvas)

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        isDrawing = false
    }
}
