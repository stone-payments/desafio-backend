package br.com.stone.lojastartwars.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb04_history")
@Getter
@Setter
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  
  private long id;
  private String card_number;
  private String cliend_id;
  private int value;
  private String date;
  private String purchase_id;

  
}
