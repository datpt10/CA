package vn.base.mvvm.utils.extensions

import androidx.viewpager2.widget.ViewPager2
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

val ViewPager2.recyclerView: RecyclerView
    get() {
        return this[0] as RecyclerView
    }