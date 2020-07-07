package id.my.avmmartin.mynewsapp.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.my.avmmartin.mynewsapp.R;
import id.my.avmmartin.mynewsapp.data.DataManager;
import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.utils.CommonUtils;
import id.my.avmmartin.mynewsapp.utils.Constants;

public class NewsDetailActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView title;
    private ImageView image;
    private TextView content;
    private Button save;
    private Button readMore;

    private News news;

    // events

    private void onSaveNewsClick() {
        News saveNews = news.copy();

        String filepath = CommonUtils.saveImage(NewsDetailActivity.this, saveNews.getImageUrl());
        saveNews.setOffline(filepath);

        long id = DataManager
            .getInstance(NewsDetailActivity.this)
            .saveNews(saveNews);

        news = DataManager
            .getInstance(NewsDetailActivity.this)
            .getSavedNewsById(id);

        save.setText(R.string.delete);
    }

    private void onDeleteNewsClick() {
        news.unsetOffline();

        DataManager
            .getInstance(NewsDetailActivity.this)
            .unsaveNewsById(news.getId());

        save.setText(R.string.save);
    }

    private void onReadMoreClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(news.getUrl()));
        startActivity(intent);
    }

    // overridden method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initComponents();
        loadData();
        setEvents();
    }

    private void initComponents() {
        actionBar = getSupportActionBar();
        title = findViewById(R.id.activity_newsdetail_title);
        image = findViewById(R.id.activity_newsdetail_image);
        content = findViewById(R.id.activity_newsdetail_content);
        save = findViewById(R.id.activity_newsdetail_btn_save);
        readMore = findViewById(R.id.activity_newsdetail_btn_readmore);

        boolean is_saved = getIntent().getBooleanExtra(Constants.INTENT_IS_SAVED, false);
        int id = getIntent().getIntExtra(Constants.INTENT_NEWS_ID, -1);

        if (is_saved) {
            news = DataManager
                .getInstance(NewsDetailActivity.this)
                .getSavedNewsById(id);
        } else {
            news = DataManager
                .getInstance(NewsDetailActivity.this)
                .getOnlineNewsById(id);
        }
    }

    private void loadData() {
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (news.isOffline()) {
            Picasso
                .get()
                .load(CommonUtils.getInternalFilepath(
                    NewsDetailActivity.this,
                    news.getImageFilepath())
                )
                .into(image);
        } else {
            Picasso
                .get()
                .load(news.getImageUrl())
                .into(image);
        }

        title.setText(news.getTitle());
        content.setText(news.getContent());

        if (news.isOffline()) {
            save.setText(R.string.delete);
        } else {
            save.setText(R.string.save);
        }
    }

    private void setEvents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news.isOffline()) {
                    onDeleteNewsClick();
                } else {
                    onSaveNewsClick();
                }
            }
        });

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReadMoreClick();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
