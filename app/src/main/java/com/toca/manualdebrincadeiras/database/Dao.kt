package com.toca.manualdebrincadeiras.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BrincadeiraDao {
    @Query(
        """
        SELECT b.id, b.nome 
        FROM brincadeiras b
        LEFT JOIN brincadeiras_tipos bt ON b.id = bt.brincadeira_id
        LEFT JOIN brincadeiras_temas btm ON b.id = btm.brincadeira_id
        WHERE (b.nome LIKE '%' || :name || '%')
        AND (b.idade_min >= :minAge)
        AND (b.idade_max <= :maxAge)
        AND (:isFavorite IS NULL OR b.favorito = :isFavorite)
        AND (
            :typeIdsSize = 0 OR b.id IN (
                SELECT brincadeira_id
                FROM brincadeiras_tipos
                WHERE tipo_id IN (:typeIds)
                GROUP BY brincadeira_id
                HAVING COUNT(DISTINCT tipo_id) = :typeIdsSize
            )
        )
        AND (
            :themeIdsSize = 0 OR b.id IN (
                SELECT brincadeira_id
                FROM brincadeiras_temas
                WHERE tema_id IN (:themeIds)
                GROUP BY brincadeira_id
                HAVING COUNT(DISTINCT tema_id) = :themeIdsSize
            )
        )
        GROUP BY b.id
        ORDER BY b.nome ASC
        """
    )
    fun getBrincadeiras(
        name: String,
        minAge: Int,
        maxAge: Int,
        isFavorite: Int?,
        typeIds: List<Int>,
        typeIdsSize: Int,
        themeIds: List<Int>,
        themeIdsSize: Int
    ): Flow<List<BrincadeiraIndex>>

    @Transaction
    @Query("SELECT * FROM brincadeiras WHERE id = :brincadeiraId")
    fun showBrincadeira(brincadeiraId: Int): Flow<BrincadeiraAllData>

    @Query("UPDATE brincadeiras SET favorito = :fav WHERE id = :brincadeiraId")
    suspend fun updateFavorite(brincadeiraId: Int, fav: Int): Void
}

@Dao
interface TipoDao {
    @Query("SELECT * FROM tipos")
    fun getTipos(): Flow<List<Tipo>>
}

@Dao
interface TemaDao {
    @Query("SELECT * FROM temas")
    fun getTemas(): Flow<List<Tema>>
}

@Dao
interface GlossarioDao {
    @Query("SELECT * FROM glossario WHERE id = :definicaoId")
    fun showDefinicao(definicaoId: Int): Flow<Definicao>
}