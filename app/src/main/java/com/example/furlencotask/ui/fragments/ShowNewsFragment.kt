package com.example.furlencotask.ui.fragments

import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.furlencotask.R
import com.example.furlencotask.data.constants.AppConstants
import kotlinx.android.synthetic.main.fragment_show_news.*

class ShowNewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        initListeners()
    }

    private fun setupViews(){
        val url = arguments?.get(AppConstants.NEWS_URL) as String
        wv_news.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                pb_webview?.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                pb_webview?.visibility = View.VISIBLE
            }
        }

        wv_news.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                setProgress(newProgress)
                super.onProgressChanged(view, newProgress)
            }
        }
        wv_news.settings.javaScriptEnabled = true
        wv_news.loadUrl(url)
    }

    private fun initListeners(){
        wv_news.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                val webView = v as WebView
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        if (webView.canGoBack()) {
                            webView.goBack()
                            true
                        } else
                            false
                    }
                    else -> false
                }
            } else
                false
        }
    }

    override fun onPause() {
        wv_news.onPause()
        super.onPause()
    }

    fun setProgress(value: Int) {
        pb_webview?.progress = value
    }

    companion object {
        @JvmStatic
        fun newInstance(newsUrl:String) =
            ShowNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(AppConstants.NEWS_URL, newsUrl)
                }
            }
    }
}