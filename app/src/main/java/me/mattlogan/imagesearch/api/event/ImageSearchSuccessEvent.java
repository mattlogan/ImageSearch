package me.mattlogan.imagesearch.api.event;

import me.mattlogan.imagesearch.api.model.ImageSearchResponse;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageSearchSuccessEvent {

    private ImageSearchResponse imageSearchResponse;

    public ImageSearchSuccessEvent(ImageSearchResponse imageSearchResponse) {
        this.imageSearchResponse = imageSearchResponse;
    }

    public ImageSearchResponse getImageSearchResponse() {
        return imageSearchResponse;
    }
}
