package com.hwei.home.ui.article

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.home.R
import com.hwei.home.databinding.ActivityArticleBinding
import com.hwei.lib_common.base.BaseActivity
import com.hwei.lib_common.router.HomeRouter

@Route(path = HomeRouter.article)
class ArticleActivity : BaseActivity<ActivityArticleBinding>() {
    @Autowired(name = "link")
    lateinit var link: String

    override fun setLayoutId(): Int {
        return R.layout.activity_article
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        binding.commonTitle.apply {
            setLeftClickListener {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    onBackPressed()
                }
            }
        }
        binding.webView.apply {
            loadUrl(link)
        }
    }

    override fun initData() {

    }

}