package com.toca.manualdebrincadeiras.screens.brincadeiraList

sealed interface BrincadeiraListEvent {
    data class OnNameChange(val name: String) : BrincadeiraListEvent
    data class OnIsFavoriteChange(val isFavorite: Int?) : BrincadeiraListEvent
    data class OnTypeIdsChange(val typeIds: Int) : BrincadeiraListEvent
    data class OnThemeIdsChange(val themeIds: Int) : BrincadeiraListEvent
    data class OnAgeRangeChange(val ageRange: ClosedFloatingPointRange<Float>) :
        BrincadeiraListEvent
}