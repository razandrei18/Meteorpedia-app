package com.example.meteorpedia.models

data class Resource<out T>(val status: Status, val data: T?, val message: String? = null){

    companion object{
        fun <T> success(data: T?) =
            Resource(Status.SUCCESS, data)

        fun <T> error(errorMessage: String?, data: T? = null) = Resource(
            Status.ERROR,
            data,
            errorMessage
        )
    }
}

enum class Status{
    ERROR,
    SUCCESS
}