package idv.google.auth.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CredentialHandler {

	private final static Map<String, String> repositoryMap = new HashMap<String, String>();

	public String getSecretKey(String account) {
		return repositoryMap.get(account);
	}

	public boolean saveUserCredentials(String account, String secretKey) {
		repositoryMap.put(account, secretKey);
		return true;
	}

}