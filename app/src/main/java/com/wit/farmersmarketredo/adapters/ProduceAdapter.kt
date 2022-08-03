package com.wit.farmersmarketredo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.CardProduceBinding
import com.wit.farmersmarketredo.models.ProduceModel


interface ProduceClickListener {
    fun onProduceClick(produce: ProduceModel)
}

class ProduceAdapter constructor(private var produces: List<ProduceModel>,private val listener: ProduceClickListener)
    : RecyclerView.Adapter<ProduceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProduceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val produce = produces[holder.adapterPosition]
        holder.bind(produce,listener)
    }

    override fun getItemCount(): Int = produces.size

    inner class MainHolder(val binding : CardProduceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(produce: ProduceModel, listener: ProduceClickListener) {//binding.paymentamount.text = donation.amount.toString()
            //binding.paymentmethod.text = donation.paymentmethod

            binding.produce = produce
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onProduceClick(produce) }
            //Include this call to force the bindings to happen immediately
            binding.executePendingBindings()
        }
    }
}