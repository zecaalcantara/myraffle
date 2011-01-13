package org.gtugs.bh.client.ui;

import java.util.HashSet;
import java.util.TreeSet;

import org.gtugs.bh.client.CurrentSetup;
import org.gtugs.bh.client.HistoryHandler;
import org.gtugs.bh.client.InputUtil;
import org.gtugs.bh.client.RaffleMethodEnum;
import org.gtugs.bh.client.ui.resource.ResourceBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DoRaffle extends Composite {

	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DoRaffle> {
	}
	
	@UiField SimplePanel result;
	@UiField FlowPanel oldResults;
	@UiField Label description, error;
	@UiField Button sendButton;
	
	private TreeSet<String> listToRaffle1, listToRaffle2;

	private Integer from, to;
	private HashSet<Integer> numbersRaffledFromRange;
	
	public DoRaffle() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		listToRaffle1=null;
		listToRaffle2=null;
		RaffleMethodEnum type=CurrentSetup.get().getType();
		if (type==null) {
			History.newItem(HistoryHandler.TOKEN_START);
			return;
		}
		switch (type) {
		case ITEM_TO_NAME:
			addStyleName(ResourceBundle.instance.css().b3());
			String[] array1=CurrentSetup.get().getSetupData(CurrentSetup.KEY_NAME_SET);
			listToRaffle1=InputUtil.transformArrayToSet(array1);
			String[] array2=CurrentSetup.get().getSetupData(CurrentSetup.KEY_ITEMS_SET);
			listToRaffle2=InputUtil.transformArrayToSet(array2);
			description.setText("Raffle lucky people from "+array1.length+" names to get "+array2.length+" items");
			break;
		case NAME_FROM_LIST:
			addStyleName(ResourceBundle.instance.css().b1());
			String[] array=CurrentSetup.get().getSetupData(CurrentSetup.KEY_NAME_SET);
			listToRaffle1=InputUtil.transformArrayToSet(array);
			description.setText("Raffle lucky people from "+array.length+" names");
			break;
		case NUMBER_RANGE:
			addStyleName(ResourceBundle.instance.css().b2());
			from=CurrentSetup.get().getSetupData(CurrentSetup.KEY_RANGE_FROM);
			to=CurrentSetup.get().getSetupData(CurrentSetup.KEY_RANGE_TO);
			description.setText("Raffle numbers from "+from+" to "+to);
			break;
		}
		if (listToRaffle1==null && (from==null || to==null) ) {
			error.setText("Invalid input. Cannot find a valid list of items to raffle");
			sendButton.setEnabled(false);
		}
	}
	
	protected String raffleFromSet(TreeSet<String> set) {
		if (set.isEmpty()) {
			return null;
		}
		String raffled=(String) set.toArray()[Random.nextInt(set.size())];
		set.remove(raffled);
		return raffled;
	}
	
	protected String raffleFromRange(Integer from, Integer to) {
		if (numbersRaffledFromRange==null) {
			numbersRaffledFromRange=new HashSet<Integer>();
		}
		// try to raffle 20 times until a number that have not appeared before gets raffled.
		// If it wasn't possible to find such a number after 20 tries, let's search
		// sequentially the first valid number after that 20th try. This is not exactly 
		// random, but it is better than keep trying undefinitely and hang the UI
		int tries=0;
		Integer raffled;
		do {
			raffled=Random.nextInt(to-from+1)+from;
			tries++;
		} while (numbersRaffledFromRange.contains(raffled) && tries<20);
		if (numbersRaffledFromRange.contains(raffled)) {
			Integer firstValidAfterRaffled=null;
			for (int i=raffled+1; i<=to && firstValidAfterRaffled==null; i++) {
				if (!numbersRaffledFromRange.contains(i)) {
					firstValidAfterRaffled=i;
				}
			}
			for (int i=from; i<raffled && firstValidAfterRaffled==null; i++) {
				if (!numbersRaffledFromRange.contains(i)) {
					firstValidAfterRaffled=i;
				}
			}
			raffled=firstValidAfterRaffled;
		}
		if (raffled==null) {
			return null;
		}
		numbersRaffledFromRange.add(raffled);
		return String.valueOf(raffled);
	}
	
	@UiHandler("sendButton")
	void onClick(ClickEvent e) {
		
		if (result.getWidget()!=null) {
			String text=((AnimatedLabel) result.getWidget()).getText();
			int pos=oldResults.getWidgetCount()+1;
			result.clear();
			oldResults.insert(new Label(pos+": "+text), 0);
		}
		String raffled1=null;
		String raffled2=null;
		String text=null;
		RaffleMethodEnum type=CurrentSetup.get().getType();
		
		switch (type) {
		case NUMBER_RANGE:
			raffled1=raffleFromRange(from, to);
			text=raffled1;
			break;
		case NAME_FROM_LIST:
			raffled1=raffleFromSet(listToRaffle1);
			text=raffled1;
			break;
		case ITEM_TO_NAME:
			raffled1=raffleFromSet(listToRaffle1);
			raffled2=raffleFromSet(listToRaffle2);
			text=raffled1+"-"+raffled2;
			break;
		}
		if (raffled1==null) {
			sendButton.setEnabled(false);
			error.setText("no more names to raffle");
			return;
		} else if (type==RaffleMethodEnum.ITEM_TO_NAME && raffled2==null) {
			sendButton.setEnabled(false);
			error.setText("no more items to raffle");
			return;
		}

		AnimatedLabel beingRaffled=new AnimatedLabel();
		beingRaffled.setTextLength(text.length());
		result.setWidget(beingRaffled);
		ShuffleAnimation anim=new ShuffleAnimation(text, beingRaffled);
		anim.run(5000);
	}

}
