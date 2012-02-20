package jan.signup.password.confirm.client;


import jan.signup.password.confirm.client.s01form.S01Presenter;
import jan.signup.password.confirm.client.s02confirm.S02Presenter;


import com.google.gwt.core.client.EntryPoint;

public class PasswordConfirm implements EntryPoint, PasswordConfirmEventBus {

	private final S01Presenter s01Presenter;
	private final S02Presenter s02Presenter;
	
	
	public PasswordConfirm(){
		s01Presenter = new S01Presenter(this);
		s02Presenter = new S02Presenter();
	}
	
	@Override
	public void onModuleLoad() {
		s01Presenter.activate();

	}

	@Override
	public void onPasswordChanged(){
		s02Presenter.activate();
	}
	
}
