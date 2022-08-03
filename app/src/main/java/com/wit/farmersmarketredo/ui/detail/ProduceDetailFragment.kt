package com.wit.farmersmarketredo.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.ProduceDetailFragmentBinding
import timber.log.Timber

class ProduceDetailFragment : Fragment() {
    private lateinit var detailViewModel: ProduceDetailViewModel
    private val args by navArgs<ProduceDetailFragmentArgs>()
    private var _fragBinding: ProduceDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = ProduceDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(ProduceDetailViewModel::class.java)
        detailViewModel.observableProduce.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render() {

        fragBinding.editMessage.setText("A Message")
        fragBinding.editUpvotes.setText("0")
        fragBinding.producevm = detailViewModel
        Timber.i("Retrofit fragBinding.producevm == $fragBinding.producevm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getProduce(args.produceid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}