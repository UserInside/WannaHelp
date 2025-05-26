package com.example.newscompose.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.compose.Colors
import com.example.common.models.NewsUiModel
import com.example.newscompose.NewsScreenState
import com.example.newscompose.NewsViewModel
import com.example.newscompose.NewsViewModelFactory
import com.example.common.R as commonR

@Composable
fun NewsScreen(
    factory: NewsViewModelFactory,
    onEventClick: (newsItem: NewsUiModel) -> Unit,
    onNavigate: () -> Unit,
) {
    val viewModel: NewsViewModel = viewModel(factory = factory)
    LaunchedEffect(viewModel) {
        viewModel.loadNewsFromDB()
    }
    NewsView(
        state = viewModel.state,
        onEventClick = onEventClick,
        onNavigate = onNavigate,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsView(
    state: NewsScreenState,
    onEventClick: (newsItem: NewsUiModel) -> Unit,
    onNavigate: () -> Unit,
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(commonR.string.news),
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp,
                    color = Colors.white,
                    fontWeight = FontWeight.ExtraBold,
                )
            }, actions = {
                IconButton(
                    onClick = { onNavigate() }) {
                    Icon(
                        painter = painterResource(commonR.drawable.icon_filter),
                        tint = Colors.white,
                        contentDescription = null
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Colors.leaf,
                titleContentColor = Colors.white,
                navigationIconContentColor = Colors.white,
            )
        )
    }, content = { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.light_grey_two)
                .padding(top = innerPadding.calculateTopPadding())
                .padding(dimensionResource(commonR.dimen.spacing_xs)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(commonR.dimen.spacing_xs))
        ) {
            items(state.lts.value) { newsItem ->
                NewsCard(
                    newsItem = newsItem, onEventClick = {
                        onEventClick(newsItem)
                    }
                )
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun NewsViewPreview() {
    NewsView(
        state = NewsScreenState(),
        onNavigate = {},
        onEventClick = {},
    )
}