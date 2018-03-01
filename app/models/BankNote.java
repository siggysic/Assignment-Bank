package models;

import java.util.*;

public class BankNote {

  protected Integer[] bankNote = {20, 50};
  protected Map<String, Long> balanceBankNote = new HashMap<String, Long>();

  public Integer[] getBankNote() {
    return bankNote;
  }

  public void setBalanceBankNote(Map<String, Long> amountDeposit) {
    if(this.balanceBankNote == null) {
      for(int n: this.bankNote) {
        Long deposit = amountDeposit.get(Integer.toString(n));
        if(deposit != null) {
          this.balanceBankNote.put(Integer.toString(n), deposit);
        }
      }
    } else {
      for(int n: this.bankNote) {
        Long note = this.balanceBankNote.get(Integer.toString(n));
        Long deposit = amountDeposit.get(Integer.toString(n));
        if(note != null && deposit != null) {
          this.balanceBankNote.put(Integer.toString(n), note + deposit);
        } else if(note == null && deposit != null) {
          this.balanceBankNote.put(Integer.toString(n), deposit);
        }
      }
    }
  }

  public List<String> checkBalanceWithdraw(Map<String, Long> amountWithdraw) {
    List<String> errors = new ArrayList<String>();
    Map<String, Long> mockupNotes = new HashMap<String, Long>();
    if(this.balanceBankNote == null) {
      errors.add("Balance is not enough to withdraw");
    } else {
      for(int n: this.bankNote) {
        Long note = this.balanceBankNote.get(Integer.toString(n));
        Long withdraw = amountWithdraw.get(Integer.toString(n));
        if(note != null && withdraw != null) {
          if(note - withdraw < 0) {
            if(withdraw > note) {
              errors.add("Balance is not enough to withdraw");
            } else {
              errors.add("This bank notes " + n + " is not enough");
            }
          } else {
            mockupNotes.put(Integer.toString(n), note - withdraw);
          }
        } else if(note == null && withdraw != null) {
          errors.add("Balance is not enough to withdraw");
        }
      }
    }

    if(errors.size() == 0) {
      for(int n: this.bankNote) {
        Long note = this.balanceBankNote.get(Integer.toString(n));
        Long withdraw = amountWithdraw.get(Integer.toString(n));
        if(note != null && withdraw != null) {
          if(note - withdraw >= 0) {
            this.balanceBankNote.put(Integer.toString(n), note - withdraw);
          }
        }
      }
    }
    return errors;
  }

  public Map<String, Long> getBalanceBankNote() {
    return this.balanceBankNote;
  }

  public Long getBalance() {
    long balance = 0;
    for(int n: bankNote) {
      Long amountOfBankNote = this.balanceBankNote.get(Integer.toString(n));
      if(amountOfBankNote != null) {
        balance = balance + (amountOfBankNote * n);
      }
    }
    return balance;
  }
}