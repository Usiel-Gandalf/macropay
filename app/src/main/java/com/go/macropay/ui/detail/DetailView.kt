package com.go.macropay.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.go.macropay.domain.models.MovieDetail
import com.go.macropay.ui.components.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    detailViewModel: DetailViewModel,
    navToBackView: () -> Unit,
    logout: () -> Unit,
) {
    val movie: MovieDetail = detailViewModel.movieDetail

    Scaffold(topBar = {
        TopAppBar(
            title = "Atras", navToBackView = navToBackView,
            logout = {
                logout()
            }
        )
    }, content = {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(start = 8.dp, end = 8.dp)
        ) {
            DetailMovieSection(movieDetail = movie)
        }
    })
}

@Composable
fun DetailMovieSection(movieDetail: MovieDetail) {
    val scroll = rememberScrollState(0)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Text(
                text = "Titulo: " + movieDetail.title,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 2.dp, bottom = 2.dp)
            )
            Box {
                Image(
                    painter = rememberAsyncImagePainter(movieDetail.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(690.dp)
                        .fillMaxWidth()
                        .padding(top = 1.dp, bottom = 1.dp)

                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(5.dp)
                        .background(Color.Black.copy(alpha = 0.5f)) // Fondo semitransparente para el texto y el icono
                        .padding(1.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = movieDetail.description!!,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .verticalScroll(scroll)
                        )
                    }
                }
            }

            Text(
                text = "Fecha de estreno: " + movieDetail.fechaEsterno,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp)
            )
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Duracion: " + movieDetail.duration,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Genero: " + movieDetail.genere,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}