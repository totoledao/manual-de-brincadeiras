package com.toca.manualdebrincadeiras.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "brincadeiras")
data class Brincadeira(
    val nome: String,
    val favorito: Int,
    val idade_min: Int,
    val idade_max: Int,
    val descricao: String,
    val integrantes: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

data class BrincadeiraIndex(
    val nome: String,
    val id: Int,
)

@Entity(tableName = "tipos")
data class Tipo(
    val nome: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

@Entity(
    tableName = "brincadeiras_tipos",
    primaryKeys = ["brincadeira_id", "tipo_id"],
    foreignKeys = [
        ForeignKey(
            entity = Brincadeira::class,
            parentColumns = ["id"],
            childColumns = ["brincadeira_id"]
        ),
        ForeignKey(entity = Tipo::class, parentColumns = ["id"], childColumns = ["tipo_id"])
    ],
    indices = [Index(value = ["brincadeira_id"]), Index(value = ["tipo_id"])]
)
data class BrincadeiraTipo(
    val brincadeira_id: Int,
    val tipo_id: Int
)

@Entity(tableName = "temas")
data class Tema(
    val nome: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

@Entity(
    tableName = "brincadeiras_temas",
    primaryKeys = ["brincadeira_id", "tema_id"],
    foreignKeys = [
        ForeignKey(
            entity = Brincadeira::class,
            parentColumns = ["id"],
            childColumns = ["brincadeira_id"]
        ),
        ForeignKey(
            entity = Tema::class,
            parentColumns = ["id"],
            childColumns = ["tema_id"]
        )
    ],
    indices = [Index(value = ["brincadeira_id"]), Index(value = ["tema_id"])]
)
data class BrincadeiraTema(
    val brincadeira_id: Int,
    val tema_id: Int
)

@Entity(tableName = "glossario")
data class Definicao(
    val nome: String,
    val descricao: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)