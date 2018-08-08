package org.tumba.frodo.core

interface IDevelopmentCard : ICard {

    val triggerRange: IntRange

    val actionType: ActionType

    val productionType: ProductionType
}

private fun Int.toRange(): IntRange = IntRange(this, this)

enum class ActionType {
    EarnFromBankInAnyPlayerTurn, // Blue
    EarnFromBankInMyTurn, // Green
    EarnFromActivePlayerInTheirTurn, // Red
    EarnFromOtherPlayerInMyTurn // Violet
}

enum class ProductionType {
    Factory,
    Catering,
    RawMaterial,
    RawFood,
    Shop,
    Market,
    Farm,
}

enum class DevelopmentCardType(
    override val triggerRange: IntRange,
    override val actionType: ActionType,
    override val productionType: ProductionType,
    override val cost: Int
) : IDevelopmentCard {

    WheatField(
        triggerRange = 1.toRange(),
        actionType = ActionType.EarnFromBankInAnyPlayerTurn,
        productionType = ProductionType.RawFood,
        cost = 1),
    Bakery(
        triggerRange = 2..3,
        actionType = ActionType.EarnFromBankInMyTurn,
        productionType = ProductionType.Shop,
        cost = 1)
}

class DevelopmentCard(type: DevelopmentCardType) : IDevelopmentCard by type