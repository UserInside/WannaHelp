package com.example.newscompose.mapper

import com.example.common.models.NewsUiModel
import com.example.domain.entities.NewsDomainModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NewsMapperTest {

    @Test
    fun testNewsMapper() {
        val actual = NewsMapper.mapNewsDomainModelToUi(NewsDomainModel())
        val expected = NewsUiModel()
        Assertions.assertEquals(expected, actual)
    }
}