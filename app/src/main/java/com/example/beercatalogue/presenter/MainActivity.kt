package com.example.beercatalogue.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.beercatalogue.R
import com.example.beercatalogue.utils.ConnectivityObserver
import com.example.beercatalogue.utils.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch{
            installSplashScreen().apply {
                setKeepVisibleCondition {
                    false
                }
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        lifecycleScope.launch {
            connectivityObserver.observe().collectLatest {
                when(it){
                    ConnectivityObserver.Status.Available -> {
                    }
                    else -> {
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@MainActivity, "No Internet", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}