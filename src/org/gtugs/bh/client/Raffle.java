package org.gtugs.bh.client;

import org.gtugs.bh.client.ui.resource.ResourceBundle;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Raffle implements EntryPoint {

	ResourceBundle bundle=GWT.create(ResourceBundle.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		bundle.css().ensureInjected();
		History.addValueChangeHandler(new HistoryHandler());
		History.fireCurrentHistoryState();
	}
	
}
