package com.ahmadov.koincrypto.view

import android.graphics.Color
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ahmadov.koincrypto.databinding.CryptoRowBinding
import com.ahmadov.koincrypto.databinding.FragmentListBinding
import com.ahmadov.koincrypto.model.Crypto


class RecyclerViewAdapter(private val cryptoList:ArrayList<Crypto>
,private val listener:Listener
) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    interface Listener {
        fun onItemClick(cryptoModel: Crypto)
    }
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class RowHolder(val binding: CryptoRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding=CryptoRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList[position])
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.cryptoNameText.text=cryptoList[position].currency
        holder.binding.cryptoPriceText.text=cryptoList[position].price
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}