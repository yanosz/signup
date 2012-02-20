package jan.signup.register.client.s01form;

import jan.signup.common.client.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class S01Display implements Display {

	private final DialogBox widget;
	private final S01Messages msgs = GWT.create(S01Messages.class);
	private TextBox firstname,lastname,email,unit;
	private Button submitButton;
	private Label firstNameLabel,lastNameLabel,emailLabel,unitLabel;

	
	
	public S01Display(){
		widget = new DialogBox();
		firstname = new TextBox();
		lastname = new TextBox();
		email = new TextBox();
		unit = new TextBox();
		submitButton = new Button(msgs.submit());
		firstNameLabel = new Label(msgs.firstName());
		lastNameLabel = new Label(msgs.lastName());
		emailLabel = new Label(msgs.email());
		unitLabel = new Label(msgs.unit());
		initWidget();
	}
	
	private void initWidget() {
		widget.setText(msgs.title());
		widget.setAnimationEnabled(true);
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(firstNameLabel);vPanel.add(firstname);
		vPanel.add(lastNameLabel);vPanel.add(lastname);
		vPanel.add(emailLabel);vPanel.add(email);
		vPanel.add(unitLabel);vPanel.add(unit);
		firstname.setWidth("95%");
		lastname.setWidth("95%");
		email.setWidth("95%");
		unit.setWidth("95%");
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(submitButton);
		widget.add(vPanel);
	}

	/* (non-Javadoc)
	 * @see jan.signup.register.client.s01form.Display#asWidget()
	 */
	@Override
	public IsWidget asWidget(){
		return widget;
	}
	
	public Button getSubmitButton(){
		return submitButton;
	}
	public String getFirstNameValue(){
		return firstname.getValue();
	}
	public String getLastNameValue(){
		return lastname.getValue();
	}
	public String getEmailValue(){
		return email.getValue();
	}
	public String getUnitValue(){
		return unit.getValue();
	}
}
