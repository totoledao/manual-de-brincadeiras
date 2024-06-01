package com.toca.manualdebrincadeiras

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.toca.manualdebrincadeiras.database.BrincadeiraDatabase
import com.toca.manualdebrincadeiras.screens.brincadeiraList.BrincadeiraListView
import com.toca.manualdebrincadeiras.screens.brincadeiraList.BrincadeiraViewModel
import com.toca.manualdebrincadeiras.ui.theme.ManualDeBrincadeirasTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BrincadeiraDatabase::class.java, "brincadeiras.db"
        ).createFromAsset("brincadeiras.db").build()
    }

    private val viewModel by viewModels<BrincadeiraViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(BrincadeiraViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return BrincadeiraViewModel(db.dao) as T

                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManualDeBrincadeirasTheme {
                val state by viewModel.state.collectAsState()
                Log.d("TESTE_VIEW", state.toString())

                BrincadeiraListView(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}