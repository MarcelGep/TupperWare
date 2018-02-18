package com.tupperware.marcel.tupperware;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListArticles extends AppCompatActivity {

    public static final String LOG_TAG = ListArticles.class.getSimpleName();

    ArticleDataSource datasource;
    ArticleListAdapter articleListAdapter;

    List<Articles> articleList = new ArrayList<>();

    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_articles);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        datasource = new ArticleDataSource(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings)
            return true;

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy(){
        super.onDestroy();
        datasource.closeDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.openDB();
        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllArticles();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void showAllArticles() {
        try{
            articleList.clear();
            articleList = datasource.getAllArticles();
        }
        catch (Exception e){
            Toast.makeText(this, "Error showAllArticles!", Toast.LENGTH_SHORT).show();
        }

        //ArrayAdapter<Articles> listadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articleList);

        articleListAdapter = new ArticleListAdapter(this, R.layout.article_list_layout2, articleList);
        final ListView articleListView = (ListView) findViewById(R.id.lvArticleList);
        articleListView.setAdapter(articleListAdapter);

        articleListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        articleListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = articleListView.getCheckedItemCount();
                mode.setTitle(checkedCount + " Artikel ausgewählt");
                articleListAdapter.toggleSelection(position);
                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_articlelist, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                MenuItem item = menu.findItem(R.id.cab_change);
                if (articleListView.getCheckedItemCount() == 1) {
                    item.setVisible(true);
                } else {
                    item.setVisible(false);
                }
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                SparseBooleanArray selected = articleListAdapter.getSelectedIds();
                switch (item.getItemId()) {
                    case R.id.cab_delete:
                        for (int i = 0; i < selected.size(); i++) {
                            if(selected.valueAt(i)){
                                Articles article = articleListAdapter.getItem(selected.keyAt(i));
                                datasource.deleteArticle(article);
                            }
                        }
                        Toast.makeText(getApplicationContext(), selected.size() + " Artikel gelöscht", Toast.LENGTH_SHORT).show();
                        showAllArticles();
                        mode.finish();
                        return true;

                    case R.id.cab_change:
                        for (int i = 0; i < selected.size(); i++) {
                            boolean isChecked = selected.valueAt(i);
                            if (isChecked) {
                                int postitionInListView = selected.keyAt(i);
                                Articles article = (Articles) articleListView.getItemAtPosition(postitionInListView);
                                AlertDialog editArticleDialog = createEditArticleDialog(article);
                                editArticleDialog.show();
                            }
                        }
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                articleListAdapter.removeSelection();
            }
        });

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Articles article = (Articles) adapterView.getItemAtPosition(position);
                Intent showArticleInfo = new Intent(getApplicationContext(), ArticleInfo.class);
                showArticleInfo.putExtra("artnr", article.getArtnr());
                showArticleInfo.putExtra("description", article.getDescription());
                showArticleInfo.putExtra("dimensions", article.getDimensions());
                showArticleInfo.putExtra("content", article.getContent());
                showArticleInfo.putExtra("price", article.getPrice());
                showArticleInfo.putExtra("color", article.getColor());
                showArticleInfo.putExtra("note", article.getNote());
                showArticleInfo.putExtra("quantity", article.getQuantity());
                showArticleInfo.putExtra("picid", article.getPicId());

                startActivity(showArticleInfo);
            }
        });
    }

    private AlertDialog createEditArticleDialog(final Articles article){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogsView = inflater.inflate(R.layout.dialog_edit_article, null);

        final EditText etNewDimensions = (EditText) dialogsView.findViewById(R.id.et_new_dimensions);
        etNewDimensions.setText(String.valueOf(article.getDimensions()));

        final EditText etNewContent = (EditText) dialogsView.findViewById(R.id.et_new_content);
        etNewContent.setText(String.valueOf(article.getContent()));

        final EditText etNewPrice = (EditText) dialogsView.findViewById(R.id.et_new_price);
        etNewPrice.setText(String.valueOf(article.getPrice()));

        final EditText etNewColor = (EditText) dialogsView.findViewById(R.id.et_new_color);
        etNewColor.setText(String.valueOf(article.getColor()));

        final EditText etNewQuantity = (EditText) dialogsView.findViewById(R.id.et_new_quantity);
        etNewQuantity.setText(String.valueOf(article.getQuantity()));

        final EditText etNewNote = (EditText) dialogsView.findViewById(R.id.et_new_note);
        etNewNote.setText(String.valueOf(article.getNote()));

        builder.setView(dialogsView)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String dimensions = etNewDimensions.getText().toString();
                        String content = etNewContent.getText().toString();
                        String price = etNewPrice.getText().toString();
                        String color = etNewColor.getText().toString();
                        String quantity = etNewQuantity.getText().toString();
                        String note = etNewNote.getText().toString();

                        if ((TextUtils.isEmpty(dimensions)) || (TextUtils.isEmpty(content)) || (TextUtils.isEmpty(price)) || (TextUtils.isEmpty(color)) || (TextUtils.isEmpty(quantity)) || (TextUtils.isEmpty(note))) {
                            Log.d(LOG_TAG, "Ein Eintrag enthielt keinen Text. Daher Abbruch der Änderung.");
                            Toast.makeText(getApplicationContext(), "Eingabe fehlerhaft!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // An dieser Stelle schreiben wir die geänderten Daten in die SQLite Datenbank
                        //datasource.openDB();
                        Articles updateArticle = datasource.updateArticle(article.getId(), dimensions, content, price, color, quantity, note);
                        Log.d(LOG_TAG, "Alter Eintrag - ID: " + article.getId() + "\n" + article.getArticleInfo());
                        Log.d(LOG_TAG, "Neuer Eintrag - ID: " + updateArticle.getId() + "\n" + updateArticle.getArticleInfo());
                        //datasource.closeDB();
                        Toast.makeText(getApplicationContext(), "Eintrag geändert!", Toast.LENGTH_SHORT).show();

                        showAllArticles();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(R.string.dialog_button_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
