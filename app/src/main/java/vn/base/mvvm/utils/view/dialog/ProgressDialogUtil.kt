package vn.base.mvvm.utils.view.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater

object ProgressDialogUtil {
    private var alertDialog: AlertDialog? = null

    fun show(
        context: Context,
        isCancelable: Boolean = false,
        onDismissListener: (() -> Unit?)? = null,
    ) {
        if (context is Activity && !context.isFinishing) {
            dismiss()

            val builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(vn.base.mvvm.ui.R.layout.custom_loading, null)
            builder.setView(view)
            builder.setCancelable(isCancelable)
            alertDialog = builder.create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.setOnDismissListener {
                onDismissListener?.invoke()
            }
            alertDialog?.show()
        }
    }

    fun dismiss() {
        try {
            if((alertDialog?.context as? Activity)?.isDestroyed == true) return
            if (alertDialog?.isShowing == true){
                alertDialog?.dismiss()
                alertDialog = null
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}