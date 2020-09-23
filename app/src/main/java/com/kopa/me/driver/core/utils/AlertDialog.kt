package com.kopa.me.driver.core.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.kopa.me.driver.R

class AlertDialog {

    var dialog: Dialog? = null

    fun show(
        context: Context,
        titleResId: Int,
        messageResId: Any,
        txtPositiveButtonTextResId: Int,
        txtNegativeButtonTextResId: Int,
        listener: DialogActionListener?
    ): Dialog? {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflator.inflate(R.layout.dialog_alert, null)

        dialog = Dialog(context, android.R.style.ThemeOverlay_Material_Dark_ActionBar)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.PauseDialogAnimation;
        dialog?.setContentView(view)
        dialog?.setCancelable(false)

        dialog?.findViewById<TextView>(R.id.txt_title)?.setText(titleResId)
        if (messageResId is Int) {
            dialog?.findViewById<TextView>(R.id.txt_message)?.setText(messageResId)
        }
        if (messageResId is String) {
            dialog?.findViewById<TextView>(R.id.txt_message)?.text = messageResId
        }

        if (txtPositiveButtonTextResId != -1) {
            dialog?.findViewById<MaterialButton>(R.id.btnDialogPositive)
                ?.setText(txtPositiveButtonTextResId)
            dialog?.findViewById<MaterialButton>(R.id.btnDialogPositive)?.setOnClickListener {
                listener?.onPositiveActionListener()
                dialog?.dismiss()
            }
        } else {
            dialog?.findViewById<MaterialButton>(R.id.btnDialogPositive)?.visibility =
                View.INVISIBLE
        }

        if (txtNegativeButtonTextResId != -1) {
            dialog?.findViewById<MaterialButton>(R.id.btnDialogNegative)
                ?.setText(txtNegativeButtonTextResId)
            dialog?.findViewById<MaterialButton>(R.id.btnDialogNegative)?.setOnClickListener {
                listener?.onNegativeActionListener()
                dialog?.dismiss()
            }
        } else {
            dialog?.findViewById<MaterialButton>(R.id.btnDialogNegative)?.visibility =
                View.INVISIBLE
        }

        dialog?.show()
        return dialog
    }


    fun hide() {
        dialog?.dismiss()
    }

}

interface DialogActionListener {
    fun onPositiveActionListener()
    fun onNegativeActionListener()
}
