package tz.co.hosannahighertech.kotlinconf2024

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tz.co.hosannahighertech.kotlinconf2024.model.Movie
import kotlin.coroutines.CoroutineContext


class MoviesViewModel : ViewModel() {
    private val apiKey =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMzM4YjQ1OTNlOTU2YzBiOWJkMzA5OGFhZTE1NDdjZCIsInN1YiI6IjU5ZDg5MjhmYzNhMzY4NjIyNzAyMGJiMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xSj8f3p0xlpVYKM-piUtKiCJsOtlPkrs3kh2AO67TDY"

    private val api: WebApi by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val build = OkHttpClient.Builder()
            .addInterceptor(logging)

        build.addInterceptor(Interceptor { chain ->
            val original = chain.request();
            val request = original.newBuilder()
                .header("Authorization", "Bearer $apiKey")
                .method(original.method, original.body)
                .build()

            chain.proceed(request);
        })

        val retrofit = Retrofit.Builder()
            .client(build.build())
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WebApi::class.java)
    }

    private val _movies = MutableStateFlow(listOf<Movie>())
    val movies = _movies.asStateFlow()

    private val handler = CoroutineExceptionHandler { _: CoroutineContext, exception: Throwable ->
        exception.printStackTrace()
    }

    fun getPopular() = viewModelScope.launch(Dispatchers.IO + handler) {
        val response = api.getMovies()
        launch {
            _movies.emit(response.results)
        }
    }
}