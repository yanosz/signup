package jan.signup.adminconfirm.client.s01decide;

import jan.signup.common.client.Display;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class S01Display implements Display {

	public final static String CREATE_ACCEPT = "ACCEPT";
	public final static String CREATE_REJECT = "REJECT";
	public final static String CREATE_ACCEPT_IMAP = "ACCEPT_IMAP";
	
	private final S01Messages msgs = GWT.create(S01Messages.class);
	
	private final DialogBox widget = new DialogBox();
	private final TextBox firstName = new TextBox();
	private final TextBox lastName = new TextBox();
	private final TextBox email = new TextBox();
	private final TextBox unit = new TextBox();
	private final TextBox emailNew = new TextBox();
	private final ListBox ldapParent = new ListBox();
	private final Button submitButton = new Button(msgs.submit());
	private ListBox createModeBox = new ListBox();
	private ListBox domainListBox = new ListBox();

	private HTML alreadyCreated = new HTML();
	
	
	public S01Display(){
		domainListBox.setWidth("9em");
		alreadyCreated.setVisible(false);
		alreadyCreated.setHTML("<span style='color: #F00;'>"+msgs.alreadyCreated() + "</span>");
		unit.setEnabled(false);
		email.setEnabled(false);
		widget.setText(msgs.title());
		FlexTable table = new FlexTable();
		submitButton.setEnabled(false);
		createModeBox.addItem(msgs.accept(), CREATE_ACCEPT);
		createModeBox.addItem(msgs.acceptImap(),CREATE_ACCEPT_IMAP );
		createModeBox.addItem(msgs.reject(), CREATE_REJECT);
		createModeBox.setVisibleItemCount(1);
		ldapParent.setVisibleItemCount(1);
		ldapParent.setWidth("100%");
		firstName.setWidth("95%");
		lastName.setWidth("95%");
		email.setWidth("95%");
		unit.setWidth("95%");
		createModeBox.setWidth("100%");

		
		
		//Already-Created Warning
		table.getFlexCellFormatter().setColSpan(0, 0, 2);
		table.setWidget(0, 0, alreadyCreated);
		
		//firstName
		table.setWidget(1, 0,new Label(msgs.firstName()));
		table.setWidget(1, 1, firstName);
		
		//lastName
		table.setWidget(2, 0, new Label(msgs.lastName()));
		table.setWidget(2, 1, lastName);
		
		//Unit
		table.setWidget(3, 0, new Label(msgs.unit()));
		table.setWidget(3, 1, unit);
		
		//E-Mail Address
		table.setWidget(4, 0, new Label(msgs.email()));
		table.setWidget(4, 1, email);
		

		//E-Mail (new)
		HorizontalPanel eMailPanel = new HorizontalPanel();
		eMailPanel.add(emailNew);
		eMailPanel.add(new HTML("@"));
		eMailPanel.add(domainListBox);
		table.setWidget(5, 0, new Label(msgs.emailNew()));
		table.setWidget(5, 1, eMailPanel);
		
		
		//Unit (ldap)
		table.setWidget(6, 0, new Label(msgs.ldapParent()));
		table.setWidget(6, 1, ldapParent);
		
		//Create Mode
		table.setWidget(7, 0, new Label(msgs.action()));
		table.setWidget(7, 1, createModeBox);
		
		
		//Submit-Button
		table.getFlexCellFormatter().setColSpan(8, 0, 2);
		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		buttonPanel.setWidth("100%");
		buttonPanel.add(submitButton);
		table.setWidget(8, 0, buttonPanel);
		table.setWidth("35em");
		table.getColumnFormatter().setWidth(0, "35%");
		widget.add(table);
		
	}
	
	public void setFirstName(final String firstNameValue) {
		firstName.setValue(firstNameValue);
		
	}

	public void setLastName(final String lastNameValue) {
		lastName.setValue(lastNameValue);
		
	}

	public void setEmail(final String emailValue) {
		email.setValue(emailValue);
		
	}

	public void setUnit(final String unitValue) {
		unit.setValue(unitValue);
		
	}
	public Button getSubmitButton(){
		return submitButton;
	}
	
	@Override
	public IsWidget asWidget() {
		return widget;
	}

	public String getFirstName() {
		return firstName.getValue();
	}

	public String getLastName() {
		return lastName.getValue();
	}

	public String getEmail() {
		return email.getValue();
	}

	public String getUnit() {
		return unit.getValue();
	}
	public String getCreateMode(){
		int selectedIndex = createModeBox.getSelectedIndex();
		return createModeBox.getValue(selectedIndex);
	}
	
	public String getLdapParent(){
		int index = ldapParent.getSelectedIndex();
		return ldapParent.getValue(index);
	}

	public void setLdapParents(List<String> parents){
		ldapParent.clear();
		if(parents != null && parents.size() > 0){
			for(String parent : parents){
				String [] elem = parent.split(",");
				int maxIndex = ( elem.length >= 2 ) ? 3  : elem.length -1 ;
				String res= "";
				for(int i = 0; i <= maxIndex; i++){
					res += elem[i];
					if(i < maxIndex){
						res += " | ";
					}
				}
				if(maxIndex >= elem.length){
					res += " | ...";
				}
				ldapParent.addItem(res,parent);
			}
		}
	}
	
	public void warnCreated(){
		alreadyCreated.setVisible(true);
		ldapParent.clear();
		firstName.setEnabled(false);
		lastName.setEnabled(false);
		emailNew.setEnabled(false);
		createModeBox.clear();
		submitButton.setEnabled(false);
	}
	
	public String getEmailNew(){
		return emailNew.getValue();
	}
	
	public void setEmailNew(String address){
		emailNew.setValue(address);
	}

	public void setValidDomains(List<String> domains){
		domainListBox.clear();
		if(domains != null && domains.size() > 0){
			for(String domain : domains){
				domainListBox.addItem(domain);
			}
		}
	}
	
	public void setDomain(String domain){
		domainListBox.clear();
		domainListBox.addItem(domain);
	}
	
	public String getDomain(){
		int selectedIndex = domainListBox.getSelectedIndex();
		return domainListBox.getValue(selectedIndex);
	}
}
