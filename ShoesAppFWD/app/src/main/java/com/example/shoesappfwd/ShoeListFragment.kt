package com.example.shoesappfwd

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.shoesappfwd.databinding.FragmentShoeListBinding
import com.example.shoesappfwd.models.ShoeViewmodel
import com.udacity.shoestore.models.Shoe

class ShoeListFragment : Fragment() {
    lateinit var binding: FragmentShoeListBinding
    private val viewModel: ShoeViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)
        callback()
        createObservation()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return  super.onOptionsItemSelected(item)
    }
    /*//    private fun initLiveData() {
//        // access the listView from xml file
//        //var mListView = binding.listView
//
//    }

//    private fun dumyData(): List<Shoe> {
//        val s= mutableListOf<Shoe>()
//     //   val x = Shoe("a",1,1,"b","c",null)
//
//    }*/

    private fun createObservation() {
        arguments?.let {
            if(!it.isEmpty) {
                var args = ShoeListFragmentArgs.fromBundle(it)
                viewModel.addNewShoe(args.shoeToAdd)
            }
            viewModel.shoeList.observe(viewLifecycleOwner, Observer {
                shoeList->
                shoeList.forEach {
 //                   Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                    var mylinearlayout: LinearLayout = binding.linearlayout
                    var tvForNewShoe = TextView(context)
                    tvForNewShoe.text = it.name
                    tvForNewShoe.textSize = 20f
                    tvForNewShoe.setLayoutParams(
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    )
                    mylinearlayout.addView(tvForNewShoe)
                }
            })
        }
    }

    private fun callback() {
        //viewModel= ViewModelProvider(this).get(ShoeViewmodel::class.java)
        binding.btntoaddshoes.setOnClickListener {
            findNavController().navigate(R.id.action_shoeListFragment_to_addShoeFragment)
        }
    }
}