package com.mdiapps.mdi.newstracker.helpers.CustomAdapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.mdiapps.mdi.newstracker.R;
import com.mdiapps.mdi.newstracker.helpers.VolleyLibrary.AppController;
import com.mdiapps.mdi.newstracker.utils.UrlUtil;

import java.util.List;

/**
 * Created by Mascot on 2/12/2017.
 */
public class NewsAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> news_author;
    private final List<String> news_title;
    private final List<String> news_description;
    private final List<String> news_datetime;
    private final List<String> news_imageurl;
    private final List<String> news_mainurl;

    public NewsAdapter(Activity context, List<String> news_author, List<String> news_title, List<String> news_description, List<String> news_datetime, List<String> news_imageurl, List<String> news_mainurl) {
        super(context, R.layout.mylist, news_title);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.news_author=news_author;
        this.news_title=news_title;
        this.news_description=news_description;
        this.news_datetime=news_datetime;
        this.news_imageurl=news_imageurl;
        this.news_mainurl=news_mainurl;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cardview_news, null,true);



        TextView txt_news_title = (TextView) rowView.findViewById(R.id.cardview_news_tv_title);
        TextView txt_news_description = (TextView) rowView.findViewById(R.id.cardview_news_tv_description);
        final ImageView img_news_image = (ImageView) rowView.findViewById(R.id.cardview_news_mainimage);
        final ImageView img_news_icon = (ImageView) rowView.findViewById(R.id.cardview_news_iv_newsicon);

        txt_news_title.setText(news_title.get(position));
        txt_news_description.setText(news_description.get(position));


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(news_imageurl.get(position), new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Image Load Error: ",error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    img_news_image.setImageBitmap(response.getBitmap());
                }
            }
        });

        imageLoader.get(UrlUtil.SELECTED_NEWS_ICON_URL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Image Load Error: ",error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    img_news_icon.setImageBitmap(response.getBitmap());
                }
            }
        });

        return rowView;

    };
}
