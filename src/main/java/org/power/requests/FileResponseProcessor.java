package org.power.requests;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.power.requests.struct.Headers;
import org.power.requests.util.IOUtils;

import java.io.*;

/**
 * save http response to file
 *
 * @author Kuo Hong
 */
final class FileResponseProcessor implements ResponseProcessor<File> {
    private final File file;

    /**
     * save http response to file
     *
     * @param filePath the file path to write to
     */
    public FileResponseProcessor(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * save http response to file
     *
     * @param file the file to write to
     */
    public FileResponseProcessor(File file) {
        this.file = file;
    }

    /**
     * copy data into file output stream.
     * only save to file when return status is 200, otherwise return null
     *
     * @param httpEntity the http response entity
     * @return true if success
     */
    @Override
    public File convert(int statusCode, Headers headers, HttpEntity httpEntity) throws IOException {
        if (statusCode != 200) {
            EntityUtils.consume(httpEntity);
            return null;
        }
        try (InputStream in = httpEntity.getContent()) {
            try (OutputStream out = new FileOutputStream(this.file)) {
                IOUtils.copy(in, out);
            }
        }
        return this.file;
    }
}
