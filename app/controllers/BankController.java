package controllers;

import play.mvc.*;
import java.util.*;
import javax.inject.*;
import play.libs.Json;
import play.data.Form;
import play.Configuration;
import javax.inject.Inject;
import play.mvc.BodyParser;
import play.data.FormFactory;
import play.data.validation.ValidationError;

import models.*;
import views.html.*;

public class BankController extends Controller {

  @Inject Configuration configuration;

  @Inject FormFactory formFactory;

  private String titleMessage = "Welcome to Siggy's Bank";

  BankNote bankNote = new BankNote();
  Map<String, Long> mappingNotes = new HashMap<String, Long>();
  Map<String, Long> results = new HashMap<String, Long>();

  public Result menu() {
    return ok(menu.render(titleMessage, new ArrayList<String>(), new ArrayList<String>(), bankNote.getBalance()));
  }

  public Result deposit() {
    configBankNotes();
    Integer[] notes = bankNote.getBankNote();

    Arrays.sort(notes, Collections.reverseOrder());

    Form<Amount> amountForm = formFactory.form(Amount.class).bindFromRequest();

    List<String> errors = new ArrayList<String>();

    mappingNotes.clear();
    results.clear();

    if(amountForm.hasErrors()) {

      for(ValidationError err: amountForm.errors("amount")) {
        errors.add(err.message());
      }
      return badRequest(menu.render(titleMessage, errors, new ArrayList<String>(), bankNote.getBalance()));

    } else {

      long amount = amountForm.get().getAmount();
      results = calculate(notes, amount);
      if(results.get("amount") == 0) {
        results.remove("amount");
        return ok(confirmDeposit.render("Deposit", amount, results));
      } else {
        errors.add("Not support your bank notes.. need " + Arrays.toString(notes) + " bank notes");
        return badRequest(menu.render(titleMessage, errors, new ArrayList<String>(), bankNote.getBalance()));
      }
    }
  }

  public Result withdraw() {
    configBankNotes();
    Integer[] notes = bankNote.getBankNote();

    Arrays.sort(notes, Collections.reverseOrder());

    Form<Amount> amountForm = formFactory.form(Amount.class).bindFromRequest();

    List<String> errors = new ArrayList<String>();

    mappingNotes.clear();
    results.clear();

    if(amountForm.hasErrors()) {

      for(ValidationError err: amountForm.errors("amount")) {
        errors.add(err.message());
      }
      return badRequest(menu.render(titleMessage, new ArrayList<String>(), errors, bankNote.getBalance()));

    } else {

      long amount = amountForm.get().getAmount();
      results = calculate(notes, amount);
      if(results.get("amount") == 0) {
        results.remove("amount");
        return ok(confirmWithdraw.render("Withdraw", amount, results));
      } else {
        errors.add("Not support your bank notes.. need " + Arrays.toString(notes) + " bank notes");
        return badRequest(menu.render(titleMessage, new ArrayList<String>(), errors, bankNote.getBalance()));
      }
    }
  }

  public Result confirmDeposit() {
    bankNote.setBalanceBankNote(results);
    return ok(menu.render(titleMessage, new ArrayList<String>(), new ArrayList<String>(), bankNote.getBalance()));
  }

  public Result confirmWithdraw() {
    List<String> errors = bankNote.checkBalanceWithdraw(results);
    return ok(menu.render(titleMessage, new ArrayList<String>(), errors, bankNote.getBalance()));
  }

  private Map<String, Long> calculate(Integer[] notes, long amount) {
    if(notes.length == 0) {
      mappingNotes.put("amount", amount);
      return mappingNotes;
    } else {
      for(int n: notes) {
        long bankNotes = amount / n;
        if(bankNotes <= 0) {
          return calculate(Arrays.copyOfRange(notes, 1, notes.length), amount);
        } else {
          mappingNotes.put(Integer.toString(n), bankNotes);
          return calculate(Arrays.copyOfRange(notes, 1, notes.length), amount - (bankNotes * n));
        }
      }
    }
    return mappingNotes;
  }

  public void configBankNotes() {
    List<Integer> configNotes = this.configuration.getIntList("bank.notes");

    bankNote.setBankNote(configNotes.toArray(new Integer[configNotes.size()]));
  }

}
