package jan.signup.mailconfim.client.s01confirm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;

import jan.signup.common.client.Presenter;
import jan.signup.common.client.rpc.RegisterService;
import jan.signup.common.client.rpc.RegisterServiceAsync;

public class S01Presenter extends Presenter {

	private final static RegisterServiceAsync REGISTER_SERVICE = GWT.create(RegisterService.class);
	
	private final S01Display display = new S01Display();
	
	@Override
	public IsWidget getDisplay() {
		return display.asWidget();
	}

	@Override
	public void bind() {
		String token = Window.Location.getParameter("token");
		REGISTER_SERVICE.confirmEmail(token, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				onEmailConfirmed(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
		
	}
	private void onEmailConfirmed(Boolean result) {
		if(result){
			display.addOkMsg();
		}else{
			display.addErrorMsg();
		}
		
	}

}
