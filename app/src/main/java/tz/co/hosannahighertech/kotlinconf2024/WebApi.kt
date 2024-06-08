package tz.co.hosannahighertech.kotlinconf2024

import retrofit2.http.GET
import retrofit2.http.Query
import tz.co.hosannahighertech.kotlinconf2024.model.MovieResponse

interface WebApi {
    @GET("movie/popular")
    suspend fun getMovies(): MovieResponse
}