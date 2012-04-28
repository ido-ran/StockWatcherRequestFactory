package com.google.gwt.sample.stockwatcher.shared.proxy;

import com.google.gwt.sample.stockwatcher.server.domain.StockQuote;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(value = StockQuote.class)
public interface StockQuoteProxy extends EntityProxy {
	String getSymbol();
	void setSymbol(String symbol);
	
	double getPrice();
	void setPrice(double price);
}
