package com.example.common.models.mapper

import com.example.common.models.NewsUiModel
import com.example.domain.entities.NewsDomainModel

object NewsMapper {
    fun mapNewsDomainModelToUi(model: NewsDomainModel): NewsUiModel =
        NewsUiModel(
            id = model.id,
            imageRes = model.imageRes,
            name = model.name,
            description = model.description,
            date = model.date,
            isRead = model.isRead,
            category = model.category
        )
}