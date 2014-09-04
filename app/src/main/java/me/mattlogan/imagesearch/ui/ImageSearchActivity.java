package me.mattlogan.imagesearch.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.mattlogan.imagesearch.ImageSearchApplication;
import me.mattlogan.imagesearch.R;
import me.mattlogan.imagesearch.api.ImageSearchApiBus;
import me.mattlogan.imagesearch.api.event.ImageSearchFailedEvent;
import me.mattlogan.imagesearch.api.event.ImageSearchRequestedEvent;
import me.mattlogan.imagesearch.api.event.ImageSearchSuccessEvent;
import me.mattlogan.imagesearch.api.model.ImageData;


public class ImageSearchActivity extends BaseActivity implements TextView.OnEditorActionListener,
        AbsListView.OnScrollListener {

    private ImageSearchApiBus imageSearchApiBus = ImageSearchApiBus.getInstance();

    private EditText editText;
    private GridView gridView;

    private ImageSearchGridAdapter adapter;

    private int lastImageFetchStartIndex;

    private static final String IMAGE_DATA_LIST_KEY = "image_data_list";
    private List<ImageData> imageDataList;

    @SuppressWarnings("unchecked")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        editText = (EditText) findViewById(R.id.image_search_edit_text);
        gridView = (GridView) findViewById(R.id.image_search_grid_view);

        editText.setOnEditorActionListener(this);

        adapter = new ImageSearchGridAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(this);

        if (savedInstanceState != null) {
            imageDataList =
                    (List<ImageData>) savedInstanceState.getSerializable(IMAGE_DATA_LIST_KEY);
        } else {
            imageDataList = new ArrayList<ImageData>();
        }
    }

    @Override public void onResume() {
        super.onResume();
        imageSearchApiBus.register(this);
    }

    @Override public void onPause() {
        super.onPause();
        imageSearchApiBus.unregister(this);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(IMAGE_DATA_LIST_KEY, (Serializable) imageDataList);
    }

    @Override public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            imageDataList.clear();
            hideKeyboard();
            requestImageFetch(0);
            return true;
        }
        return false;
    }

    @Override public void onScrollStateChanged(AbsListView absListView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

        final int lastItem = firstVisibleItem + visibleItemCount;

        if (lastItem == totalItemCount && totalItemCount != lastImageFetchStartIndex) {
            requestImageFetch(totalItemCount);
        }
    }

    private void requestImageFetch(int startIndex) {
        if (startIndex + ImageSearchApplication.RESULTS_PER_PAGE <=
                ImageSearchApplication.MAXIMUM_RESULTS) {

            imageSearchApiBus.post(new ImageSearchRequestedEvent(editText.getText().toString(),
                    startIndex));
            lastImageFetchStartIndex = startIndex;
        }
    }

    @Subscribe public void onImageSearchSuccess(ImageSearchSuccessEvent event) {
        imageDataList.addAll(event.getImageSearchResponse().getResponseData().getResults());
        adapter.notifyDataSetChanged();
    }

    @Subscribe public void onImageSearchFailed(ImageSearchFailedEvent event) {
        Toast.makeText(this, "Failed to load images", Toast.LENGTH_LONG).show();
    }

    private class ImageSearchGridAdapter extends BaseAdapter {

        private Context context;

        private ImageSearchGridAdapter(Context context) {
            this.context = context;
        }

        @Override public int getCount() {
            return imageDataList == null ? 0 : imageDataList.size();
        }

        @Override public Object getItem(int position) {
            return imageDataList == null ? null : imageDataList.get(position);
        }

        @Override public long getItemId(int position) {
            return 0;
        }

        @Override public View getView(int position, View view, ViewGroup parent) {
            ImageView imageView = (ImageView) view;
            if (imageView == null) {
                imageView = new ImageView(context);
                int height = (int) (context.getResources().getDisplayMetrics().widthPixels / 3f);
                imageView.setLayoutParams(new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            Picasso.with(context)
                    .load(imageDataList.get(position).getTbUrl())
                    .into(imageView);

            return imageView;
        }
    }
}
