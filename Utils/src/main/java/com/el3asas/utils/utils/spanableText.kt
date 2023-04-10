package com.el3asas.utils.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.el3asas.utils.R

fun setAsClickableSpannable(
    textView: TextView,
    text: String,
    startIndex: Int,
    endIndex: Int,
    listener: () -> Unit,
) {
    val ss = SpannableString(text)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }

        override fun onClick(p0: View) {
            listener()
        }
    }

    ss.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.text = ss
    textView.movementMethod = LinkMovementMethod.getInstance()
}


@BindingAdapter(
    "app:spannableText",
    "app:startIndex1",
    "app:endIndex1",
    "app:startIndex2",
    "app:endIndex2",
    "app:endIndex2",
    "app:spannableListener",
    requireAll = false
)
fun setAsClickableSpannable(
    textView: TextView,
    text: String,
    startIndex1: Int,
    endIndex1: Int,
    startIndex2: Int? = null,
    endIndex2: Int? = null,
    listener: (View) -> Unit,
) {
    val ss = SpannableString(text)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }

        override fun onClick(p0: View) {
            listener(p0)
        }
    }

    ss.setSpan(clickableSpan, startIndex1, endIndex1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    if (startIndex2 != null && endIndex2 != null) {
        ss.setSpan(clickableSpan, startIndex2, endIndex2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    textView.text = ss
    textView.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter(
    "app:addReadMoreToTextView",
    "app:addMax",
    "app:addListener",
    "app:isForSeeMore",
    requireAll = false
)
fun addReadMoreToTextView(
    textView: TextView,
    text: String?,
    max: Int,
    listener: ClickSeeMoreListener? = null,
    isForSeeMore: Boolean
) {
    try {
        if (text != "null") {
            val readMoreText = textView.resources.getString(R.string.seeMore)
            val readLess = textView.resources.getString(R.string.seeLess)
            if (text != null && text.length > max) {
                val showText = if (isForSeeMore) "${text.subSequence(0, max)}" else text
                val hintText = if (isForSeeMore) SpannableString("$showText $readMoreText")
                else SpannableString("$showText $readLess")
                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }

                    override fun onClick(p0: View) {
                        if (listener != null) listener.onSeeMoreClick(p0)
                        else {
                            textView.text = text
                            addReadMoreToTextView(textView, text, max, null, !isForSeeMore)
                        }
                    }
                }
                hintText.setSpan(
                    clickableSpan,
                    showText.length,
                    hintText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView.text = hintText
                textView.movementMethod = LinkMovementMethod.getInstance()
            } else {
                textView.text = text
            }
        }
    } catch (ignored: Exception) {
    }
}

interface ClickSeeMoreListener {
    fun onSeeMoreClick(v: View)
}