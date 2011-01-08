package org.gtugs.bh.client.ui;

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
			Integer from=CurrentSetup.get().getSetupData(CurrentSetup.KEY_RANGE_FROM);
			Integer to=CurrentSetup.get().getSetupData(CurrentSetup.KEY_RANGE_TO);
			listToRaffle1=InputUtil.transformRangeToSet(from, to);
			description.setText("Raffle numbers from "+from+" to "+to);
			break;
		}
		if (listToRaffle1==null) {
			error.setText("Invalid input. Cannot find a valid list of items to raffle");
			sendButton.setEnabled(false);
		}
			
	}
	
	protected String raffleFromSet(TreeSet<String> set) {
		String raffled=(String) set.toArray()[Random.nextInt(set.size())];
		set.remove(raffled);
		return raffled;
	}
	
	@UiHandler("sendButton")
	void onClick(ClickEvent e) {
		
		if (result.getWidget()!=null) {
			String text=((AnimatedLabel) result.getWidget()).getText();
			int pos=oldResults.getWidgetCount()+1;
			result.clear();
			oldResults.insert(new Label(pos+": "+text), 0);
		}
		if (listToRaffle1.isEmpty()) {
			sendButton.setEnabled(false);
			error.setText("no more names to raffle");
			return;
		} else if (CurrentSetup.get().getType()==RaffleMethodEnum.ITEM_TO_NAME && 
				listToRaffle2.isEmpty()) {
			sendButton.setEnabled(false);
			error.setText("no more items to raffle");
			return;
		}

		String raffled1=raffleFromSet(listToRaffle1);
		String raffled2=null;
		if (listToRaffle2!=null) {
			raffled2=raffleFromSet(listToRaffle2);
		}
		String text=raffled2==null?raffled1:(raffled1+"-"+raffled2);
		AnimatedLabel beingRaffled=new AnimatedLabel();
		beingRaffled.setTextLength(text.length());
		result.setWidget(beingRaffled);
		ShuffleAnimation anim=new ShuffleAnimation(text, beingRaffled);
		anim.run(5000);
	}

}
