package org.power.requests;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.power.requests.struct.Headers;

import java.io.IOException;

/**
 * http handler convert http response data to bytes
 *
 * @author Kuo Hong
 */
final class BytesResponseProcessor implements ResponseProcessor<byte[]> {

    @Override
    public byte[] convert(int statusCode, Headers headers, HttpEntity httpEntity)
            throws IOException {
        return (EntityUtils.toByteArray(httpEntity));
    }
}
