package org.easy.request.response;

import org.apache.http.entity.ContentType;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Response.
 *
 * @author Kuo Hong on 2015/8/22 0022.
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 3549762366369586129L;
    private ContentType contentType;
    private String raw;
    private String text;
    private byte[] bytes;
    private Charset charset;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getRaw() {
        return new String(bytes, Charset.forName("ISO-8859-1"));
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getText() {
        return new String(bytes, charset);
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

}
