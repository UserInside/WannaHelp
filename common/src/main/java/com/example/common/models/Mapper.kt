package com.example.common.models

import com.example.domain.entities.NewsDomainModel

object Mapper {
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