package de.adesso.blog.cep.creditcarddemo.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.adesso.blog.cep.creditcarddemo.model.CreditCard;
import de.adesso.blog.cep.creditcarddemo.model.Transaction;
import de.adesso.blog.cep.creditcarddemo.service.TransactionService;

@Component
public class FireEvents {

	@Autowired
	private TransactionService transactionService;

	private CreditCard creditCard1, creditCard2, creditCard3;
	
	Logger LOGGER = LoggerFactory.getLogger(FireEvents.class);

	@PostConstruct
	public void initialize() {
		creditCard1 = new CreditCard("1909");
		creditCard2 = new CreditCard("1234");
		creditCard3 = new CreditCard("0000");
	}

	@Scheduled(fixedRate = 10000)
	public void addNonVarifiedTransactions() {
		this.transactionService.addTransaction(getTransaction("11.11", Locale.GERMANY, creditCard1), "1909");
		LOGGER.info("Sending not approved transaction");
		// Expected: credit card 1 will be locked after 40 seconds because of rule 2	
	}
	
	@Scheduled(fixedRate = 5000)
	public void addIncorrectPinTransactions() {
		this.transactionService.addTransaction(getTransaction("99.99", Locale.GERMANY, creditCard2), "1909");
		// Notice that 1909 is not the correct pin of creditCard2
		LOGGER.info("Sending transaction with invalid pin");
		// Expected: credit card 2 will be locked after 10 seconds because of rule 3	
	}
	
	@Scheduled(fixedRate = 7000)
	public void addTransactionsWithDifferentCountries() {
		this.transactionService.addTransaction(getTransaction("99.99", Locale.GERMANY, creditCard3), "0000");
		this.transactionService.addTransaction(getTransaction("99.99", Locale.CANADA, creditCard3), "0000");
		LOGGER.info("Sending transactions from two different countries");
		// Expected: credit card 2 will be locked at once seconds because of rule 1
	}
	
	
	private Transaction getTransaction(String ammount, Locale locale, CreditCard creditCard) {
		Transaction tx = new Transaction();
		tx.setAmount(new BigDecimal(ammount));
		tx.setExecutionLocation(locale);
		tx.setExecutionDate(new Date(System.currentTimeMillis()));
		tx.setCreditCard(creditCard);
		tx.setPersonalApproved(false);
		return tx;
	}

}
