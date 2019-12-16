package ru.otus.api.model;

import ru.otus.orm.Id;

import java.math.BigDecimal;

public class Accaunt {
  @Id
  private long no;

  private String type;

  private BigDecimal rest;public long getNo() {
            return no;
        }

  public void setNo(long no) {
            this.no = no;
        }

  public String getType() {
            return type;
        }

  public void setType(String type) {
            this.type = type;
        }

  public BigDecimal getRest() {
            return rest;
        }

  public void setRest(BigDecimal rest) {
            this.rest = rest;
        }

  @Override public String toString() {
    return "User{" +
            "no=" + no +
            ", type='" + type + '\'' +
            ", rest='" + rest + "\' }";
  }
}
