package com.bonnjalal.wikilearn

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.io.InputStream


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(url: String){
    // Adding a WebView inside AndroidView
    // with layout as full screen

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null


    var loaderDialogScreen by remember { mutableStateOf(false) }
    var loaderErrorScreen by remember { mutableStateOf(false) }

    var loadURL by remember {
        mutableStateOf("/")
    }
    val mutableStateTrigger = remember { mutableStateOf(false) }

    PageLoading(showLoader = loaderDialogScreen){showDialog ->
       loaderDialogScreen = showDialog
    }
    PageError(showLoader = loaderErrorScreen , setShowLoader = { showDialog ->
        loaderDialogScreen = showDialog
    }, backClick = {
        loaderErrorScreen = false
    }, reloadClick = {
        loaderErrorScreen = false
        webView?.reload()
    })


    val isRefreshing by remember {
        mutableStateOf(false)
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { webView?.reload() },
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            // content
            AndroidView( factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
//            webViewClient = WebViewClient()

                    webViewClient = object : WebViewClient() {

                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            backEnabled = view.canGoBack()

                            // show dialog
                            loaderDialogScreen = true
                        }

                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                            super.onReceivedError(view, request, error)

//                            backEnabled = view!!.canGoBack()
//                            Log.e("WEB ERROR", "error: ${error.toString()}")
//                            loadUrl("file:///android_asset/error.html")
//                    mutableStateTrigger.value = true

                            if(!isOnline(context)){
                                loaderErrorScreen = true
                            }
                        }

                        // Compose WebView Part 7 | Hide elements from web view
                        override fun onPageFinished(view: WebView?, url: String?) {

                            super.onPageFinished(view, url)
//                    removeElement(view!!)

//                    loadUrl("javascript:(function() { document.getElementsByClassName('learning-header')[0].style.display='none';})()")
                            loaderDialogScreen = false
                        }

                        override fun onLoadResource(view: WebView?, url: String?) {
                            super.onLoadResource(view, url)

                            injectCSS(context, view!!)
                            removeElement(view!!)

//                    getText(view!!)

                        }


                    }

//            postVisualStateCallback(
//                10L,
//                @RequiresApi(Build.VERSION_CODES.M)
//                object : VisualStateCallback() {
//                    override fun onComplete(requestId: Long) {
//                        if (requestId == 10L) {
////                            mWebView.setVisibility(View.VISIBLE)
//                            loadUrl("javascript:(function() { document.getElementsByClassName('learning-header')[0].style.display='none';})()")
//                        }
//                    }
//                })
                    // to enable JS
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false
                    settings.allowFileAccess = true

                    setInitialScale(1)
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true

                    // to verify that the client requesting your web page is actually your Android app.
                    settings.userAgentString = System.getProperty("http.agent") //Dalvik/2.1.0 (Linux; U; Android 11; M2012K11I Build/RKQ1.201112.002)


                    loadUrl(url)
                    webView = this
                }
            }, update = {
                webView = it
                //        it.loadUrl(url)
            })
        }

    }


//    if (mutableStateTrigger.value) {
//        WebViewPage(loadURL)
//    }


    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}

fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For 29 api or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
            else ->     false
        }
    }
    // For below 29 api
    else {
        @Suppress("DEPRECATION")
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

fun removeElement(webView: WebView) {



    try {



        webView.loadUrl("javascript:(function() { document.getElementsByClassName('course-title-lockup')[0].style.display='none';})()")

    } catch (e:Exception){

    }
//    webView.loadUrl("javascript:(function() { var a =  document.getElementsByClassName('logo'); for (index = 0; index < a.length; ++index) {a[index].style.display='none'; };})()")


}

//fun getText(webView: WebView) {
//
//
//    webView.evaluateJavascript(
//        "(function(){return window.document.body.innerHTML})();"
//    ) { html ->
//
//        if (html!=null) {
//            val doc: Document = Jsoup.parse(html)
//            val link: Element? = doc.select("span.d-block").first()
//
//            Log.d("HTML WEBVIEW", html)
//            if (link != null) {
//                val text: String = link.text() // "An example link"
//                Log.d("HTML WEBVIEW", text)
//            }
//            // code here
//        }
//    }
//}

private fun injectCSS(context: Context, webView: WebView) {
    try {
        val inputStream: InputStream = context.assets.open("style.css")
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        val encoded: String = Base64.encodeToString(buffer, Base64.NO_WRAP)
        webView.loadUrl(
            "javascript:(function() {" +
                    "var test = document.getElementsByTagName('head');" +
                    "for (var counter = 0; counter < test.length; counter++){" +

                    "       var parent = document.getElementsByTagName('head').item(counter);" +

//                    "       var meta = document.createElement('meta');" +
//                    "       meta.name = 'viewport'" +
//                    "       meta.content = 'width=device-width, initial-scale=1'" +
//                    "       parent.appendChild(meta)" +


                    "       var style = document.createElement('style');" +
                    "       style.type = 'text/css';" +  // Tell the browser to BASE64-decode the string into your script !!!
                    "       style.innerHTML = window.atob('" + encoded + "');" +
                    "       parent.appendChild(style)" +


                    "}" +

//                    let div = document.querySelector(".wrapper")
//                    "var target = document.getElementsByClassName('target');" +
//                    "var target = document.querySelectorAll('.xblock--drag-and-drop .target);" +
//                    "for (var i=0;i<target.length;i+=1){" +
//                        "target[i].style.height = '300px';" +
//                    "}"+

                    "})()"
        )

//        webView.loadUrl(
//            "javascript:android.onData(function() {" +
//                    "return new Promise((resolve, reject)=>{" +
//                    "    var querySelector = '.xblock--drag-and-drop .target';" +
//                    "    var timeout = 10000;" +
//                    "    var timer = false;" +
//                    "    if(document.querySelectorAll(querySelector).length) return resolve();" +
//                    "    const observer = new MutationObserver(()=>{" +
//                    "        if(document.querySelectorAll(querySelector).length){" +
//                    "            observer.disconnect();" +
//                    "            if(timer !== false) clearTimeout(timer);" +
//                    "            return resolve();" +
//                    "        }" +
//                    "    });" +
//                    "    observer.observe(document.body, {" +
//                    "            childList: true," +
//                    "            subtree: true" +
//                    "    });" +
//                    "    if(timeout) timer = setTimeout(()=>{" +
//                    "        observer.disconnect();" +
//                    "        reject();" +
//                    "    }, timeout);" +
//                    "});" +
//
//                    "})()"
//        )

    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

@JavascriptInterface
fun onData(value: Any?) {
    //.. do something with the data
    Log.e("ONDATA", "javascript data $value"  )
}


