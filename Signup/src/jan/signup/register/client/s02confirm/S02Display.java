package jan.signup.register.client.s02confirm;

import jan.signup.common.client.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

public class S02Display implements Display{

	private final DialogBox widget = new DialogBox();
	private final S02Messages msgs = GWT.create(S02Messages.class);
	
	public S02Display(){
		widget.setText(msgs.title());
		HTML html = new HTML(msgs.message());
		widget.add(html);
	}
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

	
}
