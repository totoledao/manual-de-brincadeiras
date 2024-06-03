package com.toca.manualdebrincadeiras.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brincadeiras")
data class Brincadeira(
    val nome: String,
    val favorito: Int,
    val idade_min: Int,
    val idade_max: Int,
    val descricao: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

data class BrincadeiraIndex(
    val nome: String,
    val id: Int,
)
