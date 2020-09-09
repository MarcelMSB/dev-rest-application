package dev.marcel.application.common.feign.encoder;

import static feign.form.util.CharsetUtil.UTF_8;
import static java.util.Arrays.asList;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.ContentProcessor;
import feign.form.ContentType;
import feign.form.MultipartFormContentProcessor;
import feign.form.UrlencodedFormContentProcessor;

public class FeignEncoder implements Encoder {

    private static final Pattern CHARSET_PATTERN = Pattern.compile("(?<=charset=)([\\w\\-]+)");
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final Encoder ENCODER_DEFAULT = new Encoder.Default();

    Encoder delegate;

    Map<ContentType, ContentProcessor> processors;

    public FeignEncoder() {
        this(ENCODER_DEFAULT);
    }

    public FeignEncoder(Encoder delegate) {
        this.delegate = delegate;

        List<ContentProcessor> list = asList(new MultipartFormContentProcessor(delegate),
                new UrlencodedFormContentProcessor());

        processors = new HashMap<>(list.size(), 1.F);
        list.forEach((processor) -> processors.put(processor.getSupportedContentType(), processor));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        String contentTypeValue = getContentTypeValue(template.headers());
        ContentType contentType = ContentType.of(contentTypeValue);
        try {
            if (object instanceof MultipartFormDataOutput) {
                processors.get(contentType).process(template, getCharset(contentTypeValue), ((MultipartFormDataOutput) object).asMap());
            } else if (object instanceof ElasticsearchBulkOutput) {
                ENCODER_DEFAULT.encode(((ElasticsearchBulkOutput) object).asString(), String.class, template);
            } else {
                delegate.encode(object, bodyType, template);
            }
        } catch (Exception ex) {
            throw new EncodeException(ex.getMessage());
        }
    }

    public final ContentProcessor getContentProcessor(ContentType type) {
        return processors.get(type);
    }

    @SuppressWarnings("PMD.AvoidBranchingStatementAsLastInLoop")
    private String getContentTypeValue(Map<String, Collection<String>> headers) {
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(CONTENT_TYPE_HEADER)) {
                continue;
            }
            for (String contentTypeValue : entry.getValue()) {
                if (contentTypeValue == null) {
                    continue;
                }
                return contentTypeValue;
            }
        }
        return null;
    }

    private Charset getCharset(String contentTypeValue) {
        Matcher matcher = CHARSET_PATTERN.matcher(contentTypeValue);
        return matcher.find() ?
                Charset.forName(matcher.group(1)) :
                UTF_8;
    }
}
