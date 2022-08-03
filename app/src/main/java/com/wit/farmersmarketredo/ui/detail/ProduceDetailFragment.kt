package com.wit.farmersmarketredo.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wit.farmersmarketredo.R

class ProduceDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ProduceDetailFragment()
    }

    private lateinit var viewModel: ProduceDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.produce_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProduceDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}