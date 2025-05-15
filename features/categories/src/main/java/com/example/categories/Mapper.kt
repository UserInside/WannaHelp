package com.example.categories

import com.example.domain.entities.CategoryDomainModel

object Mapper {
    fun mapCategoriesFromDomainToUi(categoryDomainModel: CategoryDomainModel): CategoryUiModel{
        return CategoryUiModel(
            id = categoryDomainModel.id,
            name = categoryDomainModel.name,
            name_en = categoryDomainModel.name_en,
            image = categoryDomainModel.image,
            )
    }
}