package com.wit.farmersmarketredo.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.wit.farmersmarketredo.R

class ProduceDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProduceDetailFragment()
    }

    private lateinit var viewModel: ProduceDetailViewModel
    private val args by navArgs<ProduceDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.produce_detail_fragment, container, false)

        Toast.makeText(context,"Donation ID Selected : ${args.produceid}",Toast.LENGTH_LONG).show()

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProduceDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}