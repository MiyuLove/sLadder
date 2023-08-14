package com.cektjtroccccc.sladder

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout

class LadderView(context: Context, w : Int,  h : Int) : View(context) {
    private val param = LinearLayout.LayoutParams(w, h)

    init{
        layoutParams = param
        setBackgroundColor(Color.parseColor("#03dac5"))
        visibility = VISIBLE
    }

    public fun translateX(x : Int){
        translationX += x
    }
    public fun translateY(x : Int){
        translationY += x
    }
}