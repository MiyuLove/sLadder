package com.cektjtroccccc.sladder

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.cektjtroccccc.sladder.LadderUtil.LadderManager
import com.cektjtroccccc.sladder.LadderUtil.LadderUtility.Companion.ladder

class MainViewModel(application : Application) : ViewModel() {
    private val activity = application
    val HORSE_SPEED_KEY = "horse_speed"
    val CONFIRM_RESULT_KEY = "confirm_number"
    var ladderHorseSpeed = 3
    var confirmResult = -1
    var resultNumber = 0
    var ladderLine = 3
    var winningNumber = 0

    lateinit var ladderManager: LadderManager

    init {
        ladderHorseSpeed = getPreference(HORSE_SPEED_KEY,3)
        confirmResult = getPreference(CONFIRM_RESULT_KEY,-1)
    }

    fun setLadder(ladderLine : Int,winningNumber : Int){
        this.ladderLine = ladderLine
        this.winningNumber = winningNumber
        ladder.logLadder(
            "ladderLine : $ladderLine" + "resultNumber : $resultNumber confirmResult : $confirmResult"
        )
        while(true){
            this.ladderManager = LadderManager(ladderLine,winningNumber, confirmResult)
            resultNumber = ladderManager.getResultNumber()

            if(confirmResult > ladderLine-1)
                break
            if(resultNumber == confirmResult)
                break
        }

    }

    fun getPreference(key : String, defValue: Int) :Int {
        return PreferenceUtil(activity.applicationContext).getInt(key, defValue)
    }

    fun setSpeed(key : String, ladderSpeed : Int) {
        PreferenceUtil(activity.applicationContext).setInt(key, ladderSpeed)
        this.ladderHorseSpeed = ladderSpeed
    }

    fun setResult(key : String, ladderResult : Int){
        PreferenceUtil(activity.applicationContext).setInt(key, ladderResult)
        this.confirmResult = ladderResult
    }
}

class PreferenceUtil(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("speed_info", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String):String{
        return preferences.getString(key,defValue).toString()
    }

    fun setString(key: String, defValue: String){
        preferences.edit().putString(key, defValue).apply()
    }

    fun getInt(key: String, defValue: Int = 0):Int{
        return preferences.getInt(key,defValue)
    }

    fun setInt(key: String, defValue: Int = 0) {
        preferences.edit().putInt(key, defValue).apply()
    }
}