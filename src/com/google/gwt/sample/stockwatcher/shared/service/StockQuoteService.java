package com.google.gwt.sample.stockwatcher.shared.service;

import java.util.List;

import com.google.gwt.sample.stockwatcher.server.service.StockQuoteDao;
import com.google.gwt.sample.stockwatcher.server.service.StockQuoteLocator;
import com.google.gwt.sample.stockwatcher.shared.proxy.StockQuoteProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value = StockQuoteDao.class, locator = StockQuoteLocator.class)
public interface StockQuoteService extends RequestContext {
	Request<List<StockQuoteProxy>> listAll();
	Request<Integer> getNum();
}
