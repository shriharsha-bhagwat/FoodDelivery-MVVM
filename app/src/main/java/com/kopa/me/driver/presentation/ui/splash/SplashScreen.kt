package com.kopa.me.driver.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.R
import com.kopa.me.driver.core.utils.AppConstants
import com.kopa.me.driver.presentation.ui.MainActivity
import com.kopa.me.driver.presentation.ui.login.LoginActivity
import org.koin.java.KoinJavaComponent.inject

class SplashScreen : AppCompatActivity() {

    private val preferences: Preferences by inject(Preferences::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ screenNavigate() }, 2000)
    }

    private fun screenNavigate() {
        val appToken = preferences.getString(AppConstants.PREF_KEY_APP_TOKEN, "")
        val intent: Intent
        intent = if (appToken.isNullOrEmpty()) {
            Intent(this@SplashScreen, LoginActivity::class.java)
        } else {
            Intent(this@SplashScreen, MainActivity::class.java)
        }
        startActivity(intent)
        overridePendingTransition(
            R.anim.modal_activity_open_enter,
            R.anim.modal_activity_close_exit
        )
        finish()
    }
}