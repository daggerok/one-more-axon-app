package com.gituhb.daggerok.giftcard.client;

import com.github.daggerok.giftcard.api.GiftCardSummaryQuery;
import com.github.daggerok.giftcard.api.IssueCmd;
import com.github.daggerok.giftcard.api.RedeemCmd;
import com.gituhb.daggerok.giftcard.query.GiftCardSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@RequiredArgsConstructor
public class GiftCardRunner implements CommandLineRunner {

  final CommandSender sender;
  final QueryGateway queryGateway;

  @Override
  public void run(String... args) throws Exception {

    UUID id = UUID.randomUUID();

    sender.send(new IssueCmd(id, 100));

    TimeUnit.SECONDS.sleep(2);
    sender.send(new RedeemCmd(id, 10));
    TimeUnit.SECONDS.sleep(1);
    sender.send(new RedeemCmd(id, 70));
    TimeUnit.SECONDS.sleep(1);
    sender.send(new RedeemCmd(id, 13));

    TimeUnit.SECONDS.sleep(1);
    GiftCardSummary result = queryGateway.query(new GiftCardSummaryQuery(id),
                                                ResponseTypes.instanceOf(GiftCardSummary.class))
                                         .join();
    log.info("summary: {}", result);
 /*
    queryGateway.query(GiftCardSummaryQuery.of(id), GiftCardSummary.class)
                .thenAccept(giftCardSummary -> log.info("query: {}", giftCardSummary))
                .exceptionally(throwable -> {
                  log.error("oops: {}", throwable.getLocalizedMessage(), throwable);
                  return null;
                })
                .get(3, TimeUnit.SECONDS);
*/
    System.exit(0);
  }
}
