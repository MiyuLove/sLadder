package com.cektjtroccccc.sladder.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.cektjtroccccc.sladder.MainActivity
import com.cektjtroccccc.sladder.MainViewModel
import com.cektjtroccccc.sladder.R
import com.cektjtroccccc.sladder.databinding.FragmentLadderBinding
import com.cektjtroccccc.sladder.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private lateinit var viewModel : MainViewModel
    private lateinit var binding : FragmentResultBinding
    private var resultNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (requireActivity() as MainActivity).mainViewModel
        resultNumber = viewModel.ladderManager.getResultNumber() + 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Navigation.findNavController(binding.root).popBackStack(R.id.readyFragment,false)
        }
        initView()
        return binding.root
    }

    private fun initView(){
        binding.resultNumber.text = "$resultNumber 번님!"
        binding.replayButton.setOnClickListener{
            Navigation.findNavController(binding.root).popBackStack(R.id.readyFragment,false)
        }
    }
}