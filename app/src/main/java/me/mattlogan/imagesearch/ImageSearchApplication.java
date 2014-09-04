package me.mattlogan.imagesearch;

import android.app.Application;

import me.mattlogan.imagesearch.api.ImageSearchApi;
import me.mattlogan.imagesearch.api.ImageSearchApiBus;
import me.mattlogan.imagesearch.api.ImageSearchApiHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageSearchApplication extends Application {

    /**
     * Note: The Image Searcher supports a maximum of 8 result pages.
     * When combined with subsequent requests, a maximum total of 64 results are available.
     * It is not possible to request more than 64 results.
     */
    public static final int RESULTS_PER_PAGE = 8;
    public static final int MAXIMUM_RESULTS = 64;

    private static final String ENDPOINT = "https://ajax.googleapis.com";

    private ImageSearchApiHandler imageSearchApiHandler;

    @Override public void onCreate() {
        super.onCreate();
        imageSearchApiHandler = new ImageSearchApiHandler(this, buildImageSearchApi(),
                ImageSearchApiBus.getInstance());
        imageSearchApiHandler.registerForEvents();
    }

    ImageSearchApi buildImageSearchApi() {
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override public void intercept(RequestFacade request) {
                        request.addQueryParam("v", "1.0");
                        request.addQueryParam("rsz", Integer.toString(RESULTS_PER_PAGE));
                    }
                })
                .build()
                .create(ImageSearchApi.class);
    }
}
