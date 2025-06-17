package com.example.profile

import com.example.domain.entities.FriendCardDomainModel
import com.example.profile.FriendCardMapper.mapFriendsFromDomainToUi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FriendCardMapperTest {

    @Test
    fun `mapper should map Domain Model to Ui`() {

        val expected = FriendCardItem(
            id = 1,
            image = "image",
            name = "name",
        )

        val actual = mapFriendsFromDomainToUi(
            FriendCardDomainModel(
                id = 1,
                image = "image",
                name = "name",
            )
        )

        assertEquals(expected, actual)
    }
}