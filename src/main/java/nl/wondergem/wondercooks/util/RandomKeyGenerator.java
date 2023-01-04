package nl.wondergem.wondercooks.util;

import io.github.cdimascio.dotenv.Dotenv;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;



public class RandomKeyGenerator {

    public static Key getKeyFromKeyGenerator(int byteSize) {
        Dotenv dotenv = Dotenv.load();
        byte[] secureRandomKeyBytes = new byte[byteSize];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secureRandomKeyBytes);
        return new SecretKeySpec(secureRandomKeyBytes,dotenv.get("ENCYRPTION"));
        }

}
