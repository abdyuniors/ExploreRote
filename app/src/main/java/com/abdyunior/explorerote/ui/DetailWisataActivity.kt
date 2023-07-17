package com.abdyunior.explorerote.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.abdyunior.explorerote.data.Wisata
import com.abdyunior.explorerote.databinding.ActivityDetailWisataBinding

class DetailWisataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailWisataBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWisataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wisata = intent.getParcelableExtra<Wisata>(DATA)
        wisata?.let {
            with(binding) {
                imgItemPhoto.load(it.photo) {
                    crossfade(true)
                    crossfade(1000)
                }
                tvItemName.text = it.name
                tvItemLocation.text = it.location
                tvItemHrgTicket.text = "Tiket Masuk: ${it.hrgTicket}"
                tvItemDescription.text = it.description
            }
        }

        binding.btnCekLokasi.setOnClickListener {
            wisata?.let {
                openGoogleMaps(it.lat, it.lng)
            }
        }

        binding.btnHireEmail.setOnClickListener {
            wisata?.let {
                hireTourGuideByEmail(it.email, it)
            }
        }

        binding.btnHireWhatsapp.setOnClickListener {
            wisata?.let {
                hireTourGuideByWhatsApp(it.phoneNumber, it)
            }
        }
    }

    private fun openGoogleMaps(lat: Double, lng: Double) {
        val gmmIntentUri = Uri.parse("geo:$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(
                this,
                "Google Maps tidak terpasang pada perangkat Anda",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun hireTourGuideByEmail(email: String, wisata: Wisata) {
        val subject = "Hire Tour Guide"
        val message =
            "Saya tertarik dengan ${wisata.name} di ${wisata.location}. Butuh tour guide untuk mengunjungi tempat tersebut."
        val encodedSubject = Uri.encode(subject)
        val encodedMessage = Uri.encode(message)
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$email")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, encodedSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, encodedMessage)
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(
                this,
                "Tidak ada aplikasi email yang terpasang pada perangkat Anda",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun hireTourGuideByWhatsApp(phoneNumber: String, wisata: Wisata) {
        val message =
            "Saya tertarik dengan ${wisata.name} di ${wisata.location}. Butuh tour guide untuk mengunjungi tempat tersebut."
        val encodedMessage = Uri.encode(message)
        val whatsappIntent = Intent(Intent.ACTION_VIEW)
        whatsappIntent.data =
            Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=$encodedMessage")
        if (whatsappIntent.resolveActivity(packageManager) != null) {
            startActivity(whatsappIntent)
        } else {
            Toast.makeText(
                this,
                "Tidak ada aplikasi WhatsApp yang terpasang pada perangkat Anda",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val DATA = "data"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}