package com.example.projetodio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.projetodio.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding

   private var items= arrayListOf("English", "Hind", "Bengali", "Gujarati","Portuguese", "Tamil", "Telugu", "Spanish")

    private var conditions= DownloadConditions.Builder()
        .requireWifi()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding = DataBindingUtil.setContentview(this, R.layout.activity_main)
        val itemAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_dropdown_item_1line,
            items)

        binding.languageFrom.setAdapter(itemAdapter)

        binding.languageTo.setAdapter(itemAdapter)

        binding.btnTranslate.setOnClickListener {
            val options= TranslatorOptions.Builder()
                .setSourceLanguage(selecFrom())
                .setTargetLanguage(selelecTo())
                .build()

            val translator= Translation.getClient(options)


            translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                   translator.translate(binding.input.text.toString())
                       .addOnSuccessListener {
                           binding.txtOutput.text=it

                       }

                }

                .addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

            if (binding.input.text == null){

                binding.input.showSoftInputOnFocus = false
            }

        }
        binding.btnLimpar.setOnClickListener {
            binding.input.text.clear()
            binding.txtOutput.text = ""
        }
    }


    private fun selecFrom(): String {
        return  when(binding.languageFrom.text.toString()){
            "" -> TranslateLanguage.ENGLISH
            "English" -> TranslateLanguage.ENGLISH
            "Hind" -> TranslateLanguage.HINDI
            "Bengali" -> TranslateLanguage.BENGALI
            "Gujarati" -> TranslateLanguage.GUJARATI
            "Spanish" -> TranslateLanguage.SPANISH
            "Tamil" -> TranslateLanguage.TAMIL
            "Telegu" -> TranslateLanguage.TELUGU
            "Portuguese" -> TranslateLanguage.PORTUGUESE

            else -> TranslateLanguage.ENGLISH
        }
    }

    private fun selelecTo(): String {
        return  when(binding.languageTo.text.toString()){
            "" -> TranslateLanguage.PORTUGUESE
            "English" -> TranslateLanguage.ENGLISH
            "Hind" -> TranslateLanguage.HINDI
            "Bengali" -> TranslateLanguage.BENGALI
            "Gujarati" -> TranslateLanguage.GUJARATI
            "Spanish" -> TranslateLanguage.SPANISH
            "Tamil" -> TranslateLanguage.TAMIL
            "Telegu" -> TranslateLanguage.TELUGU

            else -> TranslateLanguage.PORTUGUESE

    }
   }
}