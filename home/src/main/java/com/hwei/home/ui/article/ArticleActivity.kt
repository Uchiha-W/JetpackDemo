package com.hwei.home.ui.article

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.home.R
import com.hwei.home.databinding.ActivityArticleBinding
import com.hwei.lib_base.base.BaseActivity
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.router.HomeRouter
import dagger.hilt.android.AndroidEntryPoint

@Route(path = HomeRouter.article)
@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    private val articleViewModel: ArticleViewModel by viewModels()

    @Autowired(name = "link")
    lateinit var link: String

    @Autowired(name = "id")
    @JvmField
    var id: Int = 0

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
            setRightClickListener {
                articleViewModel.collect(id)
            }
        }
        binding.webView.apply {
            loadUrl(link)
        }
    }

    override fun initData() {
        articleViewModel.collect.observe(this) {
            showToast("收藏成功")
        }
    }

}