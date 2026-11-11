package com.example.damflix7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.damflix7.ui.navigation.AppDAMFlixMainScreen
import com.example.damflix7.ui.theme.DAMFlix7Theme
import com.example.damflix7.ui.viewmodel.PeliculaViewModel
import com.example.damflix7.ui.viewmodel.PeliculaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as DAMFlixApplication
        val factory = PeliculaViewModelFactory(app.repository)

        setContent {
            DAMFlix7Theme {
                val viewModel: PeliculaViewModel = viewModel(factory = factory)
                AppDAMFlixMainScreen(viewModel = viewModel)
            }
        }
    }
}
