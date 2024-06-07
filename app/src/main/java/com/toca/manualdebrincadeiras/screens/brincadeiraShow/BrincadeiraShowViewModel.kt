package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toca.manualdebrincadeiras.database.Database
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class BrincadeiraShowViewModel(
    private val dao: Database,
) : ViewModel() {
    private val _id = MutableStateFlow<Int>(0)
    private val _brincadeira = _id.flatMapLatest { id ->
        dao.brincadeiraDao.showBrincadeira(id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _state = MutableStateFlow(BrincadeiraShowState())
    val state = combine(_state, _brincadeira, _id) { state, brincadeira, id ->
        state.copy(
            brincadeira = brincadeira,
            id = id
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BrincadeiraShowState())

    fun onEvent(event: BrincadeiraShowEvent) = when (event) {
        is BrincadeiraShowEvent.ShowBrincadeira -> {
            _id.value = event.id
        }
    }

}