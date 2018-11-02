package de.adesso.blog.cep.creditcarddemo.model;

import java.util.Date;

public class IncorrectPinEvent {
	
	private CreditCard creditCard;
	
	private Date date;
	
	public IncorrectPinEvent(CreditCard creditCard) {
		this.creditCard = creditCard;
		this.date = new Date (System.currentTimeMillis());
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Date getDate() {
		return date;
	}

}
