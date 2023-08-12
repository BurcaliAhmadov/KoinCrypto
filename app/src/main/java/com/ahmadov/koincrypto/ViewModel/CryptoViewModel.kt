package com.ahmadov.koincrypto.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmadov.koincrypto.model.Crypto
import com.ahmadov.koincrypto.service.CryptoApi
import com.ahmadov.koincrypto.view.RecyclerViewAdapter
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel :ViewModel() {

    val cryptoList=MutableLiveData<List<Crypto>>()
    val cryptoError=MutableLiveData<Boolean>()
    val crptoLoading=MutableLiveData<Boolean>()

    private var job: Job?=null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error ${throwable.localizedMessage}")
        cryptoError.value=true
    }

    fun getDataFromApi(){
        crptoLoading.value=true

        val BASE_URL="https://raw.githubusercontent.com/"
        val retrofit= Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CryptoApi::class.java)

/*

         viewModelScope CoroutineScope yerine istifade edile biler ferqi yoxdu
         Job yaradib  OnClearda job.cancal demek olar

        viewModelScope.launch( Dispatchers.IO + exceptionHandler ){

        }

 */

        job= CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response=retrofit.getData()

            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    cryptoError.value=false
                    crptoLoading.value=false
                    response.body()?.let{cryptos ->
                        println(cryptos)
                        cryptoList.value=cryptos

                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
