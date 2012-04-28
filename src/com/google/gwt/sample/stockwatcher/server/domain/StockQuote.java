package com.google.gwt.sample.stockwatcher.server.domain;

public class StockQuote {
	private String symbol;
	private double price;
	private double change;

	public StockQuote() {
	}

	public StockQuote(String symbol, double price, double change) {
		this.symbol = symbol;
		this.price = price;
		this.change = change;
	}
	
	public int getVersion() {
		return 0;
	}
	
	public String getId() {
		return symbol;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public double getPrice() {
		return this.price;
	}

	public double getChange() {
		return this.change;
	}

	public double getChangePercent() {
		return 100.0 * this.change / this.price;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setChange(double change) {
		this.change = change;
	}
}
