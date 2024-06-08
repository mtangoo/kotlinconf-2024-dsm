package tz.co.hosannahighertech.kotlinconf2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import tz.co.hosannahighertech.kotlinconf2024.model.Movie
import tz.co.hosannahighertech.kotlinconf2024.ui.theme.KotlinConf2024Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinConf2024Theme {
                val viewModel: MoviesViewModel by viewModels()
                val movies = viewModel.movies.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(movies.value.size) {
                            MovieRow(movie = movies.value[it])
                        }
                    }
                }

                LaunchedEffect(true) {
                    viewModel.getPopular()
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = movie.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${movie.poster_path}",
            contentDescription = null,
        )

        Text(
            text = movie.title,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(8.dp)
        )

        Divider()
    }
}