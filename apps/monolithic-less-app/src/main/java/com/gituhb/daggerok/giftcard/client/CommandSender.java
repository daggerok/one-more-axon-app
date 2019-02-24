package com.gituhb.daggerok.giftcard.client;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommandSender {

  final CommandGateway commandGateway;

  @Transactional
  public <CMD> CompletableFuture<CMD> send(CMD cmd) {
    return commandGateway.send(cmd);
  }
}
