package org.power.requests;

import org.power.requests.exception.RequestException;
import org.power.requests.struct.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * http requests builder
 *
 * @author Kuo Hong
 */
public class RequestBuilder {
    private Method method;
    private URI url;
    private Parameters parameters;
    private String userAgent = Utils.defaultUserAgent;
    private Headers headers;
    private Cookies cookies;

    private Charset charset = StandardCharsets.UTF_8;
    private Charset responseCharset = StandardCharsets.UTF_8;

    private byte[] body;
    private String strBody;
    // parameter type body(form-encoded)
    private Parameters formParameters;
    // http multi part post request multiParts
    private List<MultiPart> multiParts;
    // http request body from inputStream
    private InputStream in;

    private int connectTimeout = 10_000;
    private int socketTimeout = 10_000;

    private boolean gzip = true;
    // if check ssl certificate
    private boolean verify = true;
    private boolean allowRedirects = true;
    private boolean allowPostRedirects = false;
    //private CredentialsProvider provider;
    private AuthInfo authInfo;
    private String[] cert;
    private Proxy proxy;

    private Session session;
    private PooledClient pooledClient;

    RequestBuilder() {
    }

    /**
     * get http response for return result with Type T.
     */
    <T> Response<T> client(ResponseProcessor<T> processor) throws RequestException {
        try {
            return new RequestExecutor<>(build(), processor, session, pooledClient).execute();
        } catch (IOException e) {
            throw new RequestException(e);
        }
    }

    /**
     * set custom handler to handle http response
     */
    public <T> Response<T> handler(ResponseHandler<T> handler) throws RequestException {
        return client(new ResponseHandlerAdapter<T>(handler));
    }

    /**
     * Get http response for return text result.
     * Decode response body to text with charset get from response header,
     * use {@link #responseCharset(Charset)} to set charset if needed.
     */
    public Response<String> text() throws RequestException {
        return client(new StringResponseProcessor(responseCharset));
    }

    /**
     * get http response for return byte array result.
     */
    public Response<byte[]> bytes() throws RequestException {
        return client(ResponseProcessor.bytes);
    }

    /**
     * get http response for write response body to file.
     * only save to file when return status is 200, otherwise return response with null body.
     */
    public Response<File> file(File file) throws RequestException {
        return client(new FileResponseProcessor(file));
    }

    /**
     * get http response for write response body to file.
     * only save to file when return status is 200, otherwise return response with null body.
     */
    public Response<File> file(String filePath) throws RequestException {
        return client(new FileResponseProcessor(filePath));
    }

    RequestBuilder url(String url) throws RequestException {
        try {
            this.url = new URI(url);
        } catch (URISyntaxException e) {
            throw new RequestException(e);
        }
        return this;
    }

    Request build() {
        return new Request(method, url, parameters, userAgent, headers, in, multiParts, body,
                strBody, formParameters, charset, authInfo, gzip, verify, cookies, allowRedirects, allowPostRedirects,
                connectTimeout, socketTimeout, proxy);
    }

    /**
     * set userAgent
     */
    public RequestBuilder userAgent(String userAgent) {
        Objects.requireNonNull(userAgent);
        this.userAgent = userAgent;
        return this;
    }

    /**
     * Set params of url query string.
     * This is for set parameters in url query str, If want to set post form params use form((Map&lt;String, ?&gt; params) method
     */
    public RequestBuilder params(Map<String, ?> params) {
        this.parameters = new Parameters();
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            this.parameters.add(new Parameter(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    /**
     * Set params of url query string.
     * This is for set parameters in url query str, If want to set post form params use form(Parameter... params) method
     */
    public RequestBuilder params(Parameter... params) {
        this.parameters = new Parameters();
        for (Parameter param : params) {
            this.parameters.add(new Parameter(param.getName(), param.getValue()));
        }
        return this;
    }

    /**
     * Add one parameter to url query string.
     * This is for set parameters in url query str, If want to set post form params use form(String key, Object value) method
     */
    public RequestBuilder addParam(String key, Object value) {
        ensureParameters();
        this.parameters.add(new Parameter(key, value));
        return this;
    }

    /**
     * Add one parameter to url query string.
     * This is for set parameters in url query str, If want to set post form params use form(String key, Object value) method
     *
     * @deprecated use {@link #addParam(String, Object)} method instead
     */
    @Deprecated
    public RequestBuilder param(String key, Object value) {
        return addParam(key, value);
    }

    private void ensureParameters() {
        if (this.parameters == null) {
            this.parameters = new Parameters();
        }
    }

    /**
     * Add http form body data, for http post method with form-encoded body.
     *
     * @deprecated use {@link #addForm(String, Object)} method
     */
    @Deprecated
    public RequestBuilder form(String key, Object value) {
        return this.addForm(key, value);
    }

    /**
     * Add http form body data, for http post method with form-encoded body.
     */
    public RequestBuilder addForm(String key, Object value) {
        ensureFormParameters();
        formParameters.add(new Parameter(key, value));
        return this;
    }

    /**
     * Set http form body data, for http post method with form-encoded body.
     */
    public RequestBuilder forms(Map<String, ?> params) {
        this.formParameters = new Parameters();
        for (Map.Entry<String, ?> e : params.entrySet()) {
            formParameters.add(new Parameter(e.getKey(), e.getValue()));
        }
        return this;
    }

    /**
     * Set http form body data, for http post method with form-encoded body.
     */
    public RequestBuilder forms(Parameter... params) {
        this.formParameters = new Parameters();
        for (Parameter param : params) {
            formParameters.add(param);
        }
        return this;
    }

    /**
     * Set http form body data, for http post method with form-encoded body.
     *
     * @deprecated use {@link #forms(Map)} method instead
     */
    @Deprecated
    public RequestBuilder form(Map<String, ?> params) {
        return forms(params);
    }

    /**
     * Set http form body data, for http post method with form-encoded body.
     *
     * @deprecated use {@link #forms(Parameter...)} method instead
     */
    @Deprecated
    public RequestBuilder form(Parameter... params) {
        return forms(params);
    }

    /**
     * Set http form body data, for http post method with form-encoded body.
     *
     * @deprecated use {@link #forms(Map)} method instead
     */
    @Deprecated
    public RequestBuilder data(Map<String, ?> params) {
        return forms(params);
    }

    /**
     * Set http form body data, for http post method with form-encoded body.
     *
     * @deprecated use {@link #forms(Parameter...)} method instead
     */
    @Deprecated
    public RequestBuilder data(Parameter... params) {
        return forms(params);
    }

    private void ensureFormParameters() {
        if (this.formParameters == null) {
            this.formParameters = new Parameters();
        }
    }

    /**
     * Set http body data for Post/Put request
     *
     * @param data the data to post
     */
    public RequestBuilder data(byte[] data) {
        this.body = data;
        return this;
    }

    /**
     * Set http data from inputStream for Post/Put request
     */
    public RequestBuilder data(InputStream in) {
        this.in = in;
        return this;
    }

    /**
     * Set http data with text.
     * The text string will be encoded, default using utf-8, set charset with charset(Charset charset) method
     */
    public RequestBuilder data(String body) {
        this.strBody = body;
        return this;
    }

    /**
     * Set charset used to encode request, default utf-8.
     */
    public RequestBuilder charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Set charset used to encode request, default utf-8.
     */
    public RequestBuilder charset(String charset) {
        return charset(Charset.forName(charset));
    }

    /**
     * Set charset for decoding response.
     * Note that usually you do not need to set this because we can get charset from response header.
     */
    public RequestBuilder responseCharset(Charset charset) {
        this.responseCharset = charset;
        return this;
    }

    /**
     * Set charset for decoding response.
     * Note that usually you do not need to set this because we can get charset from response header.
     */
    public RequestBuilder responseCharset(String charset) {
        return responseCharset(Charset.forName(charset));
    }

    RequestBuilder method(Method method) {
        this.method = method;
        return this;
    }

    /**
     * Set headers
     */
    public RequestBuilder headers(Map<String, ?> params) {
        this.headers = new Headers();
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            this.headers.add(new Header(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    /**
     * Set headers
     */
    public RequestBuilder headers(Header... headers) {
        this.headers = new Headers();
        for (Header header : headers) {
            this.headers.add(header);
        }
        return this;
    }

    /**
     * Add one header
     *
     * @deprecated use {@link #addHeader(String, Object)} method
     */
    @Deprecated
    public RequestBuilder header(String key, Object value) {
        return addHeader(key, value);
    }

    /**
     * Add one header
     */
    public RequestBuilder addHeader(String key, Object value) {
        ensureHeaders();
        this.headers.add(new Header(key, value));
        return this;
    }

    private void ensureHeaders() {
        if (this.headers == null) {
            this.headers = new Headers();
        }
    }

    /**
     * set socket connect and read timeout in milliseconds. default is 10_000.
     * A timeout value of zero is interpreted as an infinite timeout.
     * A negative value is interpreted as undefined (system default).
     */
    public RequestBuilder timeout(int timeout) {
        this.socketTimeout = this.connectTimeout = timeout;
        return this;
    }

    /**
     * set socket connect and read timeout in milliseconds. default is 10_000.
     * A timeout value of zero is interpreted as an infinite timeout.
     * A negative value is interpreted as undefined (system default).
     */
    public RequestBuilder timeout(int connectTimeout, int socketTimeout) {
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
        return this;
    }

    /**
     * set proxy
     */
    public RequestBuilder proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    /**
     * if send gzip requests. default true
     */
    public RequestBuilder gzip(boolean gzip) {
        this.gzip = gzip;
        return this;
    }

    /**
     * set false to disable ssl check for https requests
     */
    public RequestBuilder verify(boolean verify) {
        this.verify = verify;
        return this;
    }

    /**
     * set http basic auth info
     */
    public RequestBuilder auth(String userName, String password) {
        authInfo = new AuthInfo(userName, password);
        return this;
    }

    /**
     * Set cookies
     */
    public RequestBuilder cookies(Map<String, String> cookies) {
        this.cookies = new Cookies();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            this.cookies.add(new Cookie(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    /**
     * Set cookies
     */
    public RequestBuilder cookies(Cookie... cookies) {
        this.cookies = new Cookies();
        for (Cookie cookie : cookies) {
            this.cookies.add(cookie);
        }
        return this;
    }

    /**
     * Add one cookie
     *
     * @deprecated use {@link #addCookie(String, String)} method
     */
    @Deprecated
    public RequestBuilder cookie(String name, String value) {
        return addCookie(name, value);
    }

    /**
     * Add one cookie
     */
    public RequestBuilder addCookie(String name, String value) {
        ensureCookies();
        this.cookies.add(new Cookie(name, value));
        return this;
    }

    private void ensureCookies() {
        if (this.cookies == null) {
            this.cookies = new Cookies();
        }
    }

    /**
     * If follow get/head redirect, default true.
     * This method not set following redirect for post/put/delete method, use {@code allowPostRedirects} if you want this
     */
    public RequestBuilder allowRedirects(boolean allowRedirects) {
        this.allowRedirects = allowRedirects;
        return this;
    }

    /**
     * If follow POST/PUT/DELETE redirect, default false. This method work for post method.
     */
    public RequestBuilder allowPostRedirects(boolean allowPostRedirects) {
        this.allowPostRedirects = allowPostRedirects;
        return this;
    }

    /**
     * set cert path
     * TODO: custom cert
     */
    public RequestBuilder cert(String... cert) {
        throw new UnsupportedOperationException();
//        this.cert = cert;
//        return this;
    }

    /**
     * add multi part file, will send multiPart requests.
     *
     * @param name the http request field name for this file
     * @param file the file to be send
     * @deprecated use {@link #addMultiPart(String, File)} method
     */
    @Deprecated
    public RequestBuilder multiPart(String name, File file) {
        return addMultiPart(name, file);
    }

    /**
     * add multi part file, will send multiPart requests.
     *
     * @param name the http request field name for this file
     * @param file the file to be send
     */
    public RequestBuilder addMultiPart(String name, File file) {
        ensureMultiPart();
        this.multiParts.add(new MultiPart(name, file));
        return this;
    }

    /**
     * add multi part file, will send multiPart requests.
     * this should be used with post method
     *
     * @param name     the http request field name for this file
     * @param mimeType the mimeType for file
     * @param file     the file to be send
     * @deprecated use {@link #addMultiPart(String, String, File)} method
     */
    @Deprecated
    public RequestBuilder multiPart(String name, String mimeType, File file) {
        return addMultiPart(name, mimeType, file);
    }

    /**
     * add multi part file, will send multiPart requests.
     * this should be used with post method
     *
     * @param name     the http request field name for this file
     * @param mimeType the mimeType for file
     * @param file     the file to be send
     */
    public RequestBuilder addMultiPart(String name, String mimeType, File file) {
        ensureMultiPart();
        this.multiParts.add(new MultiPart(name, mimeType, file));
        return this;
    }

    /**
     * add multi part field by byte array, will send multiPart requests.
     *
     * @param name  the http request field name for this field
     * @param bytes the multipart request content
     * @deprecated use {@link #addMultiPart(String, String, byte[])} method
     */
    @Deprecated
    public RequestBuilder multiPart(String name, String mimeType, byte[] bytes) {
        return addMultiPart(name, mimeType, bytes);
    }

    /**
     * add multi part field by byte array, will send multiPart requests.
     *
     * @param name  the http request field name for this field
     * @param bytes the multipart request content
     */
    public RequestBuilder addMultiPart(String name, String mimeType, byte[] bytes) {
        multiPart(name, mimeType, null, bytes);
        return this;
    }

    /**
     * add multi part field by byte array, will send multiPart requests.
     *
     * @param name     the http request field name for this field
     * @param fileName the file name. can be null
     * @param bytes    the multipart request content
     * @deprecated use {@link #addMultiPart(String, String, String, byte[])} method
     */
    @Deprecated
    public RequestBuilder multiPart(String name, String mimeType, String fileName, byte[] bytes) {
        return addMultiPart(name, mimeType, fileName, bytes);
    }

    /**
     * add multi part field by byte array, will send multiPart requests.
     *
     * @param name     the http request field name for this field
     * @param fileName the file name. can be null
     * @param bytes    the multipart request content
     */
    public RequestBuilder addMultiPart(String name, String mimeType, String fileName, byte[] bytes) {
        ensureMultiPart();
        this.multiParts.add(new MultiPart(name, mimeType, fileName, bytes));
        return this;
    }

    /**
     * add multi part field by inputStream, will send multiPart requests.
     *
     * @param name     the http request field name for this field
     * @param mimeType the mimeType for file
     * @param in       the inputStream for the content
     * @deprecated use {@link #addMultiPart(String, String, InputStream)} method
     */
    @Deprecated
    public RequestBuilder multiPart(String name, String mimeType, InputStream in) {
        return addMultiPart(name, mimeType, in);
    }

    /**
     * add multi part field by inputStream, will send multiPart requests.
     *
     * @param name     the http request field name for this field
     * @param mimeType the mimeType for file
     * @param in       the inputStream for the content
     */
    public RequestBuilder addMultiPart(String name, String mimeType, InputStream in) {
        multiPart(name, mimeType, null, in);
        return this;
    }

    /**
     * add multi part field by inputStream, will send multiPart requests.
     *
     * @param name     the http request field name for this field
     * @param mimeType the mimeType for file
     * @param fileName the file name. can be null
     * @param in       the inputStream for the content
     */
    public RequestBuilder multiPart(String name, String mimeType, String fileName, InputStream in) {
        ensureMultiPart();
        this.multiParts.add(new MultiPart(name, mimeType, fileName, in));
        return this;
    }

    /**
     * Add multi part key-value parameter.
     *
     * @param name  the http request field name
     * @param value the file to be send
     * @deprecated use {@link #addMultiPart(String, File)} method
     */
    @Deprecated
    public RequestBuilder multiPart(String name, String value) {
        return addMultiPart(name, value);
    }

    /**
     * Add multi part key-value parameter.
     *
     * @param name  the http request field name
     * @param value the file to be send
     */
    public RequestBuilder addMultiPart(String name, String value) {
        ensureMultiPart();
        this.multiParts.add(new MultiPart(name, value));
        return this;
    }

    private void ensureMultiPart() {
        if (this.multiParts == null) {
            this.multiParts = new ArrayList<>();
        }
    }

    RequestBuilder session(Session session) {
        this.session = session;
        return this;
    }

    /**
     * Set connection pool. used to reuse http connections.
     */
    RequestBuilder connectionPool(PooledClient pooledClient) {
        this.pooledClient = pooledClient;
        return this;
    }
}
