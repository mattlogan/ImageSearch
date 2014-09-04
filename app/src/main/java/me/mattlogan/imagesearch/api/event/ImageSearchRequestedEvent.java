package me.mattlogan.imagesearch.api.event;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageSearchRequestedEvent {

    private String query;
    private int startIndex;

    public ImageSearchRequestedEvent(String query, int startIndex) {
        this.query = query;
        this.startIndex = startIndex;
    }

    public String getQuery() {
        return query;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
