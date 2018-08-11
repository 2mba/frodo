package org.tumba.frodo.domain.game

import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.DevelopmentCardType
import org.tumba.frodo.domain.core.IDevelopmentCard

class CardStore(
    initialCards: List<IDevelopmentCard>
){
    val cards: MutableList<IDevelopmentCard> = initialCards.toMutableList()

    fun buyCard(card: IDevelopmentCard): Boolean {
        return cards.remove(card)
    }
}

class CardStoreFactory {

    fun createCardStore(): CardStore {
        return CardStore(
            mutableListOf<IDevelopmentCard>().apply {
                addAll(createDevelopmentCards(DevelopmentCardType.WheatField, 4))
                addAll(createDevelopmentCards(DevelopmentCardType.Bakery, 4))
            }
        )
    }

    private fun createDevelopmentCards(type: DevelopmentCardType, count: Int): List<IDevelopmentCard> {
        return mutableListOf<IDevelopmentCard>().apply {
            0.until(count).forEach {
                add(DevelopmentCard(type))
            }

        }
    }
}