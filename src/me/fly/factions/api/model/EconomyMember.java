package me.fly.factions.api.model;

/**
 * The {@code EconomyMember} class represents anything or anyone that can store money
 */
public interface EconomyMember extends Savable {

    /**
     * Returns the current balance of the object
     *
     * @return the balance of the object
     */

    double getBalance();

    /**
     * Sets the balance of this object
     *
     * @param x the value to set the balance to
     */

    void setBalance(double x);

    /**
     * Adds an amount of money to this object's balance
     *
     * @param x the amount to add
     */

    void addToBalance(double x);

    /**
     * Takes an amount of money from the object's balance
     *
     * @param x the amount to take
     */

    void takeFromBalance(double x);
}
