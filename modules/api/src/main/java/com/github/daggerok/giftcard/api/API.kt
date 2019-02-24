package com.github.daggerok.giftcard.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class IssueCmd(
    @TargetAggregateIdentifier val id: UUID,
    val amount: Int
)

data class IssuedEvt(
    val id: UUID,
    val amount: Int
)

data class RedeemCmd(
    @TargetAggregateIdentifier val id: UUID,
    val amount: Int
)

data class RedeemedEvt(
    val id: UUID,
    val amount: Int
)

data class GiftCardSummaryQuery(
  val id: UUID
)
