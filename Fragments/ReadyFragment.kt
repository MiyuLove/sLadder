package com.cektjtroccccc.sladder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.cektjtroccccc.sladder.MainActivity
import com.cektjtroccccc.sladder.MainViewModel
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentReadyBinding


class ReadyFragment : Fragment() {
    private lateinit var binding : FragmentReadyBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var winningLineGroup : List<Button>

    private val ladderLineMin = 2
    private val ladderLineMax = 6

    //winningLine을 1이상으로 설정하려면 ladderLine도 +1만큼 설정하는 것을 권장
    private var winningLine = 1
    private var ladderLine = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentReadyBinding.inflate(inflater)

        initView()
        return binding.root
    }

    private fun initView(){
        initAllButton()
        setLadderLine(0)
    }

    private fun initAllButton(){
        makeButtonGroup()
        for (i in 0 until winningLineGroup.size){
            winningLineGroup[i].setOnClickListener {
                setWinningLine(i)
            }
        }
        setWinningLine(winningLine)

        binding.plusButton.setOnClickListener{
            setLadderLine(1)
        }
        binding.minusButton.setOnClickListener{
            setLadderLine(-1)
        }

        binding.ladderReadyButton.setOnClickListener{
            viewModel.setLadder(ladderLine, winningLine)

            Navigation.findNavController(binding.root).navigate(R.id.action_readyFragment_to_ladderFragment)
        }
    }

    private fun setWinningLine(num : Int){
        winningLine = num
        setWinningButtonBackground(num)
    }

    private fun setWinningButtonBackground(num : Int){
        for (i in 0 until winningLineGroup.size){
            if(i == 0 || i == 3 || i == 4)winningLineGroup[i]
                .setBackgroundDrawable(resources.getDrawable(R.drawable.winning_button1))
            else winningLineGroup[i]
                .setBackgroundDrawable(resources.getDrawable(R.drawable.winning_button2))
        }

        winningLineGroup[num]
            .setBackgroundDrawable(resources.getDrawable(R.drawable.winning_button_clicked))
    }

    //무조건 initAllButton의 최정상에서 실행 되어야 함.
    private fun makeButtonGroup(){
        winningLineGroup= listOf(
            binding.winningNumber1,
            binding.winningNumber2,
            binding.winningNumber3,
            binding.winningNumber4,
            binding.winningNumber5,
            binding.winningNumber6,
        )
    }

    private fun setLadderLine(num : Int){
        if(ladderLine + num < ladderLineMin ||
            ladderLine + num > ladderLineMax)
            return

        ladderLine += num

        setWinningButtonInVisible(ladderLine)
        setWinningButtonVisible(ladderLine)

        if(winningLine >= ladderLine) {
            setWinningLine(winningLine-1)
        }
        binding.ladderLineText.text = String.format("-%d-",ladderLine)
    }

    private fun setWinningButtonVisible(num : Int){
        for(i in ladderLineMin until num)
            winningLineGroup[i].isVisible = true
    }
    private fun setWinningButtonInVisible(num : Int){
        for(i in num until ladderLineMax)
            winningLineGroup[i].isVisible = false
    }
}