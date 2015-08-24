package org.power.requests.struct;

import java.io.File;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * for multi part request
 *
 * @author Kuo Hong
 */
public class MultiPart {
    private final Type type;
    // the filed name name
    private final String name;
    // the file content type
    private final String mime;
    // the file name, can be null
    private final String fileName;
    // the file for multi part upload
    private final File file;
    private final InputStream in;
    private final byte[] bytes;
    private final String value;

    public MultiPart(String name, String value) {
        this.file = null;
        this.mime = null;
        this.name = name;
        this.type = Type.TEXT;
        this.in = null;
        this.bytes = null;
        this.value = value;
        this.fileName = null;
    }

    /**
     * get multiPart from file path
     */
    public MultiPart(String name, File file) {
        this(name, URLConnection.guessContentTypeFromName(file.getName()), file);
    }

    /**
     * get multiPart from file path
     */
    public MultiPart(String name, String mime, File file) {
        this.file = file;
        this.mime = mime;
        this.name = name;
        this.type = Type.FILE;
        this.in = null;
        this.bytes = null;
        this.value = null;
        this.fileName = file.getName();
    }

    /**
     * get multiPart from file path
     */
    public MultiPart(String name, String mime, String fileName, InputStream in) {
        this.file = null;
        this.in = in;
        this.bytes = null;
        this.mime = mime;
        this.name = name;
        this.type = Type.STREAM;
        this.value = null;
        this.fileName = fileName;
    }

    /**
     * get multiPart from file path
     */
    public MultiPart(String name, String mime, String fileName, byte[] bytes) {
        this.file = null;
        this.in = null;
        this.bytes = bytes;
        this.mime = mime;
        this.name = name;
        this.type = Type.BYTES;
        this.value = null;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public String getMime() {
        return mime;
    }

    public String getFileName() {
        return fileName;
    }

    public Type getType() {
        return type;
    }

    public InputStream getIn() {
        return in;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        FILE, STREAM, BYTES, TEXT
    }
}
