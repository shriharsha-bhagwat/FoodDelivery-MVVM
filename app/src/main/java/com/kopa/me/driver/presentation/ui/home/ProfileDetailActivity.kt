package com.kopa.me.driver.presentation.ui.home

import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kopa.me.driver.R
import com.kopa.me.driver.core.utils.logWtf
import com.kopa.me.driver.databinding.ActivityProfileDetailBinding
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.core.base.BaseActivity
import com.kopa.me.driver.presentation.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDetailActivity :
    BaseActivity<ProfileDetailViewModel, ActivityProfileDetailBinding>(layoutId = R.layout.activity_profile_detail) {

    override val mViewModel: ProfileDetailViewModel by viewModel()

    override fun observeViewModel() {
        mViewModel.driverByID.observe(this, Observer {
            renderState(it)
        })

        mViewModel.driverProfileData.observe(this, Observer {
            Glide.with(this)
                .load(it.driverImage)
                .centerCrop()
                .error(R.drawable.ic_user)
                .into(binding.imgProfilePic);
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
                showAlert("Success", this.getString(it), null)
        })

        mViewModel.onFinishActivityEvent.observe(this, EventObserver {
            finish()
        })
    }

    fun renderState(state: ViewState) {
        when (state) {
            is ProfileDetailViewModel.DriverProfileViewState.Success -> {
                logWtf(message = "${state.data}")
                hideProgressDialog()
            }
            is ProfileDetailViewModel.DriverProfileViewState.Loading -> {
                logWtf(message = "Loading")
                showProgressDialog()
            }
            is ProfileDetailViewModel.DriverProfileViewState.Error -> {
                logWtf(message = state.errorMessage)
                hideProgressDialog()
            }
        }
    }
}