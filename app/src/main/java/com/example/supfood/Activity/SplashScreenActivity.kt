package com.example.supfood.Activity

import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.supfood.R

class SplashScreenActivity : AppCompatActivity() {
    //Loading page

    private val splashScreenDuration: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#FF9F10"))
            gravity = Gravity.CENTER
        }

        val logoImageView = ImageView(this).apply {
            setImageResource(R.drawable.logo_supfood)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
        }

        layout.addView(logoImageView)
        setContentView(layout)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashScreenDuration)
    }
}
