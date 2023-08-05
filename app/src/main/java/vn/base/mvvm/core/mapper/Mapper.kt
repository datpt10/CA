package vn.base.mvvm.core.mapper

interface Mapper<in T, out R> {

    fun convert(t: T): R
}