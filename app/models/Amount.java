package models;

import play.data.validation.Constraints;

public class Amount {

  @Constraints.Required(message = "Amount is required")
  @Constraints.Max(value = 999999999, message = "Cannot input over " + 999999999)
  @Constraints.Min(value = 1, message = "Cannot input lower " + 1)
  protected long amount;

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public long getAmount() {
    return amount;
  }
}