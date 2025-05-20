package com.example.profile

import com.example.domain.entities.FriendCardDomainModel

object Mapper {
    fun mapFriendsFromDomainToUi(friendCardDomainModel: FriendCardDomainModel): FriendCardItem{
        return FriendCardItem(
            id = friendCardDomainModel.id,
            image = friendCardDomainModel.image,
            name = friendCardDomainModel.name,
        )
    }
}