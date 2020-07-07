package id.my.avmmartin.mynewsapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import id.my.avmmartin.mynewsapp.R;
import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.ui.fragment.MenuListFragment;
import id.my.avmmartin.mynewsapp.ui.fragment.SavedNewsFragment;
import id.my.avmmartin.mynewsapp.ui.fragment.SearchNewsFragment;
import id.my.avmmartin.mynewsapp.utils.Constants;

public class NewsListActivity extends AppCompatActivity {
    private MenuListFragment menuListFragment;
    private SearchNewsFragment searchNewsFragment;
    private SavedNewsFragment savedNewsFragment;

    private FragmentManager fragmentManager;

    // events

    private void loadMenuFragment() {
        fragmentManager
            .beginTransaction()
            .replace(R.id.activity_newslist_fragment_menulist, menuListFragment)
            .disallowAddToBackStack()
            .commit();
    }

    public void onSearchNewsClick() {
        fragmentManager
            .beginTransaction()
            .replace(R.id.activity_newslist_fragment_container, searchNewsFragment)
            .disallowAddToBackStack()
            .commit();
    }

    public void onSavedMenuClick() {
        fragmentManager
            .beginTransaction()
            .replace(R.id.activity_newslist_fragment_container, savedNewsFragment)
            .disallowAddToBackStack()
            .commit();
    }

    public void onNewsClick(News news) {
        Intent intent = new Intent(NewsListActivity.this, NewsDetailActivity.class);
        intent.putExtra(Constants.INTENT_IS_SAVED, news.isOffline());
        intent.putExtra(Constants.INTENT_NEWS_ID, news.getId());
        startActivity(intent);
    }

    // overridden method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initComponents();
        loadData();
        setEvents();
    }

    private void initComponents() {
        menuListFragment = new MenuListFragment();
        searchNewsFragment = new SearchNewsFragment();
        savedNewsFragment = new SavedNewsFragment();

        fragmentManager = getSupportFragmentManager();
    }

    private void loadData() {
        menuListFragment.setListener(new MenuListFragment.Listener() {
            @Override
            public void onSearchNewsClick() {
                NewsListActivity.this.onSearchNewsClick();
            }

            @Override
            public void onSavedMenuClick() {
                NewsListActivity.this.onSavedMenuClick();
            }
        });
        searchNewsFragment.setListener(new SearchNewsFragment.Listener() {
            @Override
            public void onNewsClick(News news) {
                NewsListActivity.this.onNewsClick(news);
            }
        });
        savedNewsFragment.setListener(new SavedNewsFragment.Listener() {
            @Override
            public void onNewsClick(News news) {
                NewsListActivity.this.onNewsClick(news);
            }
        });

        loadMenuFragment();
    }

    private void setEvents() {
        // none
    }

    @Override
    protected void onResume() {
        super.onResume();

        searchNewsFragment.reloadList();
        savedNewsFragment.reloadList();
    }
}
