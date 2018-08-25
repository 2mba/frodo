package org.tumba.frodo.domain.game

import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.DevelopmentCardType
import org.tumba.frodo.domain.core.SightCard

class Cards(
    initialBuildings: List<DevelopmentCard>
){

    val buildings: MutableList<DevelopmentCard> = initialBuildings.toMutableList()
    val sightCards: MutableList<SightCard> = mutableListOf()

    fun add(card: DevelopmentCard) {
        buildings.add(card)
    }
}

class CityFactory {

    fun createInitialCards(): Cards {
        return Cards(
            initialBuildings = mutableListOf(
                DevelopmentCard(DevelopmentCardType.WheatField),
                DevelopmentCard(DevelopmentCardType.Bakery)
            )
        )
    }
}