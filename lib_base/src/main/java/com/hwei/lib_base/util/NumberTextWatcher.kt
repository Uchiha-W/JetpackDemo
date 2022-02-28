package com.hwei.lib_base.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.net.Socket

/**
 * 限制最大值，最小值
 */
class NumberTextWatcher(
    val editText: EditText,
    private val maxValue: BigDecimal,
    private val minValue: BigDecimal = BigDecimal.ZERO
) :
    TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        if (s.isEmpty()) {
            return
        }
        if (s.length == 1 && s[0] == '.'){
            editText.setText("0.")
            editText.setSelection(editText.text.toString().length)
        }else {
            if (BigDecimal(s.toString()) > maxValue) {
                editText.setText(maxValue.stripTrailingZeros().toPlainString())
                editText.setSelection(editText.text.toString().length)
            } else if (BigDecimal(s.toString()) < minValue) {
                editText.setText(minValue.stripTrailingZeros().toPlainString())
                editText.setSelection(editText.text.toString().length)
            }
        }
    }
}