package com.kopa.me.driver.presentation.ui.login

import android.animation.Animator
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.kopa.me.driver.R
import com.kopa.me.driver.core.utils.AppConstants
import com.kopa.me.driver.core.utils.commit
import com.kopa.me.driver.databinding.ActivityLoginBinding
import com.kopa.me.driver.presentation.core.base.BaseActivity
import com.kopa.me.driver.presentation.ui.MainActivity
import com.kopa.me.driver.presentation.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

    override val mViewModel: LoginViewModel by viewModel()

    override fun initView() {
        super.initView()
        binding.loadingBtn.rippleColor = 0x33ffffff
        binding.loadingBtn.typeface = Typeface.SERIF

        binding.loadingBtn.cornerRadius = 100f
        val shader =
            LinearGradient(
                0f,
                0f,
                1000f,
                100f,
                ContextCompat.getColor(this, R.color.colorPrimaryLight),
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                Shader.TileMode.CLAMP
            )
        binding.loadingBtn.backgroundShader = shader
        binding.loadingBtn.animationEndAction = {
            Toast.makeText(applicationContext, "end:$it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun observeViewModel() {
        mViewModel.onLoginEvent.observe(this, EventObserver {
            if (it) {
                binding.loadingBtn.startLoading()
                binding.loadingBtn.isEnabled = false
            } else {
                binding.loadingBtn.loadingFailed()
                binding.loadingBtn.reset()
                binding.loadingBtn.isEnabled = true
            }
        })

        mViewModel.onLoginSuccessEvent.observe(this, EventObserver {
            preferences.commit {
                putString(AppConstants.PREF_KEY_APP_TOKEN, it.apiToken)
                putLong(AppConstants.PREF_KEY_DRIVER_ID, it.driverId)
            }
            binding.loadingBtn.loadingSuccessful()
            binding.loadingBtn.animationEndAction = {
                toNextPage()
                Unit
            }
        })

        mViewModel.onErrorEvent.observe(this, EventObserver {
            if (it is String)
                showAlert("Error", it, null)
            else if (it is Int)
                showAlert("Error", getString(it), null)
        })

        mViewModel.onMessageEvent.observe(this, EventObserver {
            if (it is String)
                showAlert("Success", it, null)
            else if (it is Int)
                showAlert("Success", getString(it), null)
        })
    }

    private fun toNextPage() {

        val cx = (binding.loadingBtn.left + binding.loadingBtn.right) / 2
        val cy = (binding.loadingBtn.top + binding.loadingBtn.bottom) / 2

        val animator = ViewAnimationUtils.createCircularReveal(
            binding.animateView,
            cx,
            cy,
            0f,
            resources.displayMetrics.heightPixels * 1.2f
        )
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        binding.animateView.visibility = View.VISIBLE

        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                binding.loadingBtn.postDelayed({
                    binding.loadingBtn.reset()
                    binding.animateView.visibility = View.GONE
                }, 200)
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.animateView.visibility = View.GONE

                startActivity(
                    Intent(
                        applicationContext,
                        MainActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                overridePendingTransition(0, 0)
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

    }

}