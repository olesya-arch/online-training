package epam.jwd.online_training.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

    private static final Logger LOG = LogManager.getLogger(PasswordEncoder.class);
    private static final String ALGORITHM = "SHA1";
    private static final String ERROR_ENCODING_PASSWORD = "Cannot encode a password! ";

    public PasswordEncoder() {
    }

    public static String encodePassword (String password) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(ERROR_ENCODING_PASSWORD, e);
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String encodedPassword = bigInt.toString(16);
        while (encodedPassword.length() < 32) {
            encodedPassword = "0" + encodedPassword;
        }
        return encodedPassword;
    }
}
