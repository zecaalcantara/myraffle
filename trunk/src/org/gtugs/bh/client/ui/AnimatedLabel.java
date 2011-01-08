package org.gtugs.bh.client.ui;

import org.gtugs.bh.client.ui.resource.ResourceBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AnimatedLabel extends Composite implements HasText {

	private static MyUiBinder uiBinder = GWT
			.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, AnimatedLabel> {
	}
	
	@UiField FlowPanel containerTop, containerBottom;

	private String text;
	
	public AnimatedLabel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}
	
	public void setTextLength(int length) {
		for (int i=0; i<Math.min(17, length); i++) {
			Label top=new Label();
			top.setStyleName(ResourceBundle.instance.css().letterbox());
			containerTop.add(top);
			Label bottom=new Label();
			bottom.setStyleName(ResourceBundle.instance.css().letterbox());
			bottom.addStyleName(ResourceBundle.instance.css().letterboxbottom());
			containerBottom.add(bottom);
		}
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public void setText(String text) {
		this.text=text;
		int chars=containerTop.getWidgetCount();
		for (int i=0; i<chars; i++) {
			String c=String.valueOf(i<text.length()?text.charAt(i):" ");
			((Label) containerTop.getWidget(i)).setText(c);
			((Label) containerBottom.getWidget(i)).setText(c);
		}
	}
	
}
