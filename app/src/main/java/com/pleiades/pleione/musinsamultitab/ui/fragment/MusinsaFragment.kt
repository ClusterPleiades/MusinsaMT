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
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.pleiades.pleione.musinsamultitab.Config.Companion.KEY_CURRENT_TAB_TIME
import com.pleiades.pleione.musinsamultitab.Config.Companion.KEY_CURRENT_TAB_URL_STRING
import com.pleiades.pleione.musinsamultitab.Config.Companion.KEY_STACK
import com.pleiades.pleione.musinsamultitab.Config.Companion.PREFS
import com.pleiades.pleione.musinsamultitab.R

class MusinsaFragment : Fragment() {
    companion object {
        fun newInstance(): MusinsaFragment {
            return MusinsaFragment()
        }
    }

    private lateinit var rootView: View
    private lateinit var webView: WebView
    private lateinit var button: Button

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // initialize root view
        rootView = inflater.inflate(R.layout.fragment_musinsa, container, false)

        // initialize prefs
        val prefs = requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        // initialize url
        var urlString = prefs.getString(KEY_CURRENT_TAB_URL_STRING, null)
        if(urlString == null){
            urlString = "https://m.store.musinsa.com/"
            editor.putString(KEY_CURRENT_TAB_URL_STRING, "https://m.store.musinsa.com/")
            editor.putLong(KEY_CURRENT_TAB_TIME, System.currentTimeMillis())
            editor.apply()
        }

        // initialize web view
        webView = rootView.findViewById(R.id.web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                editor.putString(KEY_CURRENT_TAB_URL_STRING, request?.url.toString())
                editor.putLong(KEY_CURRENT_TAB_TIME, System.currentTimeMillis())
                editor.apply()
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl(urlString)

        // initialize button
        button = rootView.findViewById(R.id.button)
        button.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, TabFragment.newInstance())
                .addToBackStack(KEY_STACK)
                .commit()
        }

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