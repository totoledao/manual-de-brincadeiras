package com.toca.manualdebrincadeiras.screens.brincadeiraShow.components

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(descricao: String, handleGlossary: (id: Int) -> Unit) {

    class ShowGlossary(private val context: Context) {
        @JavascriptInterface
        fun showGlossaryTerm(id: Int) {
            handleGlossary(id)
        }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = false
                settings.useWideViewPort = false
                settings.setSupportZoom(false)

                // Add the JavaScript interface
                addJavascriptInterface(ShowGlossary(context), "AndroidInterface")
            }
        },
        update = { webView ->
            val unEncodedHtml = """
                <html>
                    <head>
                        <style>
                            *, *:after, *:before {
                                margin: 0;
                                padding: 0;
                                box-sizing: border-box;
                            }                          
                            img {
                                width: 100%;
                                height: auto;
                                display: block;
                                margin: 0 auto;
                            }
                        </style>
                        <script type="text/javascript">
                            function showGlossary(term) {
                                AndroidInterface.showGlossaryTerm(term);
                            }
                        </script>
                    </head>
                    <body>
                        $descricao
                    </body>
                </html>
            """.trimIndent()
            webView.isVerticalScrollBarEnabled = false
            webView.loadDataWithBaseURL(
                null,
                unEncodedHtml,
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}
