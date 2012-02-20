package jan.signup.password.confirm.client.s02confirm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

import jan.signup.common.client.Display;

public class S02Display implements Display {

	private final DialogBox widget = new DialogBox();
	private final S02Messages msgs = GWT.create(S02Messages.class);
	
	public S02Display(){
		widget.setHTML(msgs.title());
		widget.add(new Label(msgs.info()));
	}
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

}
