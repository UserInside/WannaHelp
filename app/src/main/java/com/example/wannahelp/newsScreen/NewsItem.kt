package com.example.wannahelp.newsScreen

import com.example.wannahelp.common.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class News(
    val news : List<NewsItem> = listOf()
)
@Serializable
data class NewsItem(
    val id: Int = 0,
    @SerialName("image")
    val imageRes: String = "/home/igor/WannaHelpApplication/WannaHelp/app/src/main/res/drawable/news_card_img1.png",
    val title: String = "Спонсоры отремонтируют школу-интернат",
    val description: String = "Дубовская школа-интернат для детей\n" +
            "\\nс ограниченными возможностями здоровья стала первой в области …",
    val date: String = "Осталось 13 дней (21.09 - 20.10)",
    val category: Category = Category.KIDS,
)
