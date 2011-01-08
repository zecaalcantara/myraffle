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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class NumberRange extends Composite {

	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, NumberRange> {
	}
	
	@UiField TextBox from, to;
	@UiField Label error;
	
	public NumberRange() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		Integer f=CurrentSetup.get().getSetupData(CurrentSetup.KEY_RANGE_FROM);
		from.setText(InputUtil.transformIntegerToString(f));
		Integer t=CurrentSetup.get().getSetupData(CurrentSetup.KEY_RANGE_TO);
		to.setText(InputUtil.transformIntegerToString(t));
	}
	
	@UiHandler({"from", "to"}) void handleInput(KeyUpEvent event) {
		error.setText(null);
	}
	
	@UiHandler("go") void handleGo(ClickEvent event) {
		Integer fromInt=InputUtil.transformTextToInteger(from.getText());
		Integer toInt=InputUtil.transformTextToInteger(to.getText());
		if (fromInt==null || toInt==null || fromInt>=toInt) {
			error.setText("Invalid numbers, please check your input");
		} else {
			CurrentSetup.get().addSetupData(CurrentSetup.KEY_RANGE_FROM, fromInt);
			CurrentSetup.get().addSetupData(CurrentSetup.KEY_RANGE_TO, toInt);
			History.newItem(HistoryHandler.TOKEN_RAFFLE);
		}
	}

}
