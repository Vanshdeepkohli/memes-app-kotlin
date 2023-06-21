package com.example.memes

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.memes.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

//    https://meme-api.com/gimme
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.nextMeme.setOnClickListener {
            getData()
        }

    }

    private fun getData() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait data is loading")
        progressDialog.show()

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
            override fun onResponse(
                call: Call<responseDataClass?>,
                response: Response<responseDataClass?>
            ) {
                progressDialog.dismiss()
                binding.memeAuthor.text = response.body()?.author
                binding.memeTitle.text = response.body()?.title
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeImage)
            }

            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity,"${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}