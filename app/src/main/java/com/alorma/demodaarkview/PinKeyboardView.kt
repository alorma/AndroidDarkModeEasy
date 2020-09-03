package com.alorma.demodaarkview

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import kotlinx.android.synthetic.main.pin_keyboard.view.*

class PinKeyboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    init {
        inflate(context, R.layout.pin_keyboard, this)

        val materialDrawable = MaterialShapeDrawable()
        materialDrawable.initializeElevationOverlay(context)
        materialDrawable.fillColor = ColorStateList.valueOf(
            MaterialColors.getColor(this, R.attr.colorSurface)
        )
        materialDrawable.elevation = ViewCompat.getElevation(this)
        ViewCompat.setBackground(this, materialDrawable)

        setupNumber(context)
    }

    private fun setupNumber(context: Context) {
        pinKeyboard.layoutManager = GridLayoutManager(context, 5)
        val items = (0..9).toList().shuffled()
        pinKeyboard.adapter = NumbersAdapter(items)
    }

}

class NumbersAdapter(private val items: List<Int>) :
    RecyclerView.Adapter<NumbersAdapter.NumberHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return NumberHolder(view)
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        holder.bind(items[position])
    }

    class NumberHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(number: Int) {
            itemView.findViewById<TextView>(android.R.id.text1).text = number.toString()
        }
    }

}
