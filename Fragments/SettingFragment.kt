package com.cektjtroccccc.sladder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cektjtroccccc.sladder.LadderUtil.LadderUtility.Companion.ladder
import com.cektjtroccccc.sladder.MainActivity
import com.cektjtroccccc.sladder.MainViewModel
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel : MainViewModel
    private var ladderSpeed = 3
    private val ladderMinimumSpeed = 1
    private val ladderMaxiMumSpeed = 6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater)
        ladderSpeed = viewModel.getPreference(viewModel.HORSE_SPEED_KEY,3)

        initView()

        return binding.root
    }

    private fun initView(){
        setSpeedText(ladderSpeed)

        binding.speedMinusButton.setOnClickListener{
            ladderSpeed = if(ladderSpeed > ladderMinimumSpeed)
                    ladderSpeed - 1 else ladderMinimumSpeed

            setSpeedText(ladderSpeed)
        }

        binding.speedPlusButton.setOnClickListener{
            ladderSpeed = if(ladderSpeed < ladderMaxiMumSpeed)
                    ladderSpeed  + 1 else ladderMaxiMumSpeed

            setSpeedText(ladderSpeed)
        }

        binding.speedConfirmButton.setOnClickListener{
            viewModel.setSpeed(viewModel.HORSE_SPEED_KEY,ladderSpeed)
            ladder.makeToast(this.requireActivity(),"속도 $ladderSpeed 설정 완료!")
        }
    }

    private fun setSpeedText(ladderSpeed : Int){
        binding.speedText.text = ladderSpeed.toString()
    }
}