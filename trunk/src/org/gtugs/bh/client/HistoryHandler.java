package org.gtugs.bh.client;

import org.gtugs.bh.client.ui.ChooseMethod;
import org.gtugs.bh.client.ui.DoRaffle;
import org.gtugs.bh.client.ui.setup.ItemToName;
import org.gtugs.bh.client.ui.setup.NameFromList;
import org.gtugs.bh.client.ui.setup.NumberRange;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RootPanel;

/**
 */
public class HistoryHandler implements ValueChangeHandler<String> {

	public static final String SUBTOKEN_SETUP="/setup";
	
	public static final String TOKEN_START="start";
	public static final String TOKEN_ITEM_TO_NAME_SETUP=RaffleMethodEnum.ITEM_TO_NAME.getHistoryToken()+SUBTOKEN_SETUP;
	public static final String TOKEN_NAME_FROM_LIST_SETUP=RaffleMethodEnum.NAME_FROM_LIST.getHistoryToken()+SUBTOKEN_SETUP;
	public static final String TOKEN_NUMBER_RANGE_SETUP=RaffleMethodEnum.NUMBER_RANGE.getHistoryToken()+SUBTOKEN_SETUP;
	public static final String TOKEN_RAFFLE="raffle";

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token=TOKEN_START;
		if (event.getValue()!=null && !event.getValue().isEmpty()) {
			token=event.getValue();
		}
		RootPanel container=RootPanel.get("container");
		container.clear();
		
		boolean setup=token.endsWith(SUBTOKEN_SETUP);
		if (setup) {
			CurrentSetup.get().setType(token.substring(0, token.length()-SUBTOKEN_SETUP.length()));
			RaffleMethodEnum type=CurrentSetup.get().getType();
			container.add(new ChooseMethod());
			if (RaffleMethodEnum.ITEM_TO_NAME==type) {
				container.add(new ItemToName());
			} else if (RaffleMethodEnum.NAME_FROM_LIST==type) {
				container.add(new NameFromList());
			} else if (RaffleMethodEnum.NUMBER_RANGE==type) {
				container.add(new NumberRange());
			}
		} else if (token.equals(TOKEN_RAFFLE) && CurrentSetup.get().getType()!=null) {
			container.add(new DoRaffle());
		} else {
			CurrentSetup.get().setType((RaffleMethodEnum) null);
			container.add(new ChooseMethod());
		}
	}

}
