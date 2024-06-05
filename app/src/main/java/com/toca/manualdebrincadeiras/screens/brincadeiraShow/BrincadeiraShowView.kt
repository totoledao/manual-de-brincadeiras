package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@Composable
fun BrincadeiraShowView(
    state: BrincadeiraShowState,
    onEvent: (BrincadeiraShowEvent) -> Unit,
    navController: NavHostController
) {
    Scaffold { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Go back",
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )

                    FavoriteIcon(isFavorite = state.brincadeira?.favorito == 1)
                }
            }
            item {
                Column {
                    Text(text = state.brincadeira?.id.toString())
                    Text(text = state.brincadeira?.nome ?: "")
                    Text(text = state.brincadeira?.idade_min.toString())
                    Text(text = state.brincadeira?.idade_max.toString())
                    WebViewScreen(state.brincadeira?.descricao ?: "")
                }
            }

        }
    }
}

@Composable
fun FavoriteIcon(isFavorite: Boolean) {
    Icon(
        imageVector = if (isFavorite) {
            Icons.Filled.Favorite
        } else {
            Icons.Filled.FavoriteBorder
        },
        contentDescription = "Favorite",
    )
}


@Composable
fun WebViewScreen(descricao: String) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = false
                settings.loadWithOverviewMode = false
                settings.useWideViewPort = false
                settings.setSupportZoom(false)
            }
        },
        update = { webView ->
            val unEncodedHtml = """
                <html>
                <head>
                        <style>
                            img {
                                width: 100%;
                                height: auto;
                                display: block;
                                margin: 0 auto;
                            }
                        </style>
                    </head>
                    <body style='margin: 0; padding: 0;'>
                        $descricao                        
                    </body>
                </html>
            """.trimIndent()
            webView.loadDataWithBaseURL(
                "file:///android_asset/images/",
                unEncodedHtml,
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}