package com.google.gwt.sample.stockwatcher.server.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.sample.stockwatcher.server.domain.StockQuote;

public class StockQuoteDao {

	public List<StockQuote> listAll() {
		List<StockQuote> l = new ArrayList<StockQuote>();
		StockQuote sq = new StockQuote();
		sq.setSymbol("MSFT");
		sq.setPrice(100);
		sq.setChange(2);
		l.add(sq);
		
		sq = new StockQuote();
		sq.setSymbol("GOOG");
		sq.setPrice(90);
		sq.setChange(-2);
		l.add(sq);
		
		sq = new StockQuote();
		sq.setSymbol("BLD");
		sq.setPrice(293);
		sq.setChange(4);
		l.add(sq);
		
		return l;
	}
	
	public Integer getNum() {
		return 9;
	}
}
