package com.example.shoesappfwd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoesappfwd.databinding.FragmentAddShoeBinding
import com.example.shoesappfwd.models.ShoeVM
import com.example.shoesappfwd.models.ShoeViewmodel
import com.udacity.shoestore.models.Shoe

class AddShoeFragment : Fragment() {
  lateinit var binding:FragmentAddShoeBinding
 // lateinit var viewModel:ShoeViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(this).get(ShoeViewmodel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_shoe,container,false)
     binding.shoeVar = ShoeVM("","","","",null)
        callbacks()
        return binding.root
    }

    private fun callbacks() {
        binding.btnsavetheshoe.setOnClickListener{
            addShoeSuccessful()
            }
        binding.btncanceltheshop.setOnClickListener{
            cancelAddingShoe()
        }
        }

    private fun cancelAddingShoe() {
        findNavController().navigateUp()
    }

    private fun addShoeSuccessful() {
        if(binding.edShoeName.text.isEmpty())
            toastMSG("Shoe name")
        else if(binding.edShoeCompany.text.isEmpty())
            toastMSG("Shoe Company")
        else if(binding.edShoSize.text.isEmpty())
            toastMSG("Shoe size")
        else if(binding.edShoeCompany.text.isEmpty())
            toastMSG("Shoe Description")
        else{
            val shoe = binding.shoeVar!!.toShoe()
        //    Toast.makeText(context,Shoe.toString(),Toast.LENGTH_LONG).show()
            findNavController().navigate(AddShoeFragmentDirections.actionAddShoeFragmentToShoeListFragment(shoe))

        }
}

    private fun toastMSG(s: String) {
        Toast.makeText(context,"Please enter the $s",Toast.LENGTH_LONG).show()

    }

}