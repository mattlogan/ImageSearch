package me.mattlogan.imagesearch.api;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import me.mattlogan.imagesearch.api.event.ImageSearchFailedEvent;
import me.mattlogan.imagesearch.api.event.ImageSearchRequestedEvent;
import me.mattlogan.imagesearch.api.event.ImageSearchSuccessEvent;
import me.mattlogan.imagesearch.api.model.ImageSearchResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ImageSearchApiHandler {

    private Context context;
    private ImageSearchApi imageSearchApi;
    private ImageSearchApiBus imageSearchApiBus;

    public ImageSearchApiHandler(Context context, ImageSearchApi imageSearchApi,
                                 ImageSearchApiBus imageSearchApiBus) {

        this.context = context;
        this.imageSearchApi = imageSearchApi;
        this.imageSearchApiBus = imageSearchApiBus;
    }

    public void registerForEvents() {
        imageSearchApiBus.register(this);
    }

    @Subscribe public void onImageSearchRequestedEvent(ImageSearchRequestedEvent event) {
        Map<String, String> options = new HashMap<String, String>();
        options.put("q", event.getQuery());
        options.put("start", Integer.toString(event.getStartIndex()));

        imageSearchApi.getImages(options, new Callback<ImageSearchResponse>() {
            @Override
            public void success(ImageSearchResponse imageSearchResponse, Response response) {
                imageSearchApiBus.post(new ImageSearchSuccessEvent(imageSearchResponse));
            }

            @Override public void failure(RetrofitError error) {
                imageSearchApiBus.post(new ImageSearchFailedEvent());
            }
        });
    }
}
