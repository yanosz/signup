package jan.signup.common.client.rpc;

import java.util.List;

import jan.signup.common.shared.CreateType;
import jan.signup.common.shared.UserAccountRequest;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RegisterServiceAsync {

	void register(UserAccountRequest parameterObject, AsyncCallback<Void> callback);

	void confirmEmail(String token, AsyncCallback<Boolean> callback);

	void confirmResetPassword(String token, String newPassword,
			AsyncCallback<Boolean> callback);

	void confirmUser(String token, UserAccountRequest request,
			CreateType createType, String parentDn, AsyncCallback<Void> callback);

	void resetPassword(String email, AsyncCallback<Boolean> callback);

	void loadUserAccountRequest(String token,
			AsyncCallback<UserAccountRequest> callback);

	void getValidParents(AsyncCallback<List<String>> callback);

	void getValidDomains(AsyncCallback<List<String>> callback);

	
	

}
