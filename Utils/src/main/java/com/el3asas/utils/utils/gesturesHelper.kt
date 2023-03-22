package com.el3asas.utils.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class OnSwipeTouchListener(c: Context?) : View.OnTouchListener {
    private val gestureDetector: GestureDetector

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetector(c, GestureListener())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(p1)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onClick(e)
            return super.onSingleTapUp(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick(e)
            return super.onDoubleTap(e)
        }

        override fun onLongPress(e: MotionEvent) {
            onLongClick(e)
            super.onLongPress(e)
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > Companion.SWIPE_THRESHOLD && abs(velocityX) > Companion.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                    }
                } else {
                    if (abs(diffY) > Companion.SWIPE_THRESHOLD && abs(velocityY) > Companion.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown()
                        } else {
                            onSwipeUp()
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
    open fun onSwipeUp() {}
    open fun onSwipeDown() {}
    open fun onClick(e: MotionEvent) {}
    open fun onDoubleClick(e: MotionEvent) {}
    open fun onLongClick(e: MotionEvent) {}

}