package org.weiwei.ftp.listparsers;

import org.weiwei.ftp.FTPFile;
import org.weiwei.ftp.exception.FTPListParseException;


public interface FTPListParser {
	/**
	 * Parses a LIST command response and builds an array of FTPFile objects.
	 * 
	 * @param lines
	 *            The response to parse, splitted by line.
	 * @return An array of FTPFile objects representing the result of the
	 *         operation.
	 * @throws FTPListParseException
	 *             If this parser cannot parse the given response.
	 */
	public FTPFile[] parse(String[] lines) throws FTPListParseException;
}
