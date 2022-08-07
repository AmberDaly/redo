package com.wit.farmersmarketredo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.CardProduceBinding
import com.wit.farmersmarketredo.models.ProduceModel
import com.wit.farmersmarketredo.utils.customTransformation

interface ProduceClickListener {
    fun onProduceClick(produce: ProduceModel)
}

class ProduceAdapter constructor(private var produces: ArrayList<ProduceModel>,
                                 private val listener: ProduceClickListener,
                                 private val readOnly: Boolean)
    : RecyclerView.Adapter<ProduceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardProduceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val produce = produces[holder.adapterPosition]
        holder.bind(produce,listener)
    }

    fun removeAt(position: Int) {
        produces.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = produces.size

    inner class MainHolder(val binding : CardProduceBinding, private val readOnly : Boolean) :
                            RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(produce: ProduceModel, listener: ProduceClickListener) {
            binding.root.tag = produce
            binding.produce = produce

            Picasso.get().load(produce.profilepic.toUri())
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onProduceClick(produce) }
            binding.executePendingBindings()
        }
    }
}