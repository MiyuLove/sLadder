package com.cektjtroccccc.sladder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView

@SuppressLint("ResourceAsColor")
class MovingText (context: Context, w : Int, h : Int, t : String, textSize : Float): AppCompatTextView(context) {
    private val param = LinearLayout.LayoutParams(w, h)

    init{
        layoutParams = param
        setBackgroundColor(R.color.winning_btn_color2)
        visibility = VISIBLE
        text = t
        setTextColor(Color.BLACK)
        textAlignment = TEXT_ALIGNMENT_CENTER
        this.textSize = textSize
    }

    fun viewAnimationY(y:Int, nextAction : () ->Unit, speed : Long = 3){
        this.animate()
            .translationY(y.toFloat())
            .setDuration(1000/speed)
            .withEndAction{nextAction()}
            .start()
    }

    fun viewAnimationXBy(x:Int, nextAction : () ->Unit = {}, speed : Long = 3){
        this.animate()
            .translationXBy(x.toFloat())
            .setDuration(1000/speed)
            .withEndAction{nextAction()}
            .start()
    }


    fun translateX(x : Int){
        translationX += x
    }
    fun translateY(x : Int){
        translationY += x
    }
}