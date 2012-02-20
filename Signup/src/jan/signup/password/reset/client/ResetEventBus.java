package jan.signup.password.reset.client;

public interface ResetEventBus {

	public void onPasswordRequested(Boolean result);
	public void onBackToMain();
}