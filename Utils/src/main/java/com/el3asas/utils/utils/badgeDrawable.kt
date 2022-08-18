package com.el3asas.utils.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.core.content.ContextCompat
import com.el3asas.utils.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlin.math.max

class BadgeDrawable(context: Context) : Drawable() {
    private val mBadgePaint: Paint
    private val mBadgePaint1: Paint
    private val mTextPaint: Paint
    private val mTxtRect = Rect()
    private var mCount = ""
    private var mWillDraw = false
    override fun draw(canvas: Canvas) {
        if (!mWillDraw) {
            return
        }
        val bounds = bounds
        val width = bounds.right - bounds.left.toFloat()
        val height = bounds.bottom - bounds.top.toFloat()

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */
        val radius = max(width, height) / 2 / 2
        val centerX = width - radius - 1 + 5
        val centerY = radius - 5
        if (mCount.length <= 2) {
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, (radius + 7.5).toFloat(), mBadgePaint1)
            canvas.drawCircle(centerX, centerY, (radius + 5.5).toFloat(), mBadgePaint)
        } else {
            canvas.drawCircle(centerX, centerY, (radius + 8.5).toFloat(), mBadgePaint1)
            canvas.drawCircle(centerX, centerY, (radius + 6.5).toFloat(), mBadgePaint)
            //	        	canvas.drawRoundRect(radius, radius, radius, radius, 10, 10, mBadgePaint);
        }
        // Draw badge count text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length, mTxtRect)
        val textHeight = mTxtRect.bottom - mTxtRect.top.toFloat()
        val textY = centerY + textHeight / 2f
        if (mCount.length > 2) canvas.drawText(
            "99+",
            centerX,
            textY,
            mTextPaint
        ) else canvas.drawText(mCount, centerX, textY, mTextPaint)
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    fun setCount(count: String) {
        mCount = count

        // Only draw a badge if there are notifications.
        mWillDraw = !count.equals("0", ignoreCase = true)
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        // do nothing
    }

    override fun setColorFilter(cf: ColorFilter?) {
        // do nothing
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    init {
        val mTextSize = context.resources.getDimension(R.dimen.secondFont)
        mBadgePaint = Paint()
        mBadgePaint.color = ContextCompat.getColor(context, R.color.primaryColor)
        mBadgePaint.isAntiAlias = true
        mBadgePaint.style = Paint.Style.FILL
        mBadgePaint1 = Paint()
        mBadgePaint1.color =
            ContextCompat.getColor(context.applicationContext, R.color.grey)
        mBadgePaint1.isAntiAlias = true
        mBadgePaint1.style = Paint.Style.FILL
        mTextPaint = Paint()
        mTextPaint.color = Color.WHITE
        mTextPaint.typeface = Typeface.DEFAULT
        mTextPaint.textSize = mTextSize
        mTextPaint.isAntiAlias = true
        mTextPaint.textAlign = Paint.Align.CENTER
    }
}

fun setBadgeCount(context: Context, icon: LayerDrawable, count: String, navView: NavigationView) {
    // Reuse drawable if possible
    val reuse: Drawable? = icon.findDrawableByLayerId(R.id.ic_badge)
    val badge: BadgeDrawable = if (reuse != null && reuse is BadgeDrawable) {
        reuse
    } else {
        BadgeDrawable(context)
    }
    badge.setCount(count)
    icon.mutate()
    icon.setDrawableByLayerId(R.id.ic_badge, badge)

    navView.invalidate()

}

fun setBadgeCount(context: Context, icon: LayerDrawable, count: String, navView: BottomNavigationView) {
    // Reuse drawable if possible
    val reuse: Drawable? = icon.findDrawableByLayerId(R.id.ic_badge)
    val badge: BadgeDrawable = if (reuse != null && reuse is BadgeDrawable) {
        reuse
    } else {
        BadgeDrawable(context)
    }
    badge.setCount(count)
    icon.mutate()
    icon.setDrawableByLayerId(R.id.ic_badge, badge)

    navView.invalidate()

}