package com.google.gwt.sample.stockwatcher.server.service;

import com.google.gwt.sample.stockwatcher.server.domain.StockQuote;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class StockQuoteLocator implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		return new StockQuote();
	}

}
