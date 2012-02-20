package jan.signup.password.reset.client.s02msg;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;

import jan.signup.common.client.Presenter;
import jan.signup.password.reset.client.ResetEventBus;

public class S02Presenter extends Presenter {

	private final S02Display display = new S02Display();
	private ResetEventBus eventbus;
	
	public S02Presenter(ResetEventBus eventbus){
		this.eventbus = eventbus;
	}
	
	@Override
	public IsWidget getDisplay() {
		return display.asWidget();
	}

	@Override
	public void bind() {
		display.getBackButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventbus.onBackToMain();
			}
		});

	}

	public void showResult(Boolean result) {
		if(result){
			display.showOkMessage();
		}else{
			display.showErrroMessage();
		}
	}

}
