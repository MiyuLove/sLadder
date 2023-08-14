package com.cektjtroccccc.sladder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cektjtroccccc.sladder.LadderUtil.LadderUtility
import com.cektjtroccccc.sladder.MainActivity
import com.cektjtroccccc.sladder.MainViewModel
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentGoalBinding
import com.cektjtroccccc.sladder.databinding.FragmentLadderBinding

class GoalFragment : Fragment() {
    private lateinit var viewModel : MainViewModel
    private lateinit var binding : FragmentGoalBinding
    private var confirmNumber = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as MainActivity).mainViewModel
        confirmNumber = viewModel.getPreference(viewModel.CONFIRM_RESULT_KEY,-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGoalBinding.inflate(inflater)
        initView()

        return binding.root
    }
    private fun initView(){
        binding.confirmNumber.text = if(confirmNumber == -1)"X" else (confirmNumber+1).toString()

        binding.minusButtonGoal.setOnClickListener {
            if(confirmNumber >= 0) confirmNumber --
            binding.confirmNumber.text = if(confirmNumber == -1)"X" else (confirmNumber+1).toString()
        }

        binding.plusButtonGoal.setOnClickListener {
            if(confirmNumber < 5)confirmNumber ++
            binding.confirmNumber.text = if(confirmNumber == -1)"X" else (confirmNumber+1).toString()
        }

        binding.confirmButton.setOnClickListener {
            viewModel.setResult(viewModel.CONFIRM_RESULT_KEY,confirmNumber)
        }
    }
}