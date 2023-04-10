package com.example.shopping_list.entity

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.shopping_list.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorApp @Inject constructor(@ApplicationContext val context: Context) {

    fun errorApi (errorMessage:String){
        val toastMessage: String
        when(errorMessage){
            "HTTP 401 " -> {
                toastMessage = context.getString(R.string.error401)
            }
            "HTTP 402 " -> {
                toastMessage = context.getString(R.string.error402)
            }
            "HTTP 404 " -> {
                toastMessage = context.getString(R.string.error404)
            }
            "HTTP 429 " -> {
                toastMessage = context.getString(R.string.error429)
            }
            else -> {
                toastMessage = context.getString(R.string.errorUser)
                Log.d("KDS", "Error $errorMessage")
            }

        }
        if (toastMessage.isNotEmpty()) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

