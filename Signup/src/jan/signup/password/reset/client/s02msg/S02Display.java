package jan.signup.password.reset.client.s02msg;

import jan.signup.common.client.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;

public class S02Display implements Display {

	private final DialogBox widget = new DialogBox();
	private final S02Messages msgs = GWT.create(S02Messages.class);
	private final HTML msg = new HTML(msgs.msg());
	private final HTML errorMsg = new HTML(msgs.errroMsg());
	private final Button backButton = new Button(msgs.back());
	
	public S02Display(){
		widget.setHTML(msgs.title());
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vp.add(msg);
		vp.add(errorMsg);
		vp.add(backButton);
		widget.add(vp);
	}
	
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

	public void showOkMessage() {
		errorMsg.setVisible(false);
		backButton.setVisible(false);
		msg.setVisible(true);
	}


	public void showErrroMessage() {
		errorMsg.setVisible(true);
		backButton.setVisible(true);
		msg.setVisible(false);
	}


	public Button getBackButton() {
		return backButton;
		
	}
	
}
