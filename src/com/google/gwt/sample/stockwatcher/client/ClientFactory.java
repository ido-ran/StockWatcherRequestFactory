package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.sample.stockwatcher.shared.service.StockWatcherRequestFactory;

public interface ClientFactory {
	EventBus getEventBus();
	StockWatcherRequestFactory getRF();
}
