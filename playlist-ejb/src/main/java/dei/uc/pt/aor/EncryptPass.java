package dei.uc.pt.aor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EncryptPass {
	
	private static final Logger log = LoggerFactory.getLogger(EncryptPass.class);

    public String encrypt(String password) {
    	log.info("Encrypting password");
        String md5;
        if (null == password) {
            return null;
        }
        
        MessageDigest digest;
        
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
            return md5;
        } catch (NoSuchAlgorithmException e) {
        	String errorMsg = "Error while encrypting"+ "password: "+e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
        }        
    }

}
