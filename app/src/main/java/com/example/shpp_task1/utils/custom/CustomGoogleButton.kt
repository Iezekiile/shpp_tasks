package com.example.shpp_task1.utils.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.shpp_task1.R

/**
 * Custom button with Google icon and text.
 * Text and icon are linked and centered
 */
class CustomGoogleButton(context: Context, private val attrs: AttributeSet) :
    AppCompatButton(context, attrs) {
    private val defaultTextColor = context.getColor(android.R.color.black)
    private val defaultText = "GOOGLE"
    private val defaultTextSize = 16
    private val defaultBackgroundResId = R.drawable.btn_bg_google

    init {
        initializationAttrs()
    }

    /**
     * Initialization attributes from xml.
     */
    private fun initializationAttrs() {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomGoogleButton
        )

        setText(typedArray)
        setTextSize(typedArray)
        setTextColor(typedArray)
        setFontFamily(typedArray)
        setBackground(typedArray)
        setIcon(typedArray)
        typedArray.recycle()
    }

    /**
    * Set text to button
     */
    private fun setText(typedArray: TypedArray) {
        val customText =
            typedArray.getString(R.styleable.CustomGoogleButton_buttonText) ?: defaultText
        text = customText
    }

    /**
     * Set text size to button
     */
    private fun setTextSize(typedArray: TypedArray) {
        val customTextSize = typedArray.getDimensionPixelSize(
            R.styleable.CustomGoogleButton_buttonTextSize,
            defaultTextSize
        )
        textSize = customTextSize.toFloat()
    }

    /**
     * Set text color to button
     */
    private fun setTextColor(typedArray: TypedArray) {
        val customTextColor = typedArray.getColor(
            R.styleable.CustomGoogleButton_buttonTextColor,
            defaultTextColor
        )
        setTextColor(customTextColor)
    }

    /**
     * Set font family to button
     */
    private fun setFontFamily(typedArray: TypedArray) {
        val fontFamily =
            typedArray.getResourceId(R.styleable.CustomGoogleButton_buttonFontFamily, 0)
        if (fontFamily > 0) {
            typeface = ResourcesCompat.getFont(context, fontFamily)
        }
    }

    /**
     * Set background to button
     */
    private fun setBackground(typedArray: TypedArray) {
        val customBackgroundResId = typedArray.getResourceId(
            R.styleable.CustomGoogleButton_buttonBackground, defaultBackgroundResId
        )
        val customBackground: Drawable? = ContextCompat.getDrawable(context, customBackgroundResId)
        background = customBackground
    }

    /**
     * Set icon to button
     */
    private fun setIcon(typedArray: TypedArray) {
        val customImageResId = typedArray.getResourceId(
            R.styleable.CustomGoogleButton_buttonIcon,
            R.drawable.ic_google
        )
        val customImage = ContextCompat.getDrawable(context, customImageResId)

        val spannable = SpannableString("  $text")
        customImage?.setBounds(0, 0, customImage.intrinsicWidth, customImage.intrinsicHeight)
        val imageSpan = ImageSpan(customImage!!, ImageSpan.ALIGN_BOTTOM)
        spannable.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        text = spannable
    }
}