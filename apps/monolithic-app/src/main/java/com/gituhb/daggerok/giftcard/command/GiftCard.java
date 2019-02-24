package com.gituhb.daggerok.giftcard.command;

import com.github.daggerok.axon.api.IssueCmd;
import com.github.daggerok.axon.api.IssuedEvt;
import com.github.daggerok.axon.api.RedeemCmd;
import com.github.daggerok.axon.api.RedeemedEvt;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Log4j2
@Aggregate
@NoArgsConstructor
public class GiftCard {

  @AggregateIdentifier UUID id;
  int remainingAmount;

  @CommandHandler
  public GiftCard(IssueCmd cmd) {
    log.info("handling {}", () -> cmd);
    if (cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
    apply(new IssuedEvt(cmd.getId(), cmd.getAmount()));
  }

  @CommandHandler
  public void handle(RedeemCmd cmd) {
    log.info("handling {}", () -> cmd);
    if (cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
    if (cmd.getAmount() > remainingAmount) throw new IllegalArgumentException("amount > remainingAmount");
    apply(new RedeemedEvt(cmd.getId(), cmd.getAmount()));
  }

  @EventSourcingHandler
  public void on(IssuedEvt evt) {
    log.info("on {}", () -> evt);
    id = evt.getId();
    remainingAmount = evt.getAmount();
  }

  @EventSourcingHandler
  public void on(RedeemedEvt evt) {
    log.info("on {}", () -> evt);
    remainingAmount -= evt.getAmount();
  }
}
