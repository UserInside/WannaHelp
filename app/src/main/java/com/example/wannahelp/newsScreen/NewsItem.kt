package com.example.wannahelp.newsScreen

import com.example.wannahelp.R

data class NewsItem(
    val imageRes: Int = R.drawable.news_card_img1,
    val title: String = "Спонсоры отремонтируют школу-интернат",
    val description: String = "Дубовская школа-интернат для детей\n" +
            "\\nс ограниченными возможностями здоровья стала первой в области …",
    val remains: String = "Осталось 13 дней (21.09 - 20.10)",
)
