package org.easy.request.handler;


import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;
import org.easy.request.response.Response;

import java.io.IOException;

/**
 * ResponseHandler.
 *
 * @author Kuo Hong on 2015/8/22 0022.
 */
public class ResponseHandler extends AbstractResponseHandler<Response> {
    /**
     * Handle the response entity and transform it into the actual response
     * object.
     *
     * @param entity
     */
    @Override
    public Response handleEntity(HttpEntity entity) throws IOException {
        Response response = new Response();
        ContentType contentType = ContentType.getOrDefault(entity);
        response.setCharset(contentType.getCharset());
        response.setContentType(contentType);
        byte[] raw = EntityUtils.toByteArray(entity);
        response.setBytes(raw);
        //EntityUtils.consume(entity);
        return response;
    }
}
