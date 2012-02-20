package jan.signup.password.reset.client.s01form;

import jan.signup.common.client.Presenter;
import jan.signup.common.client.rpc.RegisterService;
import jan.signup.common.client.rpc.RegisterServiceAsync;
import jan.signup.password.reset.client.ResetEventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;

public class S01Presenter extends Presenter {

	private final S01Display display = new S01Display();
	private ResetEventBus eventbus;
	private final static RegisterServiceAsync REGISTER_SERVICE = GWT.create(RegisterService.class);
	
	public S01Presenter(ResetEventBus eventbus){
		this.eventbus = eventbus;
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
		REGISTER_SERVICE.resetPassword(display.getEmail(), new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				eventbus.onPasswordRequested(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}
		});
	}
}
