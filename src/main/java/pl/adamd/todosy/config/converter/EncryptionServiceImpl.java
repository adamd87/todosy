package pl.adamd.todosy.config.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class EncryptionServiceImpl implements EncryptionService{

    @Value("${prop.key}")
    private String key;
    @Value("${prop.initVector}")
    private String initVector;
    @Value("${prop.algorithm}")
    private String algo;

    private static final String AES = "AES";

    public String encrypt(String value){
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);

            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, spec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder()
                         .encodeToString(encrypted);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encrypted){
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);

            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE,spec, ivParameterSpec);

            byte[] original = cipher.doFinal(Base64.getDecoder()
                                                   .decode(encrypted));
            return new String(original);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
