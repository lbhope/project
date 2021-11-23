package com.xiangxue.kotlinproject

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.xiangxue.kotlinproject.config.Flag
import kotlinx.android.synthetic.main.activity_detail_link.*

// TODO WebView
class DetailLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_link)
        supportActionBar?.hide() // 隐藏ActionBar

        // 1.获取上个页面传递过来的url
        val mUrl: String = intent.getStringExtra(Flag.URL_KEY)

        // 2.设置WebView的一些参数
        val mWebSettings : WebSettings = web_view.settings // 获取WebView参数设置
        mWebSettings.useWideViewPort = false  // 将图片调整到适合webview的大小
        mWebSettings.javaScriptEnabled = true // 支持js
        mWebSettings.loadsImagesAutomatically = true  // 支持自动加载图片

        // 3.利用WebView直接加载网页链接
        // 每次启动这个activity 所加载的url网页路径肯定是不一样的 ， Intent传值
        web_view.loadUrl(mUrl)

    }

}