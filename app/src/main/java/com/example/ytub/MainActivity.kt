package com.example.ytub

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var lastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Удерживаем процессор/экран активным и гасим системные панели
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )

        // Выкручиваем яркость подсветки в ноль (для OLED физически отключает пиксели)
        val layoutParams = window.attributes
        layoutParams.screenBrightness = 0.0f
        window.attributes = layoutParams

        setContentView(R.layout.activity_main)

        // Двойной тап для разблокировки экрана
        val rootLayout = findViewById<RelativeLayout>(R.id.rootLayout)
        rootLayout.setOnClickListener {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < 400) {
                finish()
            }
            lastClickTime = clickTime
        }
    }
}
