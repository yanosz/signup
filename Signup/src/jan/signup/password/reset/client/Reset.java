package jan.signup.password.reset.client;

import jan.signup.password.reset.client.s01form.S01Presenter;
import jan.signup.password.reset.client.s02msg.S02Presenter;

import com.google.gwt.core.client.EntryPoint;

public class Reset implements EntryPoint, ResetEventBus {

	private final S01Presenter s01Presenter;
	private final S02Presenter s02Presenter;
	
	public Reset(){
		s01Presenter = new S01Presenter(this);
		s02Presenter = new S02Presenter(this);
	}
	@Override
	public void onModuleLoad() {
		s01Presenter.activate();
	}

	@Override
	public void onPasswordRequested(Boolean result){
		s02Presenter.activate();
		s02Presenter.showResult(result);
	}
	@Override
	public void onBackToMain() {
		s01Presenter.activate();
	}
	
}
