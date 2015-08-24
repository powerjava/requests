package org.power.requests;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.power.requests.struct.Headers;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * convert http response to String
 *
 * @author Kuo Hong
 */
final class StringResponseProcessor implements ResponseProcessor<String> {
    // can be null
    private final Charset charset;

    public StringResponseProcessor(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String convert(int statusCode, Headers headers, HttpEntity httpEntity)
            throws IOException {
        return EntityUtils.toString(httpEntity, charset);
    }
}
