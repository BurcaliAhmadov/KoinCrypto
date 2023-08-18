package com.ahmadov.koincrypto.repository

import com.ahmadov.koincrypto.model.Crypto
import com.ahmadov.koincrypto.service.CryptoApi
import com.ahmadov.koincrypto.util.Resource

class CryptoDownloadImpl(
    private val api:CryptoApi
):CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<Crypto>> {
        return try {
            val response = api.getData()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error("No data",null)
        }
    }
}