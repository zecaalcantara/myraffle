package org.gtugs.bh.client.ui.setup;

import org.gtugs.bh.client.CurrentSetup;
import org.gtugs.bh.client.HistoryHandler;
import org.gtugs.bh.client.InputUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ItemToName extends Composite {

	@UiField TextArea names, goods;
	@UiField Label error;

	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, ItemToName> {
	}

	public ItemToName() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		String[] arr1=CurrentSetup.get().getSetupData(CurrentSetup.KEY_NAME_SET);
		names.setText(InputUtil.transformArrayToString(arr1));
		String[] arr2=CurrentSetup.get().getSetupData(CurrentSetup.KEY_ITEMS_SET);
		goods.setText(InputUtil.transformArrayToString(arr2));
	}
	
	@UiHandler({"names", "goods"}) void handleInput(KeyUpEvent event) {
		error.setText(null);
	}
	

	@UiHandler("go") void handleGo(ClickEvent event) {
		String[] nameArray=InputUtil.transformTextToArray(names.getText());
		String[] itemArray=InputUtil.transformTextToArray(goods.getText());
		if (nameArray.length==0 || itemArray.length==0) {
			error.setText("Both lists must contain data. Please, check your input");
		} else {
			CurrentSetup.get().addSetupData(CurrentSetup.KEY_NAME_SET, nameArray); 
			CurrentSetup.get().addSetupData(CurrentSetup.KEY_ITEMS_SET, itemArray); 
			History.newItem(HistoryHandler.TOKEN_RAFFLE);
		}
	}

}
