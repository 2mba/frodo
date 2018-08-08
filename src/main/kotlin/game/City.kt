package org.tumba.frodo.game

import org.tumba.frodo.core.DevelopmentCard
import org.tumba.frodo.core.DevelopmentCardType
import org.tumba.frodo.core.IDevelopmentCard

class City(
    initialBuildings: List<IDevelopmentCard>
){

    var buildings: MutableList<IDevelopmentCard> = initialBuildings.toMutableList()

    fun build(card: IDevelopmentCard) {
        buildings.add(card)
    }
}

class CityFactory {

    fun createInitialCity(): City {
        return City(
            initialBuildings = mutableListOf(
                DevelopmentCard(DevelopmentCardType.WheatField),
                DevelopmentCard(DevelopmentCardType.Bakery)
            )
        )
    }
}