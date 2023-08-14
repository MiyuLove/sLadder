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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    lateinit var binding : FragmentMainBinding

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

    //exitButton & exitButtonClicked 함께 꼭 사용되어야 함.
    private var exitButtonClicked = 0
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