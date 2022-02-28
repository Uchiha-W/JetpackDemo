package com.hwei.lib_base.util

import android.text.InputFilter
import android.text.Spanned
import java.math.BigDecimal
import java.util.regex.Pattern

/**
 * 限制小数位长度
 */
class NumberInputFilter(
    private val pointLength: Int
) : InputFilter {

    private val p: Pattern = Pattern.compile("[0-9]*")

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source.isEmpty()) {
            return null
        }
        if (source.contains(".") && pointLength == 0) {
            return ""
        }

        if (dest.contains(".") && source.contains(".")) {
            return ""
        }

        val matcher = p.matcher(source)
        if (!matcher.matches()) {
            return source
        } else {
            val next = dest.substring(0, dstart) + source + dest.substring(dstart)
            if (BigDecimal(next).scale() > pointLength) {
                return ""
            } else {
                return null
            }
        }
    }
}