package com.google.gwt.sample.stockwatcher.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.sample.stockwatcher.shared.proxy.StockQuoteProxy;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAP12Binding;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockWatcher implements EntryPoint {

	private static final int REFRESH_INTERVAL = 5000; // 5s
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addStockButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();
	private ArrayList<String> stocks = new ArrayList<String>();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create table for stock data.
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");
		
		stocksFlexTable.setCellPadding(6);
		
		stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		stocksFlexTable.addStyleName("watchList");
		stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
		
	    // Assemble Add Stock panel.
		addPanel.add(newSymbolTextBox);
		addPanel.add(addStockButton);
		addPanel.addStyleName("addPanel");
		
	    // Assemble Main panel.
		mainPanel.add(stocksFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);
		
	    // Associate the Main panel with the HTML host page.
		RootPanel.get("stockList").add(mainPanel);
		
	    // Move cursor focus to the input box.
		newSymbolTextBox.setFocus(true);
		
		loadStockQuotes();
		
		// setup timer
		Timer refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshWatchList();
			}
		}; 
		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
		
		// Listen for mouse events
		addStockButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addStock();
			}
		});
		
		newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					addStock();
				}
			}
		});
	}

	private void loadStockQuotes() {
		ClientFactoryImpl impl = new ClientFactoryImpl();
//		impl.getRF().stockQuote().listAll().fire(new Receiver<List<StockQuoteProxy>>() {
//
//			@Override
//			public void onSuccess(List<StockQuoteProxy> response) {
//				for (StockQuoteProxy sq : response) {
//					addStockToTable(sq.getSymbol());
//				}
//			}
//		});
		impl.getRF().stockQuote().getNum().fire(new Receiver<Integer>() {

			@Override
			public void onSuccess(Integer response) {
				lastUpdatedLabel.setText(response.toString());
			}
		});
	}

	protected void refreshWatchList() {
		final double MAX_PRICE = 100.0; // $100.00
	    final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

	    StockPrice[] prices = new StockPrice[stocks.size()];
	    for (int i = 0; i < stocks.size(); i++) {
	      double price = Random.nextDouble() * MAX_PRICE;
	      double change = price * MAX_PRICE_CHANGE
	          * (Random.nextDouble() * 2.0 - 1.0);

	      prices[i] = new StockPrice(stocks.get(i), price, change);
	    }

	    updateTable(prices);
	}

	private void updateTable(StockPrice[] prices) {
		for (int i = 0; i < prices.length; i++) {
			updateTable(prices[i]);
		}
		
		lastUpdatedLabel.setText("Last Update: " + 
				DateTimeFormat.getMediumDateTimeFormat().format(new Date()));
	}

	private void updateTable(StockPrice stockPrice) {
		if (!stocks.contains(stockPrice.getSymbol())) {
			return;
		}
		
		int row = stocks.indexOf(stockPrice.getSymbol()) + 1;
		
		String priceText = NumberFormat.getFormat("#,##0.00").format(stockPrice.getPrice());
		NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
		String changeText = changeFormat.format(stockPrice.getChange());
		String changePercentText = changeFormat.format(stockPrice.getChangePercent());
		
		stocksFlexTable.setText(row, 1, priceText);
		//stocksFlexTable.setText(row, 2, changeText + " (" + changePercentText + "%)");
		Label changeWidget = (Label)stocksFlexTable.getWidget(row, 2);
		changeWidget.setText(changeText + " (" + changePercentText + "%)");
		
		String changeStyle = "noChange";
		if (stockPrice.getChangePercent() < -0.1f) {
			changeStyle = "negativeChange";
		} else if (stockPrice.getChangePercent() > 0.1f) {
			changeStyle = "positiveChange";
		}
		
		changeWidget.setStyleName(changeStyle);
	}

	protected void addStock() {
		final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		newSymbolTextBox.setFocus(true);
		
		if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
			Window.alert("'" + symbol + "' is not valid symbol");
			newSymbolTextBox.selectAll();
			return;
		}
		
		newSymbolTextBox.setText("");
		
		if (stocks.contains(symbol))
			return;
		
		addStockToTable(symbol);
		
		refreshWatchList();
	}

	private void addStockToTable(final String symbol) {
		int row = stocks.size() + 1;
		stocks.add(symbol);
		stocksFlexTable.setText(row, 0, symbol);
		stocksFlexTable.setWidget(row, 2, new Label());
		
		stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
		stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
		stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

		Button removeButton = new Button("X");
		removeButton.addStyleDependentName("remove");
		removeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int removeIndex = stocks.indexOf(symbol);
				stocks.remove(removeIndex);
				stocksFlexTable.removeRow(removeIndex + 1);
			}
		});
		stocksFlexTable.setWidget(row, 3, removeButton);
	}
}
