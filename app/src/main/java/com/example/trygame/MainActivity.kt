package com.example.trygame

import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.example.trygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)

    }
    private lateinit var code: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val tm: TelephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        code = tm.networkOperatorName
        if (code == "KYIVSTAR") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WebViewFragment()).commit()
        } else {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GameFragment()).commit()

        }


    }
}




