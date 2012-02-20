package jan.signup.common.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class Presenter {
	
	public void activate(){
		bind();
		RootPanel.get("app").clear();
		RootPanel.get("app").add(getDisplay());
	}
	
	public abstract IsWidget getDisplay();

	public abstract void bind();
	
}
