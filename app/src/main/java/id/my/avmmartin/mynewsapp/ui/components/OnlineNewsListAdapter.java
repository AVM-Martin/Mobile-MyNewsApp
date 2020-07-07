package id.my.avmmartin.mynewsapp.ui.components;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.my.avmmartin.mynewsapp.R;
import id.my.avmmartin.mynewsapp.data.DataManager;
import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.ui.fragment.SearchNewsFragment;
import id.my.avmmartin.mynewsapp.utils.CommonUtils;

public class OnlineNewsListAdapter extends RecyclerView.Adapter<NewsListViewHolder> {
    private NewsListViewHolder.Listener holderListener;
    private SearchNewsFragment.Listener listener;

    public void setListener(SearchNewsFragment.Listener listener) {
        this.listener = listener;
    }

    // overridden method

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_newslist, parent, false);

        return new NewsListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {
        holder.bindData(DataManager.getInstance(context).getOnlineNewsByPosition(position));
        holder.setListener(holderListener);
        holder.loadData();
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance(context).getOnlineNewsSize();
    }

    // constructor

    private Context context;

    public OnlineNewsListAdapter(final Context context) {
        this.context = context;

        holderListener = new NewsListViewHolder.Listener() {
            @Override
            public void onItemClick(News news) {
                listener.onNewsClick(news);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, final News news) {
                MenuItem save = menu.add(Menu.NONE, 1, 1, R.string.save);

                save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        News saveNews = news.copy();

                        String filepath = CommonUtils.saveImage(context, saveNews.getImageUrl());
                        saveNews.setOffline(filepath);

                        DataManager.getInstance(context).saveNews(saveNews);
                        return true;
                    }
                });
            }
        };
    }
}
