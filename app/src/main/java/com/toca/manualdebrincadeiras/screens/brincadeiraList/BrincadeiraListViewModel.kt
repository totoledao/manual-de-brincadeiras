package com.toca.manualdebrincadeiras.screens.brincadeiraList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toca.manualdebrincadeiras.database.Database
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BrincadeiraListViewModel(
    private val dao: Database
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.ID)
    private val _searchQuery = MutableStateFlow("")
    private val _brincadeiras = combine(_sortType, _searchQuery) { sortType, query ->
        sortType to query
    }.flatMapLatest { (sortType, query) ->
        when (sortType) {
            SortType.ID -> dao.brincadeiraDao.getOrderedBrincadeiras("id", query)
            SortType.NOME -> dao.brincadeiraDao.getOrderedBrincadeiras("nome", query)
            SortType.IDADE_MIN -> dao.brincadeiraDao.getOrderedBrincadeiras("idade_min", query)
            SortType.IDADE_MAX -> dao.brincadeiraDao.getOrderedBrincadeiras("idade_max", query)
            SortType.FAVORITO -> dao.brincadeiraDao.getFavoriteBrincadeiras(query)
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
                    dao.brincadeiraDao.getBrincadeiras()
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
                _searchQuery.value = event.nome
            }
        }
    }
}