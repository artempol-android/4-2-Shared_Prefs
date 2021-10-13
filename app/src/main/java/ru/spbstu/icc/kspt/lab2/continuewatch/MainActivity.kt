package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    var flag = true
    var tag = "States"

    var backgroundThread = Thread {
        while (true) {
            if (flag) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("seconds", Context.MODE_PRIVATE)
        val savedSeconds = sharedPref.getInt("ELAPSED_SECONDS", 0)
        secondsElapsed = savedSeconds

        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        Log.d(tag, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = getSharedPreferences("seconds", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("ELAPSED_SECONDS", secondsElapsed)
        editor.apply()
        Log.d(tag, "onDestroy")

    }

    override fun onStart() {
        super.onStart()
        flag = true
        Log.d(tag, "onStart")
    }

    override fun onStop() {
        super.onStop()
        flag = false
        Log.d(tag, "onStop")
    }
}
