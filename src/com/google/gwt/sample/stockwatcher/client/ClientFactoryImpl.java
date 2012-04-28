package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.sample.stockwatcher.shared.service.StockWatcherRequestFactory;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final StockWatcherRequestFactory rf = GWT.create(StockWatcherRequestFactory.class);
	
	public ClientFactoryImpl() {
		super();
		rf.initialize(eventBus);
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public StockWatcherRequestFactory getRF() {
		return rf;
	}

}
