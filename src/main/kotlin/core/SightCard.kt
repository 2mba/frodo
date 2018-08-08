package org.tumba.frodo.core

enum class SightCard(override val cost: Int): ICard {
    Terminal(cost = 4),
    RadioTower(cost = 22),
    AmusementPark(cost = 16),
    ShoppingCenter(cost = 4),
}