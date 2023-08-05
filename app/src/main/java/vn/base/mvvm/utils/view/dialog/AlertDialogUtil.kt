package vn.base.mvvm.utils.view.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog

object AlertDialogUtil {
    private var alertDialog: AlertDialog? = null

    fun message(
        context: Context,
        title: String? = null,
        message: String,
        textPositive: String = "",
        textNegative: String = "",
        positiveAction: () -> Unit,
        negativeAction: () -> Unit
    ) {
        val builder = AlertDialog.Builder(
            context,
        )
        builder.setTitle(title ?: "")
        builder.setMessage(message)
        builder.setPositiveButton(textPositive) { dialog, _ ->
            positiveAction.invoke()
            dialog.dismiss()
        }
        builder.setNegativeButton(textNegative) { dialog, _ ->
            negativeAction.invoke()
            dialog.dismiss()
        }
        alertDialog = builder.create()
        alertDialog?.show()
    }
}