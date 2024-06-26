package com.toca.manualdebrincadeiras.screens.brincadeiraShow.components

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView


class ShowGlossary(private val context: Context) {
    @JavascriptInterface
    fun showGlossaryTerm(id: Int) {
        Toast.makeText(context, "Glossary Term: $id", Toast.LENGTH_SHORT).show()
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContent(descricao: String) {

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
                        <a href="javascript:void(0)" onclick="showGlossary(1)">pegador</a>
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
