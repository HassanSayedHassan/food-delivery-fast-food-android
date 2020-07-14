package com.ceh.fastfood

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

object Converter {
    fun convertLayoutToImage(
        mContext: Context,
        count: Int,
        drawableId: Int
    ): Drawable {
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(R.layout.cart_icon_layout, null)
        (view.findViewById<View>(R.id.icon_cart) as ImageView).setImageResource(
            drawableId
        )
        if (count == 0) {
            val counterTextPanel =
                view.findViewById<View>(R.id.counterValuePanel)
            counterTextPanel.visibility = View.GONE
        } else {
            val textView = view.findViewById<View>(R.id.count) as TextView
            Log.d("CartSize", count.toString())
            textView.text = count.toString()
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(
                0,
                View.MeasureSpec.UNSPECIFIED
            )
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return BitmapDrawable(mContext.resources, bitmap)
    }
}