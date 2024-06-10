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

@OptIn(ExperimentalCoroutinesApi::class)
class BrincadeiraListViewModel(private val dao: Database) : ViewModel() {
    private val _name = MutableStateFlow("")
    private val _minAge = MutableStateFlow(3)
    private val _maxAge = MutableStateFlow(16)
    private val _isFavorite = MutableStateFlow<Int?>(null)
    private val _typeIds = MutableStateFlow<List<Int>?>(null)

    private val _tipos = dao.tipoDao.getTipos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _brincadeiras =
        combine(
            _name,
            _minAge,
            _maxAge,
            _isFavorite,
            _typeIds
        ) { name, minAge, maxAge, isFavorite, typeIds ->
            BrincadeiraFilter(name, minAge, maxAge, isFavorite, typeIds)
        }.flatMapLatest { filter ->
            dao.brincadeiraDao.getBrincadeiras(
                name = filter.name,
                minAge = filter.minAge,
                maxAge = filter.maxAge,
                isFavorite = filter.isFavorite,
                typeIds = filter.typeIds,
                typeIdsSize = filter.typeIds?.size ?: 0
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(BrincadeiraListState())
    val state = combine(
        _state,
        _brincadeiras,
        _tipos
    ) { state,
        brincadeiras,
        tipos ->
        state.copy(
            brincadeiras = brincadeiras,
            tipos = tipos,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BrincadeiraListState())

    fun onEvent(event: BrincadeiraListEvent) {
        when (event) {
            is BrincadeiraListEvent.OnNameChange -> {
                _name.value = event.name
                _state.value = _state.value.copy(
                    name = event.name,
                )
            }

            is BrincadeiraListEvent.OnMinAgeChange -> {
                _minAge.value = event.minAge
                _state.value = _state.value.copy(
                    minAge = event.minAge,
                )
            }

            is BrincadeiraListEvent.OnMaxAgeChange -> {
                _maxAge.value = event.maxAge
                _state.value = _state.value.copy(
                    maxAge = event.maxAge,
                )
            }

            is BrincadeiraListEvent.OnIsFavoriteChange -> {
                _isFavorite.value = event.isFavorite
                _state.value = _state.value.copy(
                    isFavorite = event.isFavorite,
                )
            }

            is BrincadeiraListEvent.OnTypeIdsChange -> {
                handleChangeTypeId(_typeIds, _state, event.typeIds)
            }
        }
    }
}

data class BrincadeiraFilter(
    val name: String,
    val minAge: Int,
    val maxAge: Int,
    val isFavorite: Int?,
    val typeIds: List<Int>?
)

fun handleChangeTypeId(
    idList: MutableStateFlow<List<Int>?>,
    state: MutableStateFlow<BrincadeiraListState>,
    id: Int
) {
    val newList = if (idList.value == null) listOf(id)
    else
        if (idList.value!!.contains(id))
            if (idList.value!!.size == 1) null
            else idList.value!!.filter { it != id }
        else idList.value!! + id

    idList.value = newList
    state.value = state.value.copy(
        typeIds = newList,
    )
}
