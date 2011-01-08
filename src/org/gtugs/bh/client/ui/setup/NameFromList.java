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

public class NameFromList extends Composite {

	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, NameFromList> {
	}

	@UiField TextArea names;
	@UiField Label error;

	public NameFromList() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		String[] arr1=CurrentSetup.get().getSetupData(CurrentSetup.KEY_NAME_SET);
		names.setText(InputUtil.transformArrayToString(arr1));
	}

	@UiHandler("names") void handleInput(KeyUpEvent event) {
		error.setText(null);
	}

	@UiHandler("go") void handleGo(ClickEvent event) {
		String[] nameArray=InputUtil.transformTextToArray(names.getText());
		if (nameArray.length==0) {
			error.setText("List of names must contain data. Please, check your input");
		} else {
			CurrentSetup.get().addSetupData(CurrentSetup.KEY_NAME_SET, nameArray);  
			History.newItem(HistoryHandler.TOKEN_RAFFLE);
		}
	}

}
