package com.hwei.lib_base.router.Interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.router.HomeRouter
import com.hwei.lib_base.router.extra.Extra

/**
 * 全局登录验证拦截器
 */
@Interceptor(priority = 8)
class LoginInterceptor : IInterceptor {
    private var context: Context? = null
    override fun init(context: Context?) {
        this.context = context
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val logined = false
        postcard.let {
            if (it.extra == Extra.NEED_LOGIN) {
                if (logined) {
                    callback.onContinue(it)
                } else {
                    ARouter.getInstance().build(HomeRouter.article).withString("link", "123")
                        .navigation(context)
                }
            } else {
                callback.onContinue(postcard)
            }
        }
    }
}