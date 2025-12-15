package com.duocuc.serena.client;

import com.duocuc.serena.config.ApiConfig
import com.duocuc.serena.data.remote.RetrofitFactory
import com.duocuc.serena.data.remote.user.UserApi

object RetrofitClient {

    val userApi: UserApi by lazy {
        RetrofitFactory.create(
            ApiConfig.USER_BASE_URL,
            UserApi::class.java
        )
    }
}
