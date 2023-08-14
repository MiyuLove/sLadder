package com.cektjtroccccc.sladder.LadderUtil

import com.cektjtroccccc.sladder.LadderUtil.LadderUtility.Companion.ladder
import kotlin.random.Random


//사다리 수와 당첨 번호만 있으면 사다리를 만들 수 있는 클래스.
class LadderManager(ladderLine : Int, winningNumber : Int, confirmNumber : Int) {
    //ladder row - ladder column
    private val ladderRoute : List<List<Int>>
    private val ladderStep :  List<List<Int>>
    private var resultNumber = 1

    fun getLadderStep() = ladderStep
    fun getLadderRoute() = ladderRoute
    fun getResultNumber() = resultNumber

    init {
        ladderStep = makeRouteColumn(ladderLine)
        ladderRoute = makeRandomRoute(ladderStep)
        resultNumber = findResult(winningNumber)
    }

    //make LadderStep
    private fun makeRouteColumn(ladderLine : Int) : List<List<Int>>{
        val randomRoute = mutableListOf<List<Int>>()
        randomRoute.add(makeRandomNumberGroup())
        for(i in 1 until ladderLine - 1) {
            randomRoute.add(removeDuplication(randomRoute[i-1],makeRandomNumberGroup()))
        }
        //ladder.logLadder("makeRouteColumn")
        //ladder.logLadder(randomRoute[0])
        //ladder.logLadder(randomRoute[i])

        return randomRoute
    }

    private fun makeRandomNumberGroup() : List<Int>{
        val routeTop = makeRandomNumbers(2,2,8)
        val routeMid = makeRandomNumbers(4,10,20)
        val routeBottom = makeRandomNumbers(2, 22, 28)

        return routeTop + routeMid + routeBottom
    }

    private fun makeRandomNumbers(count : Int, min : Int, max : Int) : List<Int>{
        val random = Random.Default
        val randomCount = random.nextInt(1, count)
        val randomNumbers = mutableListOf<Int>()

        while (randomNumbers.size < randomCount){
            val randomNumber = random.nextInt(min, max)
            if(!randomNumbers.contains(randomNumber))
                randomNumbers.add(randomNumber)
        }
        randomNumbers.sort()

        return randomNumbers
    }

    private fun removeDuplication(compareList : List<Int>, resultList : List<Int>): List<Int> {
        val referenceList = MutableList(30) { 0 }

        for(i in compareList)
            referenceList[i] = 3

        for(i in resultList) {
            if (referenceList[i] == 3){
                return removeDuplication(compareList, makeRandomNumberGroup())
            }
        }
        return resultList
    }

    //makeStopDirection
    private fun makeRandomRoute(randomRoute : List<List<Int>>) : List<List<Int>> {
        val settingLadderRoute = mutableListOf<List<Int>>()

        settingLadderRoute.add(setLadderRoute(-1, randomRoute[0]))
        for(i in 1 until randomRoute.size){
            settingLadderRoute.add(setLadderRoute(randomRoute[i-1], randomRoute[i]))
        }
        settingLadderRoute.add(setLadderRoute(1, randomRoute[randomRoute.size-1]))

        ladder.logLadder("makeRandomRoute")
        for(i in settingLadderRoute) {
            ladder.logLadder(i)
        }
        return settingLadderRoute
    }

    private fun setLadderRoute(randomLeftRoute: List<Int>, randomRightRoute: List<Int>) : List<Int>{
        val ladderRoute = (randomLeftRoute + randomRightRoute).toMutableList()
        ladderRoute.sort()

        //중복이 절대 없기 때문에 값으로 접근 가능
        for(i in randomRightRoute){
            val index = ladderRoute.indexOf(i)
            ladderRoute[index] = -ladderRoute[index]
        }

        return ladderRoute
    }

    private fun setLadderRoute(direction : Int, _randomRoute : List<Int>) : List<Int>{
        val randomRoute = _randomRoute.toMutableList()
        for(i in 0 until randomRoute.size)
            randomRoute[i] = direction * randomRoute[i]

        return randomRoute
    }

    private fun findResult(winningNumber: Int) : Int{
        for(i in ladderRoute.indices){
            if(winningNumber == searchRoute(i)) {

                return i
            }
        }

        return ladderRoute.size-1
    }

    private fun searchRoute(index : Int) : Int{
        var presentLine = index
        var presentStep = 0

        while(true){
            val beforeValue = ladderRoute[presentLine][presentStep]

            if(ladderRoute[presentLine][presentStep] < 0)
                presentLine++
            else
                presentLine--

            presentStep = ladderRoute[presentLine].indexOf(-beforeValue) + 1
            if(ladderRoute[presentLine].size <= presentStep) {
                return presentLine
            }
        }
    }
}