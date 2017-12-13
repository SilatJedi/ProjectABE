package com.silatsaktistudios.projectabe

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //restricts the app to only access URL's that link to Arduboy games
    private var kidsMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        webView.webViewClient = object : WebViewClient() {
            //for SDK 19(kitkat)
            @Suppress("OverridingDeprecatedMember")
            @TargetApi(Build.VERSION_CODES.KITKAT)
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    if (kidsMode) {
                        if (it.contains("felipemanga.github.io/ProjectABE") &&
                                !it.contains("?url=new"))
                            view?.loadUrl(it)
                    } else {
                        view?.loadUrl(it)
                    }
                    return true
                }
                return false
            }
            //for SDK 21(lollipop) and higher
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                    view: WebView?, request: WebResourceRequest?
            ): Boolean {
                request?.let {
                    val url = it.url.toString()
                    if (kidsMode) {
                        if (url.contains("felipemanga.github.io/ProjectABE") &&
                                !url.contains("?url=new"))
                            view?.loadUrl(url)
                    } else {
                        view?.loadUrl(url)
                    }
                    return true
                }
                return false
            }
        }

        webView.settings.apply {
            @SuppressLint("SetJavaScriptEnabled")
            javaScriptEnabled = true
            allowFileAccess = true
        }

        webView.loadUrl("https://felipemanga.github.io/ProjectABE/")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you Sure?")
                .setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .setCancelable(false)
                .show()
    }
}
