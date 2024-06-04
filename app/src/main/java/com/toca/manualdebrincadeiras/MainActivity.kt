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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.toca.manualdebrincadeiras.database.BrincadeiraDatabase
import com.toca.manualdebrincadeiras.screens.brincadeiraList.BrincadeiraListView
import com.toca.manualdebrincadeiras.screens.brincadeiraList.BrincadeiraListViewModel
import com.toca.manualdebrincadeiras.screens.brincadeiraShow.BrincadeiraShowView
import com.toca.manualdebrincadeiras.screens.brincadeiraShow.BrincadeiraShowViewModel
import com.toca.manualdebrincadeiras.ui.theme.ManualDeBrincadeirasTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            BrincadeiraDatabase::class.java, "brincadeiras.db"
        ).createFromAsset("brincadeiras.db").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManualDeBrincadeirasTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.Home.name,
                ) {

                    composable(route = Routes.Home.name) {
                        val viewModel by viewModels<BrincadeiraListViewModel>(
                            factoryProducer = {
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        if (modelClass.isAssignableFrom(BrincadeiraListViewModel::class.java)) {
                                            @Suppress("UNCHECKED_CAST")
                                            return BrincadeiraListViewModel(db.dao) as T

                                        }
                                        throw IllegalArgumentException("Unknown ViewModel class")
                                    }
                                }
                            }
                        )
                        val state by viewModel.state.collectAsState()

                        BrincadeiraListView(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }

                    composable(
                        route = Routes.Show.name + "/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val id = it.arguments?.getInt("id") ?: 0
                        val viewModel by viewModels<BrincadeiraShowViewModel>(
                            factoryProducer = {
                                object : ViewModelProvider.Factory {
                                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        if (modelClass.isAssignableFrom(BrincadeiraShowViewModel::class.java)) {
                                            @Suppress("UNCHECKED_CAST")
                                            return BrincadeiraShowViewModel(
                                                db.dao,
                                                id
                                            ) as T

                                        }
                                        throw IllegalArgumentException("Unknown ViewModel class")
                                    }
                                }
                            }
                        )
                        val state by viewModel.state.collectAsState()
                        Log.d("INIT", state.toString())
                        val newState = state.copy(
                            id = id,
                        )

                        BrincadeiraShowView(
                            state = newState,
                            onEvent = viewModel::onEvent,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}