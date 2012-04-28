package com.google.gwt.sample.stockwatcher.shared.service;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface StockWatcherRequestFactory extends RequestFactory {
	StockQuoteService stockQuote();
}
