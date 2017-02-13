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

import java.util.List;

/**
 * Created by Mascot on 2/11/2017.
 */
public class NewsSourceAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> news_source_id;
    private final List<String> news_source_title;
    private final List<String> news_source_detail;
    private final List<String> news_source_imageurl;

    public NewsSourceAdapter(Activity context, List<String> news_source_id, List<String> news_source_title, List<String> news_source_detail, List<String> news_source_imageurl) {
        super(context, R.layout.mylist, news_source_title);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.news_source_id=news_source_id;
        this.news_source_title=news_source_title;
        this.news_source_detail=news_source_detail;
        this.news_source_imageurl=news_source_imageurl;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);



        TextView txt_noticelist_notice_title = (TextView) rowView.findViewById(R.id.txt_user_notice_title);
        TextView txt_noticelist_notice_detail = (TextView) rowView.findViewById(R.id.txt_user_notice_detail);
        final ImageView notice_image = (ImageView) rowView.findViewById(R.id.icon);

        txt_noticelist_notice_title.setText(news_source_title.get(position));
        txt_noticelist_notice_detail.setText(news_source_detail.get(position));


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(news_source_imageurl.get(position), new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Image Load Error: ",error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    notice_image.setImageBitmap(response.getBitmap());
                }
            }
        });

        return rowView;

    };
}
