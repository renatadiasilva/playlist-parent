package dei.uc.pt.aor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;

@Stateless
public class EncryptPass {

    public String encrypt(String password) {
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
        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(EncryptPass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }

}
