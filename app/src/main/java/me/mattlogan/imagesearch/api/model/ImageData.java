package me.mattlogan.imagesearch.api.model;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageData {

    private String tbUrl;
    private String url;

    public ImageData(String tbUrl, String url) {
        this.tbUrl = tbUrl;
        this.url = url;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public String getUrl() {
        return url;
    }
}
