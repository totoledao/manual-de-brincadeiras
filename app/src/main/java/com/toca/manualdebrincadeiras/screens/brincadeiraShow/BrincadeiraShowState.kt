package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import com.toca.manualdebrincadeiras.database.BrincadeiraAllData
import com.toca.manualdebrincadeiras.database.Definicao

data class BrincadeiraShowState(
    val id: Int = 0,
    val brincadeira: BrincadeiraAllData? = null,
    val definicao: Definicao = Definicao("Nome", "Descrição", 0),
    val showBottomModal: Boolean = false,
)
