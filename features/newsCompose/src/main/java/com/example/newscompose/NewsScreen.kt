package com.example.newscompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.common.compose.Colors
import com.example.common.models.NewsUiModel
import com.example.common.R as commonR

@Composable
fun NewsScreen(){
    val viewModel = viewModel<NewsViewModel>()
    NewsView(
        state = viewModel.state
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(
    newsItem: NewsUiModel
){
    Box(modifier = Modifier.fillMaxSize()
        .clip(RoundedCornerShape(2.dp))
        .clickable {
            // set as read
            // navigation
        }) {
        GlideImage(
            model = newsItem.imageRes, // ?
            contentDescription = "lol",
        )
        Box(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = newsItem.name
            )
            Image(
                painter = painterResource(R.drawable.news_card_divider_decor),
                contentDescription = null,
                alignment = Alignment.Center
            )
            Text(
                text = newsItem.description
            )
            Row (
                modifier = Modifier.fillMaxWidth()
                    .background(Colors.leaf),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(commonR.drawable.icon_calendar),
                    contentDescription = null,
                    tint = Colors.white
                )
                Text(
                    text = newsItem.date, //format
                    color = Colors.white,
                    fontSize = 12.sp
                )
            }
        }

    }
}

@Composable
fun NewsView(
    state: NewsScreenState
){
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.lts) { newsItem ->
                NewsCard(newsItem = newsItem)
            }
        }
    }
}