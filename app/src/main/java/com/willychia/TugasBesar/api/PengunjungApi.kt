package com.willychia.TugasBesar.api

class PengunjungApi {
    companion object{
        val BASE_URL = "http://192.168.116.34:80/UGD_API/public/api/"

        val GET_ALL_URL = BASE_URL + "pengunjung/"
        val GET_BY_ID_URL = BASE_URL + "pengunjung/"
        val ADD_URL = BASE_URL + "pengunjung"
        val UPDATE_URL = BASE_URL + "pengunjung/"
        val DELETE_URL = BASE_URL + "pengunjung/"
    }
}