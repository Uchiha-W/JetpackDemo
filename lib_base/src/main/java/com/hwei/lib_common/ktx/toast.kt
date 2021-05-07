package com.hwei.lib_common.ktx

import android.widget.Toast
import com.hwei.lib_common.BaseApplication


fun showToast(msg: String) = Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_SHORT).show()