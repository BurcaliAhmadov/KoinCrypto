package com.ahmadov.koincrypto.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmadov.koincrypto.model.Crypto
import com.ahmadov.koincrypto.repository.CryptoDownload
import com.ahmadov.koincrypto.util.Resource
import kotlinx.coroutines.*

class CryptoViewModel(private val cryptoDownloadRepository : CryptoDownload) :ViewModel() {

    val cryptoList=MutableLiveData<Resource<List<Crypto>>>()
    val cryptoError=MutableLiveData<Resource<Boolean>>()
    val cryptoLoading=MutableLiveData<Resource<Boolean>>()

    private var job: Job?=null
    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error ${throwable.localizedMessage}")
        cryptoError.value=Resource.error(throwable.localizedMessage?:"Error",true)
    }

    fun getDataFromApi(){
        cryptoLoading.value=Resource.loading(true)

        job=CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource=cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main){
                resource.data?.let {
                    cryptoList.value=resource
                    cryptoLoading.value=Resource.loading(false)
                    cryptoError.value=Resource.error("",false)
                }
            }

        }
        /*
        crptoLoading.value=true

        val BASE_URL="https://raw.githubusercontent.com/"
        val retrofit= Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CryptoApi::class.java)



         viewModelScope CoroutineScope yerine istifade edile biler ferqi yoxdu
         Job yaradib  OnClearda job.cancal demek olar

        viewModelScope.launch( Dispatchers.IO + exceptionHandler ){

        }



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

         */
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
