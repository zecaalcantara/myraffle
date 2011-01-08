package org.gtugs.bh.client;

import java.util.HashMap;
import java.util.Map;


public class CurrentSetup {

	public static final String KEY_NAME_SET="names";
	public static final String KEY_ITEMS_SET="items";
	public static final String KEY_RANGE_FROM="from";
	public static final String KEY_RANGE_TO="to";

	private static CurrentSetup instance=new CurrentSetup();
	
	private RaffleMethodEnum type;
	
	private Map<String, Object> data;
	
	private CurrentSetup() {
		data=new HashMap<String, Object>();
	}
	
	public static CurrentSetup get() {
		return instance;
	}

	public RaffleMethodEnum getType() {
		return type;
	}
	
	public void setType(String type) {
		for (RaffleMethodEnum m: RaffleMethodEnum.values()) {
			if (m.getHistoryToken().equals(type)) {
			  this.type=m;
			  return;
			}
		}
		this.type=null;
	}
	
	public void setType(RaffleMethodEnum type) {
		this.type = type;
	}

	public <T> void addSetupData(String key, T obj) {
		data.put(key, obj);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSetupData(String key) {
		return (T) data.get(key);
	}
	

}
