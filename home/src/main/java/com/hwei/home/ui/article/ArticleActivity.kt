package com.hwei.home.ui.article

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.home.R
import com.hwei.home.databinding.ActivityArticleBinding
import com.hwei.lib_base.base.BaseBindingActivity
import com.hwei.lib_base.ktx.dp2px
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.router.HomeRouter
import com.hwei.lib_common.widge.ProgressView
import dagger.hilt.android.AndroidEntryPoint

@Route(path = HomeRouter.article)
@AndroidEntryPoint
class ArticleActivity : BaseBindingActivity<ActivityArticleBinding>() {

    private val articleViewModel: ArticleViewModel by viewModels()

    @Autowired(name = "link")
    lateinit var link: String

    @Autowired(name = "id")
    @JvmField
    var id: Int = 0

    @Autowired
    lateinit var title: String

    @Autowired
    lateinit var author: String

    @Autowired
    @JvmField
    var collect: Boolean = false

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
            if (!collect) {
                setTvRight("收藏") {
                    articleViewModel.collectOuter(title, author, link)
                }
            } else {
                setTvRight("已收藏")
            }
        }
        binding.webView.apply {
            loadUrl(link)
        }
        val progressBar = ProgressView(this)
        progressBar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20.dp2px)
        binding.webView.addView(progressBar)
        binding.webView.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.start(newProgress)
            }
        }
    }

    override fun initData() {
        articleViewModel.collect.observe(this) {
            showToast("收藏成功")
        }
    }

    override fun setEvent() {

    }

}