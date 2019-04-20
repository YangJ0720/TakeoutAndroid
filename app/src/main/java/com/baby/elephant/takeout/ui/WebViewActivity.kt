package com.baby.elephant.takeout.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout

/**
 * @author YangJ
 */
class WebViewActivity : AppCompatActivity() {

    private lateinit var mWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LinearLayout(this)
        view.orientation = LinearLayout.VERTICAL
        mWebView = createWebView()
        view.addView(mWebView)
        setContentView(view)
    }

    private fun createWebView(): WebView {
        val webView = WebView(application)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                println("onPageFinished")
            }
        }
        webView.webChromeClient = object : WebChromeClient() {

        }
        webView.loadUrl("https://www.hao123.com")
        return webView
    }

    override fun onDestroy() {
        mWebView.loadDataWithBaseURL(
            null, null,
            "text/html", "utf-8", null
        )
        mWebView.clearCache(true)
        mWebView.clearHistory()
        val parentView = mWebView.parent
        if (parentView != null && parentView is ViewGroup) {
            parentView.removeView(mWebView)
            mWebView.destroy()
        }
        super.onDestroy()
    }
}
