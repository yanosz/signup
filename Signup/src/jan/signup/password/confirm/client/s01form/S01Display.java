package jan.signup.password.confirm.client.s01form;

import jan.signup.common.client.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class S01Display implements Display {

	private final DialogBox widget = new DialogBox();

	S01Messages msgs = GWT.create(S01Messages.class);
	
	
	private final PasswordTextBox password = new PasswordTextBox();
	private final PasswordTextBox passwordConfirm = new PasswordTextBox();
	private final Button submitButton = new Button(msgs.submit());
	private final Label tooShort = new Label(msgs.tooShort());
	private Label noMatch = new Label(msgs.noMatch());
	
	public S01Display(){
		
		submitButton.setEnabled(false);
		password.setWidth("96%");
		passwordConfirm.setWidth("96%");
		VerticalPanel vp = new VerticalPanel();
		widget.setHTML(msgs.title());
		vp.add(new HTML(msgs.infoMsg()));
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		tooShort.setStylePrimaryName("errorMsg");
		noMatch.setStylePrimaryName("errorMsg");
		vp.add(tooShort);
		vp.add(noMatch);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
		vp.add(new Label(msgs.passwd()));
		vp.add(password);
		vp.add(new Label(msgs.passwdConfirm()));
		vp.add(passwordConfirm);
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vp.add(submitButton);
		tooShort.setVisible(false);
		noMatch.setVisible(false);
		widget.add(vp);
	}
	
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}


	public PasswordTextBox getPassword() {
		return password;
	}


	public PasswordTextBox getPasswordConfirm() {
		return passwordConfirm;
	}

	public Button getSubmitButton(){
		return submitButton;
	}


	public void setPwdTooShortVisible(boolean visible) {
		tooShort.setVisible(visible);
	}
	public void setNoMatchVisible(boolean visible){
		noMatch.setVisible(visible);
	}
}
