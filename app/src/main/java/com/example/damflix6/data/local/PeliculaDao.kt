package com.example.damflix6.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculaDao {

    // Al devolver Flow, Room vuelve a emitir la lista cada vez que cambia la tabla
    @Query("SELECT * FROM peliculas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<PeliculaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(pelicula: PeliculaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(peliculas: List<PeliculaEntity>)

    @Update
    suspend fun actualizar(pelicula: PeliculaEntity)

    @Delete
    suspend fun eliminar(pelicula: PeliculaEntity)
}
