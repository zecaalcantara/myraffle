package org.gtugs.bh.client;

public enum RaffleMethodEnum {
	ITEM_TO_NAME("itemToName"), NAME_FROM_LIST("nameFromList"), NUMBER_RANGE("numberRange");
	
	private String historyToken;
	
	private RaffleMethodEnum(String historyToken) {
		this.historyToken=historyToken;
	}
	
	public String getHistoryToken() {
		return historyToken;
	}
}
