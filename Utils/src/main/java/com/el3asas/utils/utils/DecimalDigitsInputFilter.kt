package com.moqawlat.app.helpers

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class DecimalDigitsInputFilter(digitsAfterZero: Int) : InputFilter {
    private val mPattern: Pattern

    init {
        mPattern = Pattern.compile("[0-9]+(\\.[0-9]{0,$digitsAfterZero})?")
    }

    override fun filter(
        source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int
    ): CharSequence? {
        val input = dest.toString() + source.toString()
        val matcher = mPattern.matcher(input)
        return if (!matcher.matches()) {
            ""
        } else null
    }
}