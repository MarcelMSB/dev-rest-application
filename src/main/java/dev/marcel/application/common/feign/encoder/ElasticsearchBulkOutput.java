package dev.marcel.application.common.feign.encoder;

public class ElasticsearchBulkOutput {

    private final String entity;

    private ElasticsearchBulkOutput(final String entity) {
        this.entity = entity;
    }

    public static ElasticsearchBulkOutput of(final String entity) {
        return new ElasticsearchBulkOutput(entity);
    }
   
    public String asString() {
        return entity;
    }
}
