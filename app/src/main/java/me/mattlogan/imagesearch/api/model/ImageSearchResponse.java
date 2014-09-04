package me.mattlogan.imagesearch.api.model;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageSearchResponse {

    private ResponseData responseData;

    public ImageSearchResponse(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }
}
