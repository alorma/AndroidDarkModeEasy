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
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeUtils
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.pin_keyboard.view.*

class PinKeyboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.pinKeyboardStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Demo_Pin
) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private var strokeWidth: Float = 0f
    private var strokeColor: ColorStateList = ColorStateList.valueOf(
        MaterialColors.getColor(this, R.attr.colorPrimary)
    )

    init {
        inflate(context, R.layout.pin_keyboard, this)

        initAttributes(
            context,
            attributeSet,
            defStyleAttr,
            defStyleRes
        )

        initBackground(
            context,
            attributeSet,
            defStyleAttr,
            defStyleRes
        )

        setupNumber(context)
    }

    private fun initAttributes(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {

        context.withStyledAttributes(
            attributeSet,
            R.styleable.PinKeyboardView,
            defStyleAttr,
            defStyleRes
        ) {
            strokeColor = getColorStateList(R.styleable.PinKeyboardView_strokeColor) ?: strokeColor
            strokeWidth = getDimension(R.styleable.PinKeyboardView_strokeWidth, 0f)
        }
    }

    private fun initBackground(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val shapeAppearanceModel = ShapeAppearanceModel.builder(
            context,
            attributeSet,
            defStyleAttr,
            defStyleRes
        ).build()

        val materialDrawable = MaterialShapeDrawable(shapeAppearanceModel)

        materialDrawable.initializeElevationOverlay(context)
        materialDrawable.elevation = ViewCompat.getElevation(this)

        materialDrawable.fillColor = ColorStateList.valueOf(
            MaterialColors.getColor(this, R.attr.colorSurface)
        )

        materialDrawable.strokeWidth = strokeWidth
        materialDrawable.strokeColor = strokeColor

        ViewCompat.setBackground(this, materialDrawable)
    }

    private fun setupNumber(context: Context) {
        pinKeyboard.layoutManager = GridLayoutManager(context, 5)
        val items = (0..9).toList().shuffled()
        pinKeyboard.adapter = NumbersAdapter(items)
    }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
        MaterialShapeUtils.setElevation(this, elevation)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        MaterialShapeUtils.setParentAbsoluteElevation(this)
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
