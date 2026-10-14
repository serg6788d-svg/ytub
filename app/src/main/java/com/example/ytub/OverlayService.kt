package com.example.ytub

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout

class OverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private var lastClickTime: Long = 0

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        params.screenBrightness = 0.0f

        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null)
        
        val rootLayout = overlayView?.findViewById<RelativeLayout>(R.id.overlayRoot)
        rootLayout?.setOnClickListener {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < 400) {
                stopSelf()
            }
            lastClickTime = clickTime
        }

        windowManager?.addView(overlayView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (overlayView != null) {
            windowManager?.removeView(overlayView)
            overlayView = null
        }
    }
}
