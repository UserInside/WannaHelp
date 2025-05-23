package com.example.newscompose.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.common.compose.Colors
import com.example.common.models.NewsUiModel
import com.example.common.utils.extensions.timestampFormatter
import com.example.newscompose.R
import com.example.common.R as commonR


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(
    newsItem: NewsUiModel,
    onEventClick: (newsItem: NewsUiModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.white)
            .clip(RoundedCornerShape(2.dp))
            .clickable(
                onClick = {
                    onEventClick(newsItem)
                },
            ),
    ) {
        Box {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(commonR.dimen.spacing_xxs))
                    .height(248.dp),
                model = newsItem.imageRes,
                contentDescription = null,
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(248.dp)
                    .background(Colors.transparent),
                painter = painterResource(R.drawable.news_card_image_fade),
                contentDescription = null
            )
        }
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Colors.white), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(commonR.dimen.spacing_s))
                        .background(Colors.transparent),
                    textAlign = TextAlign.Center,
                    color = Colors.blue_grey,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 21.sp,
                    text = newsItem.name,
                )
                Image(
                    modifier = Modifier.padding(
                        top = dimensionResource(commonR.dimen.spacing_xs),
                        bottom = dimensionResource(commonR.dimen.spacing_s)
                    ),
                    painter = painterResource(R.drawable.news_card_divider_decor),
                    contentDescription = null,
                    alignment = Alignment.Center
                )
                Text(
                    modifier = Modifier.padding(horizontal = 26.dp),
                    textAlign = TextAlign.Center,
                    color = Colors.black_70,
                    fontSize = 14.sp,
                    text = newsItem.description
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(commonR.dimen.spacing_m))
                        .background(Colors.leaf),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(commonR.drawable.icon_calendar),
                        contentDescription = null,
                        tint = Colors.white
                    )
                    Text(
                        text = timestampFormatter(newsItem.date.toLong()).toString(),
                        color = Colors.white,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}