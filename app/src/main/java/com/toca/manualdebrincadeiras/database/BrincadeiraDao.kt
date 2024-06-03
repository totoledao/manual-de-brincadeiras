package com.toca.manualdebrincadeiras.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BrincadeiraDao {
    @Query("SELECT id, nome FROM brincadeiras")
    fun getBrincadeiras(): Flow<List<BrincadeiraIndex>>

    @Query(
        "SELECT id, nome FROM brincadeiras WHERE nome LIKE '%' || :searchTerm || '%' ORDER BY CASE WHEN :columnName = 'nome' THEN nome " +
                "WHEN :columnName = 'idade_min' THEN idade_min " +
                "WHEN :columnName = 'idade_max' THEN idade_max " +
                "ELSE id END ASC"
    )
    fun getOrderedBrincadeiras(
        columnName: String,
        searchTerm: String = ""
    ): Flow<List<BrincadeiraIndex>>

    @Query("SELECT id, nome FROM brincadeiras WHERE favorito = 1 AND nome LIKE '%' || :searchTerm || '%'")
    fun getFavoriteBrincadeiras(searchTerm: String = ""): Flow<List<BrincadeiraIndex>>

}
