package org.power.requests;

import org.apache.http.HttpEntity;
import org.power.requests.struct.Headers;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kuo Hong
 */
public class ResponseHandlerAdapter<T> implements ResponseProcessor<T> {

    private final ResponseHandler<T> responseHandler;

    ResponseHandlerAdapter(ResponseHandler<T> responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public T convert(int statusCode, Headers headers, HttpEntity httpEntity) throws IOException {
        try (InputStream in = httpEntity.getContent()) {
            return responseHandler.handle(statusCode, headers, in);
        }
    }
}
