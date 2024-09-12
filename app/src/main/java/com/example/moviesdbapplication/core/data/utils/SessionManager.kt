package com.example.moviesdbapplication.core.data.utils

import com.example.moviesdbapplication.utils.SharedPrefs

//class SessionManager(private val sharedPrefs: SharedPrefs) {
//    // Method to check if the access token has expired
////    fun isAccessTokenExpired(): Boolean {
////        val currentTimeMillis = System.currentTimeMillis()
////        return currentTimeMillis >= sharedPrefs.getRefreshToken()
////    }
//
//    // Method to update the access token and its expiration time in the session
//    fun updateAccessToken(token: String?, expiresIn: Int?) {
////        expiresIn?.let {
////            sharedPrefs.saveExpiredDateToken(expiresIn.toLong())
////        }
//        token?.let { sharedPrefs.saveRefreshToken(it) }
//
//    }
//
//    fun getAccessToken(): String {
//        return sharedPrefs.getRefreshToken()
//    }
//
//    fun getRefreshToken() {
//        sharedPrefs.getRefreshToken()
//    }
//
//    fun getRefreshTokenParams(): HashMap<String, String> {
//        val data = HashMap<String, String>()
//        data["client_id"] = "android"
//        data["grant_type"] = "refresh_token"
//        data["client_secret"] = BuildConfig.CLIENT_SECRET
//        data["scope"] = "openid"
//        data["username"] = sharedPrefs.getName()
//        data["refresh_token"] = sharedPrefs.getRefreshToken()
//        return data
//    }
//
//    fun isAccessTokenEmpty(): Boolean {
//        return sharedPrefs.getRefreshToken().isEmpty()
//    }
//}