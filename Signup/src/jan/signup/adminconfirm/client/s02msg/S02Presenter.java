package jan.signup.adminconfirm.client.s02msg;

import com.google.gwt.user.client.ui.IsWidget;

import jan.signup.common.client.Presenter;

public class S02Presenter extends Presenter {

	private final S02Display display = new S02Display();
	
	@Override
	public IsWidget getDisplay() {
		return display.asWidget();
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub

	}

}
