package dei.uc.pt.aor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.sun.syndication.io.impl.Base64;

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
        String securedPassword = "";
        if (null == password) {
            return null;
        }
        
        MessageDigest digest;
        
        try {
//            digest = MessageDigest.getInstance("MD5");
//            digest.update(password.getBytes(), 0, password.length());
//            md5 = new BigInteger(1, digest.digest()).toString(16);
//            return md5;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			
			byte byteData[] = md.digest();
			byte[] data2 = Base64.encode(byteData);
			securedPassword = new String(data2);
			return securedPassword;
            
        } catch (NoSuchAlgorithmException e) {
        	String errorMsg = "Error while encrypting"+ "password: "+e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
        }        
    }

}
