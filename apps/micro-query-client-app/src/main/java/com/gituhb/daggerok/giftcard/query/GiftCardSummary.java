package com.gituhb.daggerok.giftcard.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Data
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of")
public class GiftCardSummary {

  @Id
  @Column(nullable = false)
  UUID id;

  @Column(nullable = false)
  int initialValue;

  @Column(nullable = false)
  int remainingValue;
}
