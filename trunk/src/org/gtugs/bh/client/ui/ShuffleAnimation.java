package org.gtugs.bh.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HasText;

public class ShuffleAnimation extends Animation {
	private HasText widget;
	private String finalString;
	private boolean onlyNumbers;
	
	public ShuffleAnimation(String finalString, HasText widget) {
		this.widget=widget;
		this.finalString=finalString;
		this.onlyNumbers=finalString.matches("\\d*");
	}
	@Override
	protected void onUpdate(double progress) {
		StringBuilder s=new StringBuilder();
		int startRandom=(int) (finalString.length()*progress);
		int i=0;
		for (; i<startRandom; i++) {
			s.append(finalString.charAt(i));
		}
		for (; i<finalString.length(); i++) {
			if (onlyNumbers) {
				s.append((char) ('0'+Random.nextInt(10)));
			} else {
				s.append((char) ('a'+Random.nextInt(23)));
			}
		}
		widget.setText(s.toString());
	}
	@Override
	protected void onComplete() {
		super.onComplete();
		widget.setText(finalString);
	}
}
