package com.toca.manualdebrincadeiras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BrincadeiraViewModel(
    private val dao: BrincadeiraDao
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.ID)
    private val _brincadeiras =
        _sortType.flatMapLatest { sortType ->
            when (sortType) {
                SortType.ID -> dao.getOrderedBrincadeiras("id", _state.value.nome)
                SortType.NOME -> dao.getOrderedBrincadeiras("nome", _state.value.nome)
                SortType.IDADE_MIN -> dao.getOrderedBrincadeiras("idade_min", _state.value.nome)
                SortType.IDADE_MAX -> dao.getOrderedBrincadeiras("idade_max", _state.value.nome)
                SortType.FAVORITO -> dao.getFavoriteBrincadeiras(_state.value.nome)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(BrincadeiraListState())
    val state =
        combine(_state, _sortType, _brincadeiras) { state, sortType, brincadeiras ->
            state.copy(
                brincadeiras = brincadeiras,
                sortType = sortType
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BrincadeiraListState())

    fun onEvent(event: BrincadeiraListEvent) {
        when (event) {
            is BrincadeiraListEvent.GetBrincadeiras -> {
                viewModelScope.launch {
                    dao.getBrincadeiras()
                }
            }

            is BrincadeiraListEvent.SortBrincadeiras -> {
                _sortType.value = event.sortType
            }

            is BrincadeiraListEvent.SetNome -> {
                _state.update {
                    it.copy(
                        nome = event.nome
                    )
                }
            }
        }
    }
}