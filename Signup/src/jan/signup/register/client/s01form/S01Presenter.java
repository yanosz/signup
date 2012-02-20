package jan.signup.register.client.s01form;

import jan.signup.common.client.Presenter;
import jan.signup.common.client.rpc.RegisterService;
import jan.signup.common.client.rpc.RegisterServiceAsync;
import jan.signup.common.shared.UserAccountRequest;
import jan.signup.register.client.RegisterEventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;

public class S01Presenter extends Presenter{

	private static final RegisterServiceAsync REGISTER_SERVICE = GWT.create(RegisterService.class);
	private final S01Display display;
	private final RegisterEventBus eventBus;
	
	
	public S01Presenter(RegisterEventBus eventBus){
		display = new S01Display();
		this.eventBus = eventBus;
	}
	
	@Override
	public IsWidget getDisplay() {
		return display.asWidget();
	}

	@Override
	public void bind() {
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onFormSubmit();
			}
		});
	}

	public void onFormSubmit(){
		display.getSubmitButton().setEnabled(false);
		String firstName = display.getFirstNameValue();
		String lastName = display.getLastNameValue();
		String email = display.getEmailValue();
		String unit = display.getUnitValue();
		UserAccountRequest accountRequest = new UserAccountRequest(firstName, lastName, email, unit);
		REGISTER_SERVICE.register(accountRequest, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				onFormSubmitSuccess();
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
	
	private void onFormSubmitSuccess() {
		eventBus.onRegisterSuccess();
	}

}
