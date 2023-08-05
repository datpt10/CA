package vn.base.mvvm.choosePhotoHelper.callback

/**
 * @author aminography
 */
fun interface ChoosePhotoCallback<T> {
    fun onChoose(photo: T?)
}