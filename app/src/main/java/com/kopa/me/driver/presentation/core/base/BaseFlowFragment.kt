package com.kopa.me.driver.presentation.core.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.R
import com.kopa.me.driver.core.utils.AlertDialog
import com.kopa.me.driver.core.utils.DialogActionListener
import com.tapadoo.alerter.Alerter
import org.koin.java.KoinJavaComponent

/**
 * This is the documentation block about the class
 */

abstract class BaseFlowFragment<VM : BaseViewModel, Binding : ViewDataBinding>(
	@LayoutRes layoutId: Int
) : BaseFragment<VM, Binding>(layoutId) {

    override val mViewModel: VM? = null

    protected var alertDialog: AlertDialog? = null
    protected val preferences: Preferences by KoinJavaComponent.inject(Preferences::class.java)

    open fun showAlert(
		title: String,
		message: String,
		listener: View.OnClickListener?
	) {
        val alertObj = Alerter.create(requireActivity())
            .setTitle(title)
            .setText(message)
            .setBackgroundColorRes(R.color.colorPrimaryDark)
        if (listener != null) alertObj
            .setOnClickListener(listener)
            .show() else alertObj.show()
    }

    fun showDialog(
		titleResId: Int,
		messageResId: Any,
		txtPositiveButtonTextResId: Int,
		txtNegativeButtonTextResId: Int,
		onDialogActionListener: DialogActionListener?
	) {
        alertDialog?.hide()

        alertDialog = AlertDialog()
        alertDialog?.show(
			requireContext(),
			titleResId,
			messageResId,
			txtPositiveButtonTextResId,
			txtNegativeButtonTextResId,
			onDialogActionListener
		)
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.hide()
    }
}