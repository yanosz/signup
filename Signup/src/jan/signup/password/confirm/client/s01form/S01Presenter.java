package jan.signup.password.confirm.client.s01form;

import jan.signup.common.client.Presenter;
import jan.signup.common.client.rpc.RegisterService;
import jan.signup.common.client.rpc.RegisterServiceAsync;
import jan.signup.password.confirm.client.PasswordConfirmEventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class S01Presenter extends Presenter {

	
	private final PasswordConfirmEventBus eventbus;
	private final S01Display display;
	private final static RegisterServiceAsync REGISTER_SERVICE = GWT.create(RegisterService.class);
	
	public S01Presenter(PasswordConfirmEventBus eventBus) {
		this.eventbus = eventBus;
		this.display = new S01Display();
	}

	@Override
	public IsWidget getDisplay() {
		return display.asWidget();
	}

	@Override
	public void bind() {
		addChangeValidator(display.getPassword());
		addChangeValidator(display.getPasswordConfirm());
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onSubmitButtonClick();
			}

		});
		
	}
	private void onSubmitButtonClick() {
		String token = Window.Location.getParameter("token");
		REGISTER_SERVICE.confirmResetPassword(token, display.getPassword().getValue(), new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					eventbus.onPasswordChanged();
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	public void addChangeValidator(PasswordTextBox textBox){
		textBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				validatePasswords();
			}
		});
	}
	
	public void validatePasswords(){
		String s1 = display.getPassword().getValue();
		String s2 = display.getPasswordConfirm().getValue();
		if(s1 != null && ! pwdTooShort(s1) && pwdMatch(s1,s2)){
			display.getSubmitButton().setEnabled(true);
		}else{
			display.getSubmitButton().setEnabled(false);
		}
	}

	private boolean pwdMatch(String s1, String s2) {
		boolean noMatch = (s2 == null) || (!s1.equals(s2));
		display.setNoMatchVisible(noMatch);
		return !noMatch;
	}

	private boolean pwdTooShort(String s1) {
		boolean tooShort = s1.length() < 10;
		display.setPwdTooShortVisible(tooShort);
		return tooShort;
	}
	
	
}
