package jan.signup.adminconfirm.client;

import jan.signup.adminconfirm.client.s01decide.S01Presenter;
import jan.signup.adminconfirm.client.s02msg.S02Presenter;

import com.google.gwt.core.client.EntryPoint;

public class Adminconfirm implements EntryPoint, AdminconfirmEventBus {
	@Override
	public void onModuleLoad() {
		new S01Presenter(this).activate();
	}
	
	@Override
	public void onAccountCreated(){
		onShowSuccessMessage();
	}

	@Override
	public void onShowSuccessMessage() {
		new S02Presenter().activate();
		
	}
	
}
