package com.cektjtroccccc.sladder.LadderUtil

class LadderUtility {
    companion object{
        val ladder = LadderUtility()
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