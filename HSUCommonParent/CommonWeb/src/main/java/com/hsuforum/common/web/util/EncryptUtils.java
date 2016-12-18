package com.hsuforum.common.web.util;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Base64;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;
/**
 * Encrypt and decrypt utility
 * @author Marvin
 *
 */
public class EncryptUtils {
	/**
	 * decrypt AES input base64 string
	 * @param base64Text
	 * @return
	 * @throws EncryptionException
	 */
	public static String decrypt(String base64Text) throws EncryptionException {
		CipherText cipherText = CipherText.fromPortableSerializedBytes(Base64.decode(base64Text));

		PlainText recoveredPlaintext = ESAPI.encryptor().decrypt(cipherText);

		return recoveredPlaintext.toString();
	}
	
	/**
	 * encrypt AES return base64 string
	 * @param plaintext
	 * @return
	 * @throws EncryptionException
	 */
	public static String encrypt(String plainText) throws EncryptionException {
		CipherText ciphertext = ESAPI.encryptor().encrypt( new PlainText(plainText) );
        String base64=Base64.encodeBytes(ciphertext.asPortableSerializedByteArray());
        return base64;
	}
}
