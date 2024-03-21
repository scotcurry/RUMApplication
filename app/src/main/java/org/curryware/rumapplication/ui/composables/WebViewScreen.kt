package org.curryware.rumapplication.ui.composables

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.datadog.android.webview.WebViewTracking


@Composable
fun WebViewScreen() {

    val url = "https://p4o643dcn3.execute-api.us-east-1.amazonaws.com/Prod/hello"
    AndroidView(factory =
        {WebView(it).apply {
            WebViewTracking.enable(this, listOf("p4o643dcn3.execute-api.us-east-1.amazonaws.com"))
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}