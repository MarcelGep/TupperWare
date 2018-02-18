package com.tupperware.marcel.tupperware;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Marcel on 09.12.2016.
 */

public class ArticleListAdapter extends ArrayAdapter<Articles>{

    Context context;
    List<Articles> articleList;
    LayoutInflater inflater;
    SparseBooleanArray SelectedItemsIds;


    public ArticleListAdapter(Context context, int resourceId, List<Articles> articleList) {
        super(context, resourceId, articleList);
        SelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.articleList = articleList;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        TextView description;
        //TextView artnr;
        TextView quantity;
        ImageView articleIcon;
        CheckBox articleChkBox;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.article_list_layout2, null);

            //holder.artnr = (TextView) view.findViewById(R.id.tvArtNr);
            holder.description = (TextView) view.findViewById(R.id.tvDescription);
            holder.quantity = (TextView) view.findViewById(R.id.tvQuantity);
            holder.articleChkBox = (CheckBox) view.findViewById(R.id.item_cbx);

            holder.articleIcon = (ImageView) view.findViewById(R.id.articleIcon);
            holder.articleIcon.setBackgroundResource(R.drawable.listview_selecter);
            view.setTag(holder);

            //holder.articleChkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) context);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        int PicId = context.getResources().getIdentifier("drawable/"
                    + articleList.get(position).getPicId(), null, context.getPackageName());
        holder.articleIcon.setImageResource(PicId);
        holder.description.setText(articleList.get(position).getDescription());
        //holder.artnr.setText(articleList.get(position).getArtnr());
        holder.quantity.setText(articleList.get(position).getQuantity()+"x");
        //holder.articleChkBox.setText(articleList.get(position).getDescription());

        return view;
    }

    public void remove(Articles articles){
        articleList.remove(articles);
        notifyDataSetChanged();
    }

    public List<Articles> getArticles(){
        return articleList;
    }

    public void toggleSelection(int position){
        selectView(position, !SelectedItemsIds.get(position));
    }

    public void removeSelection(){
        SelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value){
        if(value){
            SelectedItemsIds.put(position, value);
        }
        else{
            SelectedItemsIds.delete(position);
        }
    }

    public int getSelectedCount(){
        return SelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds(){
        return SelectedItemsIds;
    }
}
