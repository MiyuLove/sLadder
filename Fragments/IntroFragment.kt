package com.cektjtroccccc.sladder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.ViewCompat.animate
import androidx.navigation.Navigation
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {
    private lateinit var binding : FragmentIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            System.runFinalization()
            System.exit(0)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIntroBinding.inflate(inflater, container, false)

        initView()
        return binding.root
    }

    private fun initView(){
        initIntro()
    }

    private fun initIntro(){
        binding.IntroText.animate()
            .alpha(1f)
            .setDuration(2000)
            .withEndAction {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_introFragment_to_mainFragment)
            }
    }
}