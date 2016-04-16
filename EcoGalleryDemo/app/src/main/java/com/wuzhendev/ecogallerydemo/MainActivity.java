package com.wuzhendev.ecogallerydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import us.feras.ecogallery.EcoGallery;

public class MainActivity extends Activity {

    private EcoGallery gallery;
    private SampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new SampleAdapter();

        gallery = (EcoGallery) findViewById(R.id.gallery);
        gallery.setAdapter(mAdapter);
    }

    public class SampleAdapter extends BaseAdapter {

        public SampleAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sample, parent, false);
            }

            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            int resid = getResources().getIdentifier("pic" + position % 10, "drawable",
                    parent.getContext().getPackageName());
            iv.setImageResource(resid);
            return convertView;
        }
    }
}
