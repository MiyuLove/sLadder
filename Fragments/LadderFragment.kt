package com.cektjtroccccc.sladder.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.cektjtroccccc.sladder.LadderUtil.LadderUtility.Companion.ladder
import com.cektjtroccccc.sladder.LadderView
import com.cektjtroccccc.sladder.MainActivity
import com.cektjtroccccc.sladder.MainViewModel
import com.cektjtroccccc.sladder.MovingText
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentLadderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue


class LadderFragment : Fragment() {
    private lateinit var viewModel : MainViewModel
    private lateinit var binding : FragmentLadderBinding
    private var job : Job? = null
    private lateinit var movingList : List<Queue<Pair<Int,Int>>>
    private var targetWidth = 0
    private var targetHeight = 0
    private var ladderLine = 0
    private var isRunning = false
    private var speed : Long = 3
    private var isAnimateRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as MainActivity).mainViewModel
        ladderLine = viewModel.ladderLine
        speed = viewModel.ladderHorseSpeed.toLong()

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val screenCanvasWidthRatio = 0.98
        val screenCanvasHeightRatio = 0.88
        targetWidth = (screenWidth * screenCanvasWidthRatio).toInt()
        targetHeight = (screenHeight * screenCanvasHeightRatio).toInt()
        job = Job()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            job?.cancel()
            job = null
            Navigation.findNavController(binding.root).popBackStack(R.id.readyFragment,false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLadderBinding.inflate(inflater)
        initView()

        return binding.root
    }

    private fun initView(){
        initRatioWithCanvas()
        makeLadder()
        makeResultBlock()
        buttonInit(makeHorse())
    }

    private fun makeLadder(){
        makeColumn()
        makeStep()
    }

    private var _paddingTop = 0
    private var _paddingLeft = 0
    private var _ladderWidth = 0
    private var _ladderHeight = 0
    private var _stepWidth = 0
    private var _stepHeight = 0
    private var _stepTopRatio = 0
    private var _horseWidth = 0
    private var _horseHeight = 0

    private fun initRatioWithCanvas(){
        _paddingTop = (targetHeight * 0.07f).toInt()
        _paddingLeft = targetWidth/(ladderLine+1) - (targetWidth/35)

        _ladderWidth = targetWidth/30
        _ladderHeight = (targetHeight * 0.75).toInt()

        _stepWidth = _paddingLeft + _ladderWidth
        _stepHeight = _ladderWidth
        _stepTopRatio = targetHeight/40

        _horseWidth = _ladderWidth*3
        _horseHeight = _horseWidth
    }

    private fun makeColumn(){
        var paddingLeft = _paddingLeft
        val paddingTop = _paddingTop

        for(i in 0 until ladderLine){
            val ladderView = LadderView(requireContext(),_ladderWidth ,_ladderHeight)

            ladderView.translateX(paddingLeft)
            ladderView.translateY(paddingTop)
            paddingLeft += _stepWidth

            binding.ladderCanvas.addView(ladderView)
        }
    }

    private fun makeStep(){
        var movingLeft = _paddingLeft + _ladderWidth
        var movingTop : Int

        for (i in viewModel.ladderManager.getLadderStep()) {
            i.forEach {
                val ladderView = LadderView(requireContext(), _stepWidth, _stepHeight)

                movingTop = _stepTopRatio * it + _paddingTop
                ladderView.translateX(movingLeft)
                ladderView.translateY(movingTop)

                binding.ladderCanvas.addView(ladderView)
            }
            movingLeft += targetWidth/(ladderLine+1)
        }
    }

    private fun makeHorse() : List<MovingText>{
        val horseGroup = mutableListOf<MovingText>()
        var paddingLeft = _paddingLeft - _ladderWidth
        val paddingTop = _paddingTop - _horseWidth
        for(i in 0 until viewModel.ladderLine){
            horseGroup.add(MovingText(requireContext(),_horseWidth ,_horseHeight, (i+1).toString(),30f))
            horseGroup[i].translateX(paddingLeft)
            horseGroup[i].translateY(paddingTop)
            paddingLeft += _stepWidth

            binding.ladderCanvas.addView(horseGroup[i])
        }

        return horseGroup
    }

    private fun makeResultBlock(){
        var paddingLeft = _paddingLeft - _ladderWidth
        val paddingTop = _ladderHeight + _paddingTop
        for(i in 0 until viewModel.ladderLine){
            val resultBlock = MovingText(requireContext(),_horseWidth ,_horseHeight, (i+1).toString(),30f)
            if(viewModel.winningNumber == i)
                resultBlock.text = "당"
            resultBlock.translateX(paddingLeft)
            resultBlock.translateY(paddingTop)
            paddingLeft += _stepWidth

            binding.ladderCanvas.addView(resultBlock)
        }
    }

    private fun movingHorse(horse: List<MovingText>) =
        GlobalScope.launch (Dispatchers.Main){
            while(isActive ){
                var ladderCount = 0
                for(i in 0 until ladderLine){
                    if(movingList[i].isNotEmpty()){
                        val pair = movingList[i].remove()

                        horse[i].viewAnimationY(
                            pair.first * _stepTopRatio + _paddingTop - _horseWidth/3,
                            { horse[i].viewAnimationXBy(pair.second, {}, speed) },
                            speed
                        )
                    }
                    else{
                        horse[i].viewAnimationY(_ladderHeight + _paddingTop - _horseWidth/3, {}, speed)
                        ladderCount ++
                    }
                }

                delay(2000/speed)

                if(ladderCount == ladderLine) {
                    ladder.logLadder("good!")
                    isRunning = false
                    job?.cancel()
                    job = null
                    Navigation.findNavController(binding.root).navigate(R.id.action_ladderFragment_to_resultFragment)
                    break
                }
            }
    }

    private fun buttonInit(horseGroup : List<MovingText>){
        movingList = makeHorseRoute(viewModel.ladderManager.getLadderRoute())
        binding.startAndStopButton.setOnClickListener {
            if(!isAnimateRunning){
                isAnimateRunning = true
                binding.startAndStopButton.isEnabled = false
                binding.startAndStopButton.text = "중지 중..."
                GlobalScope.launch (Dispatchers.Main){
                    job?.join()
                    delay(2000/speed)
                    binding.startAndStopButton.isEnabled = true
                    binding.startAndStopButton.text = "재개하기!"
                }
                job?.cancel()
            }else{
                isAnimateRunning = false
                binding.startAndStopButton.text = "일시정지"
                job = movingHorse(horseGroup)
            }
        }

        binding.resultButton.setOnClickListener {
            job?.cancel()
            job = null
            Navigation.findNavController(binding.root).navigate(R.id.action_ladderFragment_to_resultFragment)
        }
    }

    //LadderManager에서 화면 크기를 받으면 그에 따른 margin값 생성을 해주는 클래스를
    //만드는 것이 훨씬 낫지 않을까?
    private fun makeHorseRoute(ladderRoute : List<List<Int>>) : ArrayList<Queue<Pair<Int, Int>>> {
        val movingList = arrayListOf<Queue<Pair<Int, Int>>>()

        for(i in 0 until ladderLine){
            var presentLine = i
            var presentStep = 0
            val queue : Queue<Pair<Int,Int>> = LinkedList()

            while(true){
                val beforeValue = ladderRoute[presentLine][presentStep]

                if(ladderRoute[presentLine][presentStep] < 0) {
                    queue.add(Pair(Math.abs(ladderRoute[presentLine][presentStep]),_stepWidth))
                    presentLine++
                }
                else {
                    queue.add(Pair(Math.abs(ladderRoute[presentLine][presentStep]),-_stepWidth))
                    presentLine--
                }
                presentStep = ladderRoute[presentLine].indexOf(-beforeValue) + 1

                if(ladderRoute[presentLine].size <= presentStep) {
                    break
                }
            }
            movingList.add(queue)
        }

        return movingList
    }
}