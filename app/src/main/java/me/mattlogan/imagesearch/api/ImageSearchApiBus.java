package me.mattlogan.imagesearch.api;

import com.squareup.otto.Bus;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageSearchApiBus extends Bus {

    private static ImageSearchApiBus singleton;

    public static ImageSearchApiBus getInstance() {
        if (singleton == null) {
            singleton = new ImageSearchApiBus();
        }
        return singleton;
    }
}
