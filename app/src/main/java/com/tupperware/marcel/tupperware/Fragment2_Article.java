package com.tupperware.marcel.tupperware;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Marcel on 14.12.2016.
 */

public class Fragment2_Article extends Fragment{
    private String dimensions;
    private String content;
    private String price;
    private String color;
    private String note;

    View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {
            dimensions = bundle.getString("dimensions");
            content = bundle.getString("content");
            price = bundle.getString("price");
            color = bundle.getString("color");
            note = bundle.getString("note");
        }

        contentView = inflater.inflate(R.layout.layout_2_article, null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvDimensionsContent = (TextView) contentView.findViewById(R.id.tvDimensionsContent);
        TextView tvContentContent = (TextView) contentView.findViewById(R.id.tvContentContent);
        TextView tvPriceContent = (TextView) contentView.findViewById(R.id.tvPriceContent);
        TextView tvColorContent= (TextView) contentView.findViewById(R.id.tvColorContent);
        TextView tvNoteContent = (TextView) contentView.findViewById(R.id.tvNoteContent);

        tvDimensionsContent.setText(dimensions);
        tvContentContent.setText(content);
        tvPriceContent.setText(price + " €");
        tvColorContent.setText(color);
        tvNoteContent.setText(note);

        super.onViewCreated(view, savedInstanceState);
    }
}
