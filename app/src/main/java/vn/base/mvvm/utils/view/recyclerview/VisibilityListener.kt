package vn.base.mvvm.utils.view.recyclerview

interface VisibilityListener {
    fun onVisibleChanged(positionShowing: Int, positionHiding: Int, visibilityPercentage: Float)
}