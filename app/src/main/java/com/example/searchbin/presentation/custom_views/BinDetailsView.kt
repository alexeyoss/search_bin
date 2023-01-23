package com.example.searchbin.presentation.custom_views


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.searchbin.R
import com.example.searchbin.databinding.BinDetailsViewBinding

class BinDetailsView
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    private val binding = BinDetailsViewBinding.inflate(LayoutInflater.from(context), this)

    var title: String = ""
        set(value) {
            field = value
            binding.headerText.text = value
        }

    var body: String = ""
        set(value) {
            field = value
            binding.bodyText.text = value
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.BinDetailsView) {
            title = getString(R.styleable.BinDetailsView_title) ?: ""
            body = getString(R.styleable.BinDetailsView_body) ?: ""
        }
    }
}