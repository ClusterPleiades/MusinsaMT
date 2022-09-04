package com.pleiades.pleione.musinsamultitab.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.pleiades.pleione.musinsamultitab.R

class MusinsaFragment : Fragment() {
    companion object {
        fun newInstance(): MusinsaFragment {
            return MusinsaFragment()
        }
    }

    private lateinit var rootView: View
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // initialize root view
        rootView = inflater.inflate(R.layout.fragment_musinsa, container, false)

        // initialize web view
        webView = rootView.findViewById(R.id.web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl("https://m.store.musinsa.com/")

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // initialize on back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack())
                    webView.goBack()
                else
                    requireActivity().finish()
            }
        })
    }
}