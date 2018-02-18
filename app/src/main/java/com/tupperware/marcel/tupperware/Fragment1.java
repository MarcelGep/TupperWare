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

public class Fragment1 extends Fragment {
    private static final String LOG_TAG = Fragment1.class.getSimpleName();
    public static String name= null;
    private String artnr;
    private String description;
    private String dimensions;
    private String content;
    private String picid;

    View contentView;
    //CatalogDataSource dataSource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {
            artnr = bundle.getString("artnr");
            description = bundle.getString("description");
            dimensions = bundle.getString("dimensions");
            content = bundle.getString("content");
            picid = bundle.getString("picid");
        }

        contentView = inflater.inflate(R.layout.layout_1, null);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnAddArticle = (Button) contentView.findViewById(R.id.btnAddArticle);
        Button btnBack= (Button) contentView.findViewById(R.id.btnBack);
        TextView tvArtNrContent = (TextView) contentView.findViewById(R.id.tvArtNrContent);
        TextView tvDescriptionContent = (TextView) contentView.findViewById(R.id.tvDescriptionContent);
        tvArtNrContent.setText(artnr);
        tvDescriptionContent.setText(description);

        ImageView catalogIcon = (ImageView) getActivity().findViewById(R.id.ivCatalogIcon);
        int PicId = getResources().getIdentifier("drawable/" + picid, null, getActivity().getPackageName());
        catalogIcon.setImageResource(PicId);

        btnAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addArticle = new Intent(getActivity(), NewArticle.class);
                addArticle.putExtra("artnr", artnr);
                addArticle.putExtra("description", description);
                addArticle.putExtra("dimensions", dimensions);
                addArticle.putExtra("content", content);

                startActivity(addArticle);
                getActivity().finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
