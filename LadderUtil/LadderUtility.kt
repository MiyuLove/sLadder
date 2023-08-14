package com.cektjtroccccc.sladder.LadderUtil

import android.app.Activity
import android.widget.Toast

class LadderUtility {
    companion object{
        val ladder = LadderUtility()
    }

    private var toast : Toast? = null

    fun makeToast(activity: Activity, message : String,){
        toast?.cancel()
        toast = Toast.makeText(activity.applicationContext, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun logLadder(text : String){
        println("dd123 : " + text)
    }

    fun logLadder(text : Int){
        logLadder(text.toString())
    }

    fun logLadder(text : Float){
        logLadder(text.toString())
    }

    fun logLadder(text : Any){
        logLadder(text.toString())
    }

    fun logLadder(text : List<Int>){
        print("dd123 : ")
        for(i in text){
            print(i.toString() + " ")
        }
        println()
    }
}