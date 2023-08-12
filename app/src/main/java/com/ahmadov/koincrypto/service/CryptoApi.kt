package com.ahmadov.koincrypto.service

import com.ahmadov.koincrypto.model.Crypto
import retrofit2.Response
import retrofit2.http.GET


//https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
//BASE_URL -> https://raw.githubusercontent.com/

interface CryptoApi {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData():Response<List<Crypto>>


}