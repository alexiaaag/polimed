package com.example.polimedapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.polimedapplication.ui.navigation.AppNavigation
import com.example.polimedapplication.ui.theme.PoliMedApplicationTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 🔹 Setează limba aplicației la ROMÂNĂ înainte de UI
        setAppLocale("ro")

        super.onCreate(savedInstanceState)
        setContent {
            PoliMedApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }

    // 🔹 Funcția care setează limba aplicației
    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
