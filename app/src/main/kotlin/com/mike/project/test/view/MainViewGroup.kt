package com.mike.project.test.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.withStyledAttributes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.mike.project.test.R
import com.mike.project.test.databinding.ViewGroupMainBinding

@Suppress("LeakingThis")
open class MainViewGroup : CoordinatorLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs, defStyleAttr)
    }

    init {
        initBase()
    }

    protected open val appBarLayout: AppBarLayout?
        get() = findViewById(R.id.appbar_layout)

    protected open val collapsingToolbarLayout: CollapsingToolbarLayout?
        get() = findViewById(R.id.collapsing_toolbar_layout)

    protected open val toolbar: MaterialToolbar?
        get() = findViewById(R.id.toolbar)

    protected open val container: ViewGroup?
        get() = findViewById(R.id.container)

    protected open val txtTitle: TextView?
        get() = findViewById(R.id.txt_title)

    protected open var title: String?
        get() = collapsingToolbarLayout?.title?.toString()
        set(value) {
            collapsingToolbarLayout?.title = value
            toolbar?.title = value
            txtTitle?.text = value
        }

    protected open var titleEnabled: Boolean = true
        set(value) {
            field = value
            collapsingToolbarLayout?.isTitleEnabled = value
            txtTitle?.visibility = if (value) VISIBLE else GONE
        }

    protected open var expanded: Boolean = true
        set(value) {
            field = value
            setExpanded(expanded, false)
        }

    fun setTitle(@StringRes resId: Int) {
        title = try {
            context?.getString(resId)
        } catch (_: Exception) {
            null
        }
    }

    open fun setExpanded(expanded: Boolean, animate: Boolean = true) {
        appBarLayout?.setExpanded(expanded, animate)
    }

    protected open fun initBase() {
        ViewGroupMainBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (child?.id == R.id.appbar_layout || child?.id == R.id.container) {
            super.addView(child, params)
            return
        }
        container?.addView(child, params)
    }

    protected open fun initAttrs(attrs: AttributeSet, @AttrRes defStyleAttr: Int = 0) {
        context.withStyledAttributes(attrs, R.styleable.MainViewGroup, defStyleAttr, 0) {
            title = obtainTitleAttribute()
            titleEnabled = obtainTitleEnabledAttribute()
            expanded = obtainExpandedAttribute()
        }
    }

    private fun TypedArray.obtainTitleAttribute(): String? {
        return getString(R.styleable.MainViewGroup_title)
    }

    private fun TypedArray.obtainTitleEnabledAttribute(): Boolean {
        return getBoolean(R.styleable.MainViewGroup_titleEnabled, titleEnabled)
    }

    private fun TypedArray.obtainExpandedAttribute(): Boolean {
        return getBoolean(R.styleable.MainViewGroup_expanded, expanded)
    }


}