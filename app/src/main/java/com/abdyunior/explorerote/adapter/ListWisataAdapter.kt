package com.abdyunior.explorerote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.abdyunior.explorerote.data.Wisata
import com.abdyunior.explorerote.databinding.ItemWisataBinding

class ListWisataAdapter(private val listWisata: ArrayList<Wisata>) :
    RecyclerView.Adapter<ListWisataAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(val binding: ItemWisataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemWisataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val wisata = listWisata[position]
        with(holder.binding) {
            imgItemPhoto.load(wisata.photo) {
                crossfade(true)
                crossfade(1000)
            }
            tvItemName.text = wisata.name
            tvItemLocation.text = wisata.location
            /*tvItemHrgTicket.text = "Tiket Masuk: ${wisata.hrgTicket}"*/

            // Item click listener menggunakan ViewBinding
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(wisata)
            }
        }
    }

    override fun getItemCount(): Int = listWisata.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Wisata)
    }
}