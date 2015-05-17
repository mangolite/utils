package org.spamjs.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO: Auto-generated Javadoc
/**
 * The Class HashUtil.
 */
public final class HashUtil {

	/** The Constant PAD_ZERO. */
	private static final String PAD_ZERO = "0";
	
	/** The Constant MD5. */
	private static final String MD5 = "MD5";
	
	/** The Constant SHA1. */
	private static final String SHA1 = "SHA1";
	
	/** The Constant SHA2. */
	private static final String SHA2 = "SHA-256";

	/**
	 * Keep constructor private so that instantiation is not allowed.
	 */
	private HashUtil() {
		throw new IllegalStateException("This is a class for constants and should not be instantiated");
	}

	// private static final Log LOG = new Log();

	/**
	 * Gets the m d5 hash.
	 *
	 * @param str the str
	 * @return md5 hashed string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getMD5Hash(String str) throws NoSuchAlgorithmException {
		return getMD5Hash(str.getBytes());
	}

	/**
	 * Gets the m d5 hash.
	 *
	 * @param byteArray the byte array
	 * @return md5 hashed string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getMD5Hash(byte[] byteArray) throws NoSuchAlgorithmException {
		return getHashedStrFor(byteArray, MD5);
	}

	/**
	 * Gets the SH a1 hash.
	 *
	 * @param str the str
	 * @return sha1 hashed string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getSHA1Hash(String str) throws NoSuchAlgorithmException {
		return getSHA1Hash(str.getBytes());
	}

	/**
	 * Gets the SH a1 hash.
	 *
	 * @param byteArray the byte array
	 * @return sha1 hashed string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getSHA1Hash(byte[] byteArray) throws NoSuchAlgorithmException {
		return getHashedStrFor(byteArray, SHA1);
	}

	/**
	 * Gets the SH a2 hash.
	 *
	 * @param str the str
	 * @return sha2 hashed string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getSHA2Hash(String str) throws NoSuchAlgorithmException {
		return getSHA1Hash(str.getBytes());
	}

	/**
	 * Gets the SH a2 hash.
	 *
	 * @param byteArray the byte array
	 * @return sha2 hashed string
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public static String getSHA2Hash(byte[] byteArray) throws NoSuchAlgorithmException {
		return getHashedStrFor(byteArray, SHA2);
	}

	/**
	 * Gets the hashed str for.
	 *
	 * @param byteArray the byte array
	 * @param algorithm the algorithm
	 * @return the hashed str for
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	private static String getHashedStrFor(byte[] byteArray, String algorithm) throws NoSuchAlgorithmException {

		MessageDigest msgDigest = MessageDigest.getInstance(algorithm);
		msgDigest.reset();
		msgDigest.update(byteArray);
		byte[] digest = msgDigest.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		// Pad it to get full 32 chars.
		while (hashtext.length() < 32) {
			hashtext = PAD_ZERO + hashtext;
		}

		return hashtext;

	}

}
