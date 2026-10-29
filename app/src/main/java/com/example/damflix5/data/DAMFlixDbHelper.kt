package com.example.damflix5.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.damflix5.model.peliculasIniciales

class DAMFlixDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "damflix_native.db"
        private const val DATABASE_VERSION = 1

        // Nombres de tabla y columnas
        const val TABLE_PELICULAS = "peliculas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_DIRECTOR = "director"
        const val COLUMN_GENERO = "genero"
        const val COLUMN_ANIO = "anio"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_ES_VISTA = "es_vista"
        const val COLUMN_ES_FAVORITA = "es_favorita"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSql = """
            CREATE TABLE $TABLE_PELICULAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT NOT NULL,
                $COLUMN_DIRECTOR TEXT NOT NULL,
                $COLUMN_GENERO TEXT NOT NULL,
                $COLUMN_ANIO INTEGER NOT NULL,
                $COLUMN_DESCRIPCION TEXT,
                $COLUMN_ES_VISTA INTEGER NOT NULL DEFAULT 0,
                $COLUMN_ES_FAVORITA INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent()

        db.execSQL(createTableSql)

        // Películas de ejemplo para que el catálogo no empiece vacío
        for (pelicula in peliculasIniciales) {
            val values = ContentValues().apply {
                put(COLUMN_TITULO, pelicula.titulo)
                put(COLUMN_DIRECTOR, pelicula.director)
                put(COLUMN_GENERO, pelicula.genero)
                put(COLUMN_ANIO, pelicula.anio)
                put(COLUMN_DESCRIPCION, pelicula.descripcion)
                put(COLUMN_ES_VISTA, if (pelicula.esVista) 1 else 0)
                put(COLUMN_ES_FAVORITA, if (pelicula.esFavorita) 1 else 0)
            }
            db.insert(TABLE_PELICULAS, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PELICULAS")
        onCreate(db)
    }
}
