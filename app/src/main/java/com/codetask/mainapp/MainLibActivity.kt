package com.codetask.mainapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.codetask.mainapp.ui.theme.MainAppTheme
import com.find.myipapp.view.MainActivity

class MainLibActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppTheme {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
            }
        }
    }
}