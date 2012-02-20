package jan.signup.password.reset.client.s01form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import jan.signup.common.client.Display;

public class S01Display implements Display {

	private final DialogBox widget = new DialogBox();
	
	private final S01Messages msgs = GWT.create(S01Messages.class);
	private final Button submitButton = new Button(msgs.submit());
	private final TextBox emailInput = new TextBox();
	
	public S01Display(){
		widget.setHTML(msgs.title());
		VerticalPanel vp = new VerticalPanel();
		vp.add(new HTML(msgs.message()));
		vp.add(new Label(msgs.email()));
		emailInput.setWidth(("97%"));
		vp.add(emailInput);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vp.add(submitButton);
		widget.add(vp);
	}
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

	public Button getSubmitButton(){
		return submitButton;
	}
	
	public String getEmail(){
		return emailInput.getValue();
	}
}
