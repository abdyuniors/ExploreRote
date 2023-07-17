package com.abdyunior.explorerote.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdyunior.explorerote.R
import com.abdyunior.explorerote.adapter.ListWisataAdapter
import com.abdyunior.explorerote.data.Wisata
import com.abdyunior.explorerote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Wisata>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWisata.setHasFixedSize(true)
        list.addAll(getListWisata())

        showRecyclerList()
    }

    private fun getListWisata(): ArrayList<Wisata> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataLocation = resources.getStringArray(R.array.data_location)
        val dataHrgTicket = resources.getStringArray(R.array.data_price)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataLat = resources.getStringArray(R.array.data_lat)
        val dataLng = resources.getStringArray(R.array.data_lng)
        val dataPhoneNumber = resources.getStringArray(R.array.data_phone)
        val dataEmail = resources.getStringArray(R.array.data_email)
        val listWisata = ArrayList<Wisata>()
        for (i in dataName.indices) {
            val wisata = Wisata(
                dataName[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1),
                dataLocation[i],
                dataHrgTicket[i],
                dataLat[i].toDouble(),
                dataLng[i].toDouble(),
                dataPhoneNumber[i],
                dataEmail[i]
            )
            listWisata.add(wisata)
        }
        dataPhoto.recycle()
        return listWisata
    }

    private fun showSelectedWisata(wisata: Wisata) {
        Toast.makeText(this, "Kamu memilih " + wisata.name, Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        binding.rvWisata.layoutManager = LinearLayoutManager(this)
        val listWisataAdapter = ListWisataAdapter(list)
        binding.rvWisata.adapter = listWisataAdapter
        listWisataAdapter.setOnItemClickCallback(object : ListWisataAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Wisata) {
                showSelectedWisata(data)
                val intent = Intent(this@MainActivity, DetailWisataActivity::class.java)
                intent.putExtra(DetailWisataActivity.DATA, data)
                startActivity(intent)
            }
        })
    }
}