package com.gituhb.daggerok.giftcard.query;

import com.github.daggerok.giftcard.api.GiftCardSummaryQuery;
import com.github.daggerok.giftcard.api.IssuedEvt;
import com.github.daggerok.giftcard.api.RedeemedEvt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCardSummaryProjector {

  final EntityManager entityManager;

  @EventHandler
  @Transactional
  public void on(IssuedEvt evt) {
    log.info(evt.toString());
    entityManager.persist(GiftCardSummary.of(evt.getId(), evt.getAmount(), evt.getAmount()));
  }

  @EventHandler
  @Transactional
  public void on(RedeemedEvt evt) {
    GiftCardSummary giftCardSummary = entityManager.find(GiftCardSummary.class, evt.getId());
    log.info("evt: {}, to: {}", evt, giftCardSummary);
    entityManager.merge(GiftCardSummary.of(giftCardSummary.getId(),
                                           giftCardSummary.getInitialValue(),
                                           giftCardSummary.getRemainingValue() - evt.getAmount()));
  }

  @QueryHandler
  public GiftCardSummary handle(GiftCardSummaryQuery query) {
    return entityManager.find(GiftCardSummary.class, query.getId());
  }
}
