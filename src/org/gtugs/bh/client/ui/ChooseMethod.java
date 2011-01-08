package org.gtugs.bh.client.ui;

import org.gtugs.bh.client.CurrentSetup;
import org.gtugs.bh.client.RaffleMethodEnum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class ChooseMethod extends Composite {

	@UiField Hyperlink link1, link2, link3;
	
	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, ChooseMethod> {
	}

	public ChooseMethod() {
		initWidget(uiBinder.createAndBindUi(this));
		RaffleMethodEnum type=CurrentSetup.get().getType();
		if (type!=null) {
			link1.setVisible(type!=RaffleMethodEnum.NAME_FROM_LIST);
			link2.setVisible(type!=RaffleMethodEnum.NUMBER_RANGE);
			link3.setVisible(type!=RaffleMethodEnum.ITEM_TO_NAME);
		}
	}

}
