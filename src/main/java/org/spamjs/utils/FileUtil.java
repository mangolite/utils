package org.spamjs.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtil.
 */
public final class FileUtil {

	/** The Constant RESOURCE. */
	private static final String RESOURCE = "resource";
	
	/** The Constant LOG. */
	private static final Log LOG = new Log();
	
	/** The Constant FILE_PREFIX. */
	public static final String FILE_PREFIX = "file://";

	/**
	 * Instantiates a new file util.
	 */
	private FileUtil() {
		throw new IllegalStateException(
				"This is a class with static methods and should not be instantiated");
	}

	/**
	 * Read file.
	 *
	 * @param filename the filename
	 * @return the string
	 */
	@SuppressWarnings(RESOURCE)
	public static String readFile(String filename) {
		boolean isFilesystem = filename.startsWith(FILE_PREFIX);
		filename = isFilesystem ? filename.substring(FILE_PREFIX.length())
				: filename;
		StringBuilder sb = new StringBuilder();
		InputStream in = null;
		BufferedReader reader = null;
		try {
			if (isFilesystem) {
				in = new FileInputStream(new File(filename));
			} else {
				in = FileUtil.class.getResourceAsStream(filename);
			}
			if (in == null) {
				return null;
			}
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}

		} catch (IOException e) {
			LOG.error("cannot load "
					+ (isFilesystem ? "filesystem" : "classpath") + " file "
					+ filename, e);
		} finally {
			CloseUtil.close(reader);
			CloseUtil.close(in);
		}
		return sb.toString();
	}

	/**
	 * Write the data into file.
	 *
	 * @param fileLocation            Path of file
	 * @param content            Content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void saveToFile(String fileLocation, String content)
			throws IOException {
		Writer output = null;
		File file = new File(fileLocation);

		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(content);

		} catch (IOException io) {
			throw io;
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * Save to file.
	 *
	 * @param fileLocation the file location
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void saveToFile(String fileLocation, byte[] content)
			throws IOException {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(new File(fileLocation));
			// create an object of BufferedOutputStream
			bos = new BufferedOutputStream(fos);
			bos.write(content);

		} catch (IOException io) {
			throw io;
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}
}
