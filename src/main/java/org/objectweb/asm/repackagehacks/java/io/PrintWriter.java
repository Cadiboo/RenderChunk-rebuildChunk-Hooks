package org.objectweb.asm.repackagehacks.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class PrintWriter extends java.io.PrintWriter {

	public PrintWriter(final Writer out) {
		super(out);
	}

	public PrintWriter(final Writer out, final boolean autoFlush) {
		super(out, autoFlush);
	}

	public PrintWriter(final OutputStream out) {
		super(out);
	}

	public PrintWriter(final OutputStream out, final boolean autoFlush) {
		super(out, autoFlush);
	}

	public PrintWriter(final String fileName) throws FileNotFoundException {
		super(fileName);
	}

	public PrintWriter(final String fileName, final String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
	}

	public PrintWriter(final File file) throws FileNotFoundException {
		super(file);
	}

	public PrintWriter(final File file, final String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
	}

}
