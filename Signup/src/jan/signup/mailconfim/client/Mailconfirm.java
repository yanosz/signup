package jan.signup.mailconfim.client;

import jan.signup.mailconfim.client.s01confirm.S01Presenter;

import com.google.gwt.core.client.EntryPoint;

public class Mailconfirm implements EntryPoint {

	@Override
	public void onModuleLoad() {
		new S01Presenter().activate();
	}

}
