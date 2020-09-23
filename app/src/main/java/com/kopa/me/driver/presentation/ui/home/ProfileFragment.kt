package com.kopa.me.driver.presentation.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kopa.me.driver.R
import com.kopa.me.driver.core.service.LocationUpdateService
import com.kopa.me.driver.core.utils.*
import com.kopa.me.driver.databinding.FragmentProfileBinding
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.core.base.BaseFlowFragment
import com.kopa.me.driver.presentation.ui.common.LoadingState
import com.kopa.me.driver.presentation.ui.home.HomeViewModel.DriverProfileViewState
import com.kopa.me.driver.presentation.ui.login.LoginActivity
import com.kopa.me.driver.presentation.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ProfileFragment :
    BaseFlowFragment<ProfileViewModel, FragmentProfileBinding>(layoutId = R.layout.fragment_profile),
    PermissionUtils.OnPermissionResultListener {

    override val mViewModel: ProfileViewModel by viewModel()

    lateinit var permissionUtils: PermissionUtils

    override fun setupViews() {
        setupPermissions()
        setupBindings()

        mViewModel.driverByID.observe(this, Observer {
            renderState(it)
            checkDutyStatus()
        })

        mViewModel.onLogout.observe(this, Observer {
            logoutUserOut()
        })

        mViewModel.driverProfileData.observe(this, Observer {
            Glide
                .with(requireActivity())
                .load(it.driverImage)
                .centerCrop()
                .error(R.drawable.ic_user)
                .into(binding.imgProfilePic);
        })

        mViewModel.onErrorEvent.observe(this, EventObserver {
            if (it is String)
                showAlert("Error", it, null)
            else if (it is Int)
                showAlert("Error", requireContext().getString(it), null)
        })

        mViewModel.onMessageEvent.observe(this, EventObserver {
            if (it is String)
                showAlert("Success", it, null)
            else if (it is Int)
                showAlert("Success", requireContext().getString(it), null)
        })

        mViewModel.onDutyStatusChangeEvent.observe(this, EventObserver {
            if (it) {
                preferences.commit {
                    putBoolean(AppConstants.PREF_KEY_DRIVER_DUTY_STATUS, true)
                }
                binding.switchOnDuty.setChecked(true)
                binding.textOnDuty.text = getString(R.string.on_duty)

                startLocationUpdates()
            } else {
                preferences.commit {
                    putBoolean(AppConstants.PREF_KEY_DRIVER_DUTY_STATUS, false)
                }
                binding.switchOnDuty.setChecked(false)
                binding.textOnDuty.text = getString(R.string.off_duty)

                stopLocationUpdates()
            }
        })

        mViewModel.onProfileDetailEvent.observe(this, EventObserver {
            startActivity(
                Intent(
                    activity,
                    ProfileDetailActivity::class.java
                )
            )
            activity?.overridePendingTransition(
                R.anim.modal_activity_open_enter,
                R.anim.modal_activity_close_exit
            )
        })
    }

    private fun checkDutyStatus() {
        val dutyStatus = preferences.getBoolean(AppConstants.PREF_KEY_DRIVER_DUTY_STATUS, false)
        if (dutyStatus) {
            binding.switchOnDuty.setChecked(true)
            binding.textOnDuty.text = getString(R.string.on_duty)

            startLocationUpdates()
        } else {
            binding.switchOnDuty.setChecked(false)
            binding.textOnDuty.text = getString(R.string.off_duty)

            stopLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        val intent = Intent(requireActivity(), LocationUpdateService::class.java)
        intent.action = AppConstants.BROADCAST_MESSAGE_START_LOCATION_UPDATE
        requireActivity().startService(intent)
    }

    private fun stopLocationUpdates() {
        val intent = Intent(requireActivity(), LocationUpdateService::class.java)
        intent.action = AppConstants.BROADCAST_MESSAGE_STOP_LOCATION_UPDATE
        requireActivity().startService(intent)
    }

    private fun setupPermissions() {
        permissionUtils = PermissionUtils(requireContext(), this)
    }

    private fun setupBindings() {
        binding.switchOnDuty.setOnClickListener {
            if (binding.switchOnDuty.isChecked()) {
                mViewModel.onDutyStatusChange(false)
            } else {
                if (checkForLocationSettings()) {
                    mViewModel.onDutyStatusChange(true)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun checkPermissions() {
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        permissionUtils.checkPermissionGranted(
            permissions,
            "KOPA needs location permissions to continue. Please Allow from Settings!.",
            1
        )
    }

    private fun checkForLocationSettings(): Boolean {
        if (!AppUtils.isLocationEnabled(requireContext())) {
            showLocationEnabledDialog()
            return false
        }
        return true
    }

    private fun showLocationEnabledDialog() {
        showDialog(
            R.string.location_title,
            R.string.location_message,
            R.string.go_to_settings_location,
            R.string.i_want_to_do_laterr,
            object : DialogActionListener {
                override fun onPositiveActionListener() {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                override fun onNegativeActionListener() {
                    //nothing to implment
                }
            })
    }

    private fun logoutUserOut() {
        preferences.commit {
            putString(AppConstants.PREF_KEY_APP_TOKEN, "")
            putLong(AppConstants.PREF_KEY_DRIVER_ID, -1L)
        }
        activity?.finish()
        startActivity(
            Intent(
                activity,
                LoginActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        activity?.overridePendingTransition(
            R.anim.modal_activity_open_enter,
            R.anim.modal_activity_close_exit
        )
    }

    override fun renderState(state: ViewState) {
        when (state) {
            is DriverProfileViewState.Success -> {
                logWtf(message = "${state.data}")
                sharedViewModel.showLoading.value = LoadingState.HIDE
            }
            is DriverProfileViewState.Loading -> {
                logWtf(message = "Loading")
                sharedViewModel.showLoading.value = LoadingState.SHOW
            }
            is DriverProfileViewState.Error -> {
                logWtf(message = state.errorMessage)
                sharedViewModel.showLoading.value = LoadingState.HIDE
            }
        }
    }

    override fun permissionGranted(requestCode: Int) {

    }

    override fun partialPermissionGranted(
        requestCode: Int,
        grantedPermissions: List<String?>
    ) {
        if (grantedPermissions.size > 0) {
            checkPermissions()
        }
    }

    override fun permissionDenied(requestCode: Int) {
        showSettingsDialog()
    }

    override fun neverAskAgain(requestCode: Int) {
        showSettingsDialog()
    }

    /*
    * show Go to settings alert when all permissions are denied
    * */
    fun showSettingsDialog() {
        showDialog(
            R.string.go_to_settings,
            R.string.permissions_have_to,
            R.string.dialog_ok,
            -1,
            object : DialogActionListener {
                override fun onPositiveActionListener() {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }

                override fun onNegativeActionListener() {
                    // do nothing
                }
            })
    }

}