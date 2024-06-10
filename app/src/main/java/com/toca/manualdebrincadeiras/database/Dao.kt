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
        WHERE (b.nome LIKE '%' || :name || '%')
        AND (b.idade_min >= :minAge)
        AND (b.idade_max <= :maxAge)
        AND (:isFavorite IS NULL OR b.favorito = :isFavorite)
        AND (
            :typeIds IS NULL OR b.id IN (
                SELECT brincadeira_id
                FROM brincadeiras_tipos
                WHERE tipo_id IN (:typeIds)
                GROUP BY brincadeira_id
                HAVING COUNT(DISTINCT tipo_id) = :typeIdsSize
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
        typeIds: List<Int>?,
        typeIdsSize: Int?
    ): Flow<List<BrincadeiraIndex>>

    @Transaction
    @Query("SELECT * FROM brincadeiras WHERE id = :brincadeiraId")
    fun showBrincadeira(brincadeiraId: Int): Flow<BrincadeiraWithTipo>
}

@Dao
interface TipoDao {
    @Query("SELECT * FROM tipos")
    fun getTipos(): Flow<List<Tipo>>
}