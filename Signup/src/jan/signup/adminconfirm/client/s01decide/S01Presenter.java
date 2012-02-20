package jan.signup.adminconfirm.client.s01decide;

import jan.signup.adminconfirm.client.AdminconfirmEventBus;
import jan.signup.common.client.Presenter;
import jan.signup.common.client.rpc.RegisterService;
import jan.signup.common.client.rpc.RegisterServiceAsync;
import jan.signup.common.shared.CreateType;
import jan.signup.common.shared.UserAccountRequest;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;

public class S01Presenter extends Presenter {

	private final S01Display display = new S01Display();
	private final static RegisterServiceAsync REGISTER_SERVICE = GWT.create(RegisterService.class);
	private String token;
	private AdminconfirmEventBus eventbus;
	
	public S01Presenter(AdminconfirmEventBus eventBus){
		this.eventbus = eventBus;
	}
	
	@Override
	public IsWidget getDisplay() {
		return display.asWidget();
	}

	@Override
	public void bind() {
		display.getSubmitButton().setEnabled(false);
		token = Window.Location.getParameter("token");
		REGISTER_SERVICE.getValidParents(new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(List<String> result) {
				display.setLdapParents(result);
			}
			
		});
		REGISTER_SERVICE.getValidDomains(new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(List<String> result) {
				display.setValidDomains(result);
			}
		});
		
		REGISTER_SERVICE.loadUserAccountRequest(token, new AsyncCallback<UserAccountRequest>() {
			@Override
			public void onSuccess(UserAccountRequest result) {
				onUserAccountRequestLoad(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
		
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onSubmitButtonClicked();
			}
		});
	}
	
	public void onUserAccountRequestLoad(UserAccountRequest request){
		if(request == null){
			Window.alert("Token error!");
			return;
		}
		display.setFirstName(request.getFirstName());
		display.setLastName(request.getLastName());
		display.setEmail(request.getEmail());
		display.setUnit(request.getUnit());
		display.getSubmitButton().setEnabled(true);
		display.setEmailNew(request.getEmailNew());
		if(request.isAlreadyCreated()){
			display.setDomain(request.getDomain());
			display.setLdapParents(Arrays.asList(request.getLdapParent()));
			display.warnCreated();
		}
	
	}
	
	public void onSubmitButtonClicked(){
		display.getSubmitButton().setEnabled(false);
		String parentDn = display.getLdapParent();
		String firstName = display.getFirstName();
		String email = display.getEmail();
		String unit = display.getUnit();
		String ldapParent = display.getLdapParent();
		String lastName = display.getLastName();
		boolean alreadyCreated = false;
		String emailNew = display.getEmailNew();
		String domain =  display.getDomain();
		UserAccountRequest request = new UserAccountRequest(firstName, lastName, email, unit, alreadyCreated, ldapParent, emailNew,domain);

		String modeValue = display.getCreateMode();
		CreateType ctype = CreateType.REJECT;
		if(modeValue.equals(S01Display.CREATE_ACCEPT)){
			ctype = CreateType.ACCEPT;
		}else if(modeValue.equals(S01Display.CREATE_ACCEPT_IMAP)){
			ctype = CreateType.ACCEPT_IMAP;
		}
		
		REGISTER_SERVICE.confirmUser(token, request, ctype, parentDn,new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				eventbus.onAccountCreated();
			}
		});
	}
}
