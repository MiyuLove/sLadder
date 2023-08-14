package com.cektjtroccccc.sladder.Fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding : FragmentMainBinding
    private var exitButtonClicked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){ exitButton() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        initView()
        return binding.root
    }
    private fun initView(){
        initButton()
    }

    private fun initButton(){
        binding.startGame.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_readyFragment)
        }

        binding.goalList.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_historyFragment)
        }

        binding.settingGame.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_settingFragment)
        }

        binding.exitButton.setOnClickListener{
            exitButton()
        }
    }

    private fun exitButton(){
        exitButtonClicked ++
        if(exitButtonClicked > 1){
            System.runFinalization()
            System.exit(0)
        }
        Toast.makeText(binding.root.context,"종료 버튼을 한번 더\n눌러주세요", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            exitButtonClicked = 0
        },3000)
    }
}