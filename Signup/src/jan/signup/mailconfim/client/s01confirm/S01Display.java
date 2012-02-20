package jan.signup.mailconfim.client.s01confirm;

import jan.signup.common.client.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

public class S01Display implements Display {

	private final DialogBox widget = new DialogBox();
	private final S01Messages msgs = GWT.create(S01Messages.class);
	
	public S01Display(){
		widget.setText(msgs.title());
	}
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

	public void addErrorMsg(){
		HTML html = new HTML(msgs.errorMessage());
		widget.add(html);
	}

	public void addOkMsg(){
		HTML html = new HTML(msgs.message());
		widget.add(html);
		
	}
}
