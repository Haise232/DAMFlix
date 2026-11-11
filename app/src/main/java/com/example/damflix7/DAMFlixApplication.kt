package com.example.damflix7

import android.app.Application
import com.example.damflix7.data.PeliculaRepository
import com.example.damflix7.data.local.AppDatabase

class DAMFlixApplication : Application() {

    // Se crean la primera vez que se acceden y se reutilizan después (Singleton)
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { PeliculaRepository(database.peliculaDao()) }
}
