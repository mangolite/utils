package com.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtil {

	private static final String PAD_ZERO = "0";
	private static final String MD5 = "MD5";
	private static final String SHA1 = "SHA1";
	private static final String SHA2 = "SHA-256";

	/**
	 * Keep constructor private so that instantiation is not allowed.
	 */
	private HashUtil() {
		throw new IllegalStateException("This is a class for constants and should not be instantiated");
	}

	// private static final Log LOG = new Log();

	/**
	 * @param str
	 * @return md5 hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Hash(String str) throws NoSuchAlgorithmException {
		return getMD5Hash(str.getBytes());
	}

	/**
	 * @param byteArray
	 * @return md5 hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5Hash(byte[] byteArray) throws NoSuchAlgorithmException {
		return getHashedStrFor(byteArray, MD5);
	}

	/**
	 * @param str
	 * @return sha1 hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA1Hash(String str) throws NoSuchAlgorithmException {
		return getSHA1Hash(str.getBytes());
	}

	/**
	 * @param byteArray
	 * @return sha1 hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA1Hash(byte[] byteArray) throws NoSuchAlgorithmException {
		return getHashedStrFor(byteArray, SHA1);
	}

	/**
	 * @param str
	 * @return sha2 hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA2Hash(String str) throws NoSuchAlgorithmException {
		return getSHA1Hash(str.getBytes());
	}

	/**
	 * @param byteArray
	 * @return sha2 hashed string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA2Hash(byte[] byteArray) throws NoSuchAlgorithmException {
		return getHashedStrFor(byteArray, SHA2);
	}

	/**
	 * @param byteArray
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
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
