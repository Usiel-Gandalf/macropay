package com.go.macropay.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.go.macropay.R
import com.go.macropay.domain.models.Movie
import com.go.macropay.ui.components.TopAppBarWithoutIconAndWithAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    mainViewModel: MainViewModel,
    navigateToMovieDetail: (idMovie: Int) -> Unit,
    logout: () -> Unit,
) {
    val loading by mainViewModel.loading.observeAsState(initial = false)
    val error by mainViewModel.error.observeAsState(initial = true)

    LaunchedEffect(true) {
        mainViewModel.getMoviesList()
    }

    val movieList: MutableList<Movie> = mainViewModel.filterMovieList
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarWithoutIconAndWithAction(
                title = "MAIN",
                logout = {
                    logout()
                },
                scrollBehavior = scrollBehavior
            )
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(all = 8.dp)
            ) {
                LazyCardsForMovies(movieList = movieList, onClicGoToDetail = { idMovie ->
                    navigateToMovieDetail(idMovie)
                })
            }
        })
}

@Composable
fun LazyCardsForMovies(
    movieList: List<Movie>,
    onClicGoToDetail: (idMovie: Int) -> Unit,
) {
    lateinit var movie: Movie

    if (movieList.isNotEmpty()) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(movieList.size) {
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(360.dp)
                        .fillMaxSize()
                        .background(color = Color.Black, shape = RoundedCornerShape(12.dp))
                ) {
                    Box {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clickable {
                                    onClicGoToDetail(movieList[it].id)
                                },
                            painter = rememberAsyncImagePainter(movieList[it].image),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(5.dp)
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(1.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = "Calificacion",
                                    tint = Color.White
                                )
                                Text(
                                    text = movieList[it].calification,
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    Text(
                        text = movieList[it].title,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(30.dp)
                            .align(Alignment.CenterHorizontally)
                            .verticalScroll(rememberScrollState(0))
                            .background(Color.Black)
                    )
                }
            }
        }

    } else {
        Text(
            text = "Sin resultado", color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp)

        )
    }
}