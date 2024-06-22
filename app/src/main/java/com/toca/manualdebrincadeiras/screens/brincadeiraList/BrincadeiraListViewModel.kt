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
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class)
class BrincadeiraListViewModel(private val dao: Database) : ViewModel() {
    private val _name = MutableStateFlow("")
    private val _minAge = MutableStateFlow(3)
    private val _maxAge = MutableStateFlow(16)
    private val _isFavorite = MutableStateFlow<Int?>(null)
    private val _typeIds = MutableStateFlow<List<Int>>(emptyList())
    private val _themeIds = MutableStateFlow<List<Int>>(emptyList())

    private val _tipos = dao.tipoDao.getTipos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _temas = dao.temaDao.getTemas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _brincadeiras = combine(
        _name,
        _minAge,
        _maxAge,
        _isFavorite,
        _typeIds,
        _themeIds
    ) { any: Array<Any?> ->
        BrincadeiraFilter(
            any[0] as String,
            any[1] as Int,
            any[2] as Int,
            any[3] as Int?,
            (any[4] as? List<*>)?.filterIsInstance<Int>() ?: emptyList(),
            (any[5] as? List<*>)?.filterIsInstance<Int>() ?: emptyList()
        )
    }.flatMapLatest { filter ->
        dao.brincadeiraDao.getBrincadeiras(
            name = filter.name,
            minAge = filter.minAge,
            maxAge = filter.maxAge,
            isFavorite = filter.isFavorite,
            typeIds = filter.typeIds,
            typeIdsSize = filter.typeIds.size,
            themeIds = filter.themeIds,
            themeIdsSize = filter.themeIds.size
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(BrincadeiraListState())
    val state = combine(
        _state,
        _brincadeiras,
        _tipos,
        _temas
    ) { state, brincadeiras, tipos, temas ->
        state.copy(
            brincadeiras = brincadeiras,
            tipos = tipos,
            temas = temas,
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

            is BrincadeiraListEvent.OnIsFavoriteChange -> {
                _isFavorite.value = event.isFavorite
                _state.value = _state.value.copy(
                    isFavorite = event.isFavorite,
                )
            }

            is BrincadeiraListEvent.OnTypeIdsChange -> {
                handleChangeId(_typeIds, _state, event.typeIds, true)
            }

            is BrincadeiraListEvent.OnThemeIdsChange -> {
                handleChangeId(_themeIds, _state, event.themeIds, false)
            }

            is BrincadeiraListEvent.OnAgeRangeChange -> {
                _minAge.value = event.ageRange.start.roundToInt()
                _maxAge.value = event.ageRange.endInclusive.roundToInt()
                _state.value = _state.value.copy(
                    minAge = event.ageRange.start.roundToInt(),
                    maxAge = event.ageRange.endInclusive.roundToInt(),
                    ageRange = event.ageRange.start.roundToInt()
                        .toFloat()..event.ageRange.endInclusive.roundToInt().toFloat()
                )
            }
        }
    }
}

data class BrincadeiraFilter(
    val name: String,
    val minAge: Int,
    val maxAge: Int,
    val isFavorite: Int?,
    val typeIds: List<Int>,
    val themeIds: List<Int>
)

fun handleChangeId(
    idList: MutableStateFlow<List<Int>>,
    state: MutableStateFlow<BrincadeiraListState>,
    id: Int,
    isTypeId: Boolean
) {
    val newList =
        if (idList.value.contains(id)) idList.value.filter { it != id }
        else idList.value + id

    idList.value = newList
    if (isTypeId) {
        state.value = state.value.copy(
            typeIds = newList,
        )
    } else {
        state.value = state.value.copy(
            themeIds = newList,
        )
    }
}
