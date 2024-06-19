package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import com.toca.manualdebrincadeiras.database.BrincadeiraAllData

data class BrincadeiraShowState(
    val id: Int = 0,
    val brincadeira: BrincadeiraAllData? = null,
)
