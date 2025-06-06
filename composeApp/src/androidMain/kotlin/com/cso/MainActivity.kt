package com.cso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                prefs = remember {
                    createDataStore(applicationContext)
                },
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}