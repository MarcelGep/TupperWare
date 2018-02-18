package com.tupperware.marcel.tupperware;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marcel on 11.12.2016.
 */

public class CatalogListAdapter extends ArrayAdapter<Catalog> {
    public static final String LOG_TAG = CatalogListAdapter.class.getSimpleName();

    Context context;
    List<Catalog> catalogList;

    public CatalogListAdapter(Context context, int name,  int artnr, List<Catalog> catalogList) {
        super(context, R.layout.catalog_list_layout, R.id.tvArtNr, catalogList);
        this.context = context;
        this.catalogList = catalogList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.catalog_list_layout, parent, false);
        }

        Catalog catalog = catalogList.get(position);

        int PicId = context.getResources().getIdentifier("drawable/" + catalog.getPicId(), null, context.getPackageName());

        ImageView imageView = (ImageView) itemView.findViewById(R.id.catalogIcon);
        imageView.setImageResource(PicId);

        TextView tvDescription = (TextView) itemView.findViewById(R.id.tvArtikelname);
        tvDescription.setText(catalog.getDescription());

        TextView tvArtnr = (TextView) itemView.findViewById(R.id.tvArtNr);
        tvArtnr.setText(catalog.getArtnr());

        return itemView;
    }
}
