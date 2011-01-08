package org.gtugs.bh.client.ui.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ResourceBundle extends ClientBundle {

	public ResourceBundle instance=GWT.create(ResourceBundle.class);
	
	@Source({"reset.css", "main.css"})
	Css css();

	ImageResource arrow1();
	ImageResource arrow2();
	ImageResource arrow3();
}
