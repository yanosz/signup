package jan.signup.adminconfirm.client.s02msg;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

import jan.signup.common.client.Display;

public class S02Display implements Display {

	private final DialogBox widget = new DialogBox();
	private S02Messages msgs = GWT.create(S02Messages.class);
	
	public S02Display(){
		widget.setText(msgs.title());
		widget.add(new HTML(msgs.message()));
	}
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

}
