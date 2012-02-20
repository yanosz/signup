package jan.signup.register.client;

import jan.signup.register.client.s01form.S01Presenter;
import jan.signup.register.client.s02confirm.S02Presenter;

import com.google.gwt.core.client.EntryPoint;

public class Register implements EntryPoint, RegisterEventBus{

	@Override
	public void onModuleLoad() {
		onShowForm();
	}

	/* (non-Javadoc)
	 * @see jan.signup.register.client.RegisterEventBus#onShowForm()
	 */
	@Override
	public void onShowForm() {
		new S01Presenter(this).activate();

	}

	
	/* (non-Javadoc)
	 * @see jan.signup.register.client.RegisterEventBus#onShowConfirm()
	 */
	@Override
	public void onShowConfirm(){
		new S02Presenter().activate();
	}

	@Override
	public void onRegisterSuccess() {
		onShowConfirm();
		
	}
}
