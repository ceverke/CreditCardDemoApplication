package de.adesso.blog.cep.creditcarddemo.model;

public class CreditCard {

    private String pin;

    private boolean active;

    public CreditCard(String pin) {
        this.setPin(pin);
        this.setActive(true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean checkPin(String pin) {
        return pin.equals(this.pin);
    }

}
