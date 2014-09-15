package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha2Util {

	private static final Log LOG = new Log();

	private Sha2Util() {
		throw new IllegalStateException(
				"This is a utility class with static methods and should not be instantiated");
	}

	public static String getSha2inHex(String input) {

		// Return blank String if input String is null
		if (input == null) {
			return "";
		}

		// Convert input String to byte array
		byte[] inputArr = input.getBytes();
		byte[] mdbytes = null;
		try {
			// Get MessageDigest for sha2 algorithm
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(inputArr);
			mdbytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("NoSuchAlgorithmException occured:" + e.getMessage(), e);
		}
		// convert the sha2 to hex
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
		}
		return hexString.toString();
	}
}
