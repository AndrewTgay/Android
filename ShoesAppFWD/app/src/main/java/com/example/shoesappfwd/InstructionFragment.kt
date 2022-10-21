package com.example.shoesappfwd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.example.shoesappfwd.databinding.FragmentInstructionBinding

class InstructionFragment : Fragment() {
lateinit var binding:FragmentInstructionBinding
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = DataBindingUtil.inflate(inflater,R.layout.fragment_instruction,container,false)
    callBacks()
    return binding.root
    }

    private fun callBacks() {
        binding.btnToListShoe.setOnClickListener{
            findNavController().navigate(R.id.action_instructionFragment_to_shoeListFragment)
        }
    }


}