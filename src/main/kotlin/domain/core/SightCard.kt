package org.tumba.frodo.domain.core

enum class SightCard(override val cost: Int): ICard {
    Terminal(cost = 4),
    RadioTower(cost = 22),
    AmusementPark(cost = 16),
    ShoppingCenter(cost = 4);

    override val cardName: String = name
}