package jan.signup.common.client.rpc;

import java.util.List;

import jan.signup.common.shared.CreateType;
import jan.signup.common.shared.UserAccountRequest;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../register.do")
public interface RegisterService extends RemoteService{
	public void register(UserAccountRequest parameterObject);
	public boolean confirmEmail(String token);
	void confirmUser(String token, UserAccountRequest request,
			CreateType createType,String parentDn);
	public UserAccountRequest loadUserAccountRequest(String token);
	boolean resetPassword(String email);
	public boolean confirmResetPassword(String token,String newPassword);
	public List<String> getValidParents();
	public List<String> getValidDomains();
}
