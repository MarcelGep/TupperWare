package com.tupperware.marcel.tupperware;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Marcel on 14.12.2016.
 */

public class Fragment1_Article extends Fragment {
    private static final String LOG_TAG = Fragment1_Article.class.getSimpleName();
    public static String name= null;
    private String artnr;
    private String description;
    private String picid;
    private String quantity;

    View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {
            artnr = bundle.getString("artnr");
            description = bundle.getString("description");
            quantity = bundle.getString("quantity");
            picid = bundle.getString("picid");
        }

        contentView = inflater.inflate(R.layout.layout_1_article, null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvArtNrContent = (TextView) contentView.findViewById(R.id.tvArtNrContent);
        TextView tvDescriptionContent = (TextView) contentView.findViewById(R.id.tvDescriptionContent);
        TextView tvQuantity = (TextView) contentView.findViewById(R.id.tvQuantity);
        tvArtNrContent.setText(artnr);
        tvDescriptionContent.setText(description);
        tvQuantity.setText("Bestand: " + quantity + " Stück");

        ImageView articleIcon = (ImageView) getActivity().findViewById(R.id.ivArticleIcon);
        int PicId = getResources().getIdentifier("drawable/" + picid, null, getActivity().getPackageName());
        articleIcon.setImageResource(PicId);

        super.onViewCreated(view, savedInstanceState);
    }
}
