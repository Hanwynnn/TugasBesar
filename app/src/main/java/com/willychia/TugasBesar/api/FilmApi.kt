package com.willychia.TugasBesar.api

class FilmApi {
    companion object{
        val BASE_URL = "http://192.168.185.34:80/UGD_API/public/api/"

        val GET_ALL_URL = BASE_URL + "film/"
        val GET_BY_ID_URL = BASE_URL + "film/"
        val ADD_URL = BASE_URL + "film"
        val UPDATE_URL = BASE_URL + "film/"
        val DELETE_URL = BASE_URL + "film/"
    }
}