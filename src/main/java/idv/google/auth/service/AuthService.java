package idv.google.auth.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.warrenstrange.googleauth.ICredentialRepository;

import idv.google.auth.handler.CredentialHandler;

@Service
public class AuthService {

	private static final String issuer = "KHW.Corp";
	private static final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
	private static final GoogleAuthenticatorQRGenerator qrGenerator = new GoogleAuthenticatorQRGenerator();

	@Autowired
    private CredentialHandler handler;

	@PostConstruct
	public void init() {

		googleAuthenticator.setCredentialRepository(new ICredentialRepository() {
			@Override
			public String getSecretKey(String account) {
				return handler.getSecretKey(account);
			}

			@Override
			public void saveUserCredentials(String account, String secretKey, int validationCode, List<Integer> scratchCodes) {
				handler.saveUserCredentials(account, secretKey);
			}
		});
	}

	@SuppressWarnings("static-access")
	public String getQrcode(String account) {
		GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(account);
		return qrGenerator.getOtpAuthURL(issuer, account, key);
	}

	public boolean validCode(String account, int code) {
		return googleAuthenticator.authorizeUser(account, code);
	}

}