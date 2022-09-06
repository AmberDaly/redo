package com.wit.farmersmarketredo.ui.farmers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.FragmentAddProduceBinding
import com.wit.farmersmarketredo.databinding.FragmentFarmersBinding
import com.wit.farmersmarketredo.models.ProduceModel


class FarmersFragment : Fragment() {

    private lateinit var farmersViewModel: FarmersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        farmersViewModel =
            ViewModelProvider(this).get(FarmersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_farmers, container, false)
        //val textView: TextView = root.findViewById(R.id.text_slideshow)
        farmersViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })



        return root


    }
}