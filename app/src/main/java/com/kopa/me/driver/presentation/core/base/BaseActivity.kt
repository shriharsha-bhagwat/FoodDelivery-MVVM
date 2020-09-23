package com.kopa.me.driver.presentation.core.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.BR
import com.kopa.me.driver.R
import com.tapadoo.alerter.Alerter
import com.kopa.me.driver.presentation.utils.ext.actAsFluid
import org.koin.java.KoinJavaComponent.inject

abstract class BaseActivity<VM : BaseViewModel, Binding : ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity(), BaseView {

    protected abstract val mViewModel: VM?

    protected lateinit var binding: Binding
        private set

    protected var loadingDialog: Dialog? = null

    protected val preferences: Preferences by inject(Preferences::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        binding = DataBindingUtil.setContentView<Binding>(this, layoutId)
        binding.setVariable(BR.viewModel, mViewModel)
        binding.lifecycleOwner = this

        initView()
        observeViewModel()
    }

    open fun showAlert(
        title: String,
        message: String,
        listner: View.OnClickListener?
    ) {
        val alertObj = Alerter.create(this)
            .setTitle(title)
            .setText(message)
            .setBackgroundColorRes(R.color.colorPrimaryDark)
        if (listner != null) alertObj
            .setOnClickListener(listner)
            .show() else alertObj.show()
    }

    /**
     * Shows progress in an activity
     */
    fun showProgressDialog(message: String = "") {
        loadingDialog = setLoadingDialog(this)
        loadingDialog?.show()
    }

    /**
     * Hides progress in an activity
     */
    fun hideProgressDialog() {
        loadingDialog?.hide()
    }

    open fun initView() {
        actAsFluid()
    }

    abstract override fun observeViewModel()

    private fun setLoadingDialog(context: Context): Dialog {
        val dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.9f)
        }
        return dialog
    }
}