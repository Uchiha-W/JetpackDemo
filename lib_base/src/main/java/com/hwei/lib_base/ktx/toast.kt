package com.hwei.lib_base.ktx

import android.widget.Toast
import com.hwei.lib_base.BaseApplication


fun showToast(msg: String) = Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_SHORT).show()