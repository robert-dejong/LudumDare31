package com.game.damage;

public class Hit {
	
	private int amount, x, y, duration = 0;
	private int cash = 0;
	
	public Hit(final int x, final int y, final int amount) {
		setX(x);
		setY(y);
		setAmount(amount);
	}
	
	public Hit(final int x, final int y, final int cash, final boolean isCash) {
		setX(x);
		setY(y);
		setCash(cash);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

}
