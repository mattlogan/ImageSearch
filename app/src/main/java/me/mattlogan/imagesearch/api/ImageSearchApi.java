package me.mattlogan.imagesearch.api;

import java.util.Map;

import me.mattlogan.imagesearch.api.model.ImageSearchResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by matthewlogan on 9/3/14.
 */
public interface ImageSearchApi {
    @GET("/ajax/services/search/images")
    public void getImages(@QueryMap Map<String, String> options,
                          Callback<ImageSearchResponse> response);
}
