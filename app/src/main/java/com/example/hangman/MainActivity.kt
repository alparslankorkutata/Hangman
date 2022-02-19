package com.example.hangman

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.hangman.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var dictionary = arrayOf("")
    private var index = 0
    private val letters : MutableList<Char> = mutableListOf()
    private var successes = 0
    private var lost = 0
    private val images = arrayOf (R.drawable.zero, R.drawable.one, R.drawable.two,
        R.drawable.three, R.drawable.four, R.drawable.five,
        R.drawable.six, R.drawable.seven, R.drawable.eight,
        R.drawable.nine, R.drawable.ten)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dictionary = resources.getStringArray(R.array.words)
        initializeGame()
    }

    private fun generateWord(): Int {
        return Random.nextInt(0, dictionary.size)
    }

    private fun initializeGame() {
        successes = 0
        lost = 0
        letters.clear()
        binding.image.setImageResource(images[0])
        index = generateWord()
        var w = ""
        for (i in dictionary[index].indices) {
            w += "_ "
        }
        binding.word.text = w
    }

    fun check(view: View) {
        val b = view as Button
        val let = b.text[0]
        if (!letters.contains(let)) {
            val w = binding.word.text.toString().toCharArray()
            letters.add(let)
            var count = 0
            for (i in dictionary[index].indices) {
                if (Character.toLowerCase(let) == dictionary[index][i]) {
                    w[2 * i] = dictionary[index][i]
                    count++
                }
            }
            if (count != 0) { // letter is in word
                var mes = ""
                for (i in w.iterator()) {
                    mes += i
                }
                binding.word.text = mes
                successes += count
                if (successes == dictionary[index].length) {
                    Toast.makeText(this, "Tebrikler!", Toast.LENGTH_SHORT).show()
                    initializeGame()
                }
            } else {
                Toast.makeText(this, "Yanlış! Denemeye devam et!", Toast.LENGTH_SHORT).show()
                // change image, check if not lost
                lost++
                binding.image.setImageResource(images[lost])
                if (lost == images.size - 1) {
                    Toast.makeText(this, "Kaybettin! Kelime: " + dictionary[index], Toast.LENGTH_SHORT).show()
                    initializeGame()
                }
            }
        } else {
            Toast.makeText(this, "Bu harf zaten kullanıldı...", Toast.LENGTH_SHORT).show()
        }
    }
}