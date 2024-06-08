package tz.co.hosannahighertech.kotlinconf2024.model

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)