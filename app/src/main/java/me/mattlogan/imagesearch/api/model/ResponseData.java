package me.mattlogan.imagesearch.api.model;

import java.util.List;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ResponseData {

    private List<ImageData> results;

    public ResponseData(List<ImageData> results) {
        this.results = results;
    }

    public List<ImageData> getResults() {
        return results;
    }
}
