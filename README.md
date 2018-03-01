# Siggy's bank Asssignment

## Version
- sbt version ```1.1.0```
- java version ```1.8```

## Feauture
- Deposit
- Withdraw
- Show balance

---

## Configuration
***support when it have to change currencies, country, bank notes***

Can configuration bank notes at ```application.conf```
and input bank note in ```bank.notes``` variable
example : ```bank.notes = [20, 50, 70]```

---

## Hot to run this project
- first step : Download build tool for this project using ```sbt```
and this [sbt](https://www.scala-sbt.org/download.html) bring you to download sbt (support all platform)
- second step : Clone this project to your repository and checkout to ```master``` branch
- third step : Go to command line or terminal and change directory to ```Siggy-Bank```
and type ```sbt run``` ***(maybe first time it take long time)***
- if unknown command sbt please exit your cmd or terminal and go to third step again
- forth step : When it application started then go to browser and type ```localhost:9000```