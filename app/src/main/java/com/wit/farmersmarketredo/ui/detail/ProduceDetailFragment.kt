package com.wit.farmersmarketredo.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wit.farmersmarketredo.databinding.ProduceDetailFragmentBinding
import com.wit.farmersmarketredo.ui.auth.LoggedInViewModel
import com.wit.farmersmarketredo.ui.list.ListViewModel
import timber.log.Timber

class ProduceDetailFragment : Fragment() {

    private lateinit var detailViewModel: ProduceDetailViewModel
    private val args by navArgs<ProduceDetailFragmentArgs>()
    private var _fragBinding: ProduceDetailFragmentBinding? = null
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val listViewModel : ListViewModel by activityViewModels()
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = ProduceDetailFragmentBinding.inflate(
            inflater,
            container, false
        )
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(
            ProduceDetailViewModel::class.java
        )
        detailViewModel.observableProduce.observe(
            viewLifecycleOwner, Observer { render() })

        fragBinding.editProduceButton.setOnClickListener {
            detailViewModel.updateProduce(
                loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.produceid, fragBinding.producevm?.observableProduce!!.value!!
            )
            listViewModel.load()
            findNavController().navigateUp()
        }
        fragBinding.deleteProduceButton.setOnClickListener {
            listViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                detailViewModel.observableProduce.value?.uid!!)
            findNavController().navigateUp()
    }
        return root
    }

    private fun render() {
        fragBinding.producevm = detailViewModel
        Timber.i("Retrofit fragBinding.producevm == $fragBinding.producevm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getProduce(loggedInViewModel.liveFirebaseUser.value?.uid!!,args.produceid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}