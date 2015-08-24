package org.power.requests;

import org.apache.http.HttpEntity;
import org.power.requests.struct.Headers;

import java.io.IOException;

/**
 * interface to trans data to result
 *
 * @author Kuo Hong
 */
interface ResponseProcessor<T> {

    static ResponseProcessor<String> string = new StringResponseProcessor(null);

    static ResponseProcessor<byte[]> bytes = new BytesResponseProcessor();

    /**
     * from http Body to result with type T
     */
    T convert(int statusCode, Headers headers, HttpEntity httpEntity) throws IOException;
}
