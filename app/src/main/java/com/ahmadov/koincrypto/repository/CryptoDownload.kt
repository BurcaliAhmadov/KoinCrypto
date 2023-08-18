package com.ahmadov.koincrypto.repository

import com.ahmadov.koincrypto.model.Crypto
import com.ahmadov.koincrypto.util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos():Resource<List<Crypto>>
}