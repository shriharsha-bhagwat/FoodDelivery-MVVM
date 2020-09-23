
package com.kopa.me.driver.presentation.ui.settings

import com.kopa.me.driver.R
import com.kopa.me.driver.databinding.FragmentSettingsBinding
import com.kopa.me.driver.presentation.core.ViewState
import com.kopa.me.driver.presentation.core.base.BaseFlowFragment
import com.kopa.me.driver.presentation.utils.ThemeHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 */

class SettingsFragment: BaseFlowFragment<SettingsViewModel, FragmentSettingsBinding>(
		layoutId = R.layout.fragment_settings
) {

	override val mViewModel: SettingsViewModel by viewModel()

	override fun setupViews() {
		binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
			if (isChecked) ThemeHelper.applyTheme(ThemeHelper.ThemeMode.DARK_MODE)
			else ThemeHelper.applyTheme(ThemeHelper.ThemeMode.LIGHT_MODE)
		}
	}

	override fun renderState(state: ViewState) {

	}
}