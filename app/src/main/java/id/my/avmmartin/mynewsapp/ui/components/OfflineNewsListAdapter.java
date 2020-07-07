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
import id.my.avmmartin.mynewsapp.ui.fragment.SavedNewsFragment;

public class OfflineNewsListAdapter extends RecyclerView.Adapter<NewsListViewHolder> {
    private NewsListViewHolder.Listener holderListener;
    private SavedNewsFragment.Listener listener;

    public void setListener(SavedNewsFragment.Listener listener) {
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
        holder.bindData(DataManager.getInstance(context).getSavedNewsByPosition(position));
        holder.setListener(holderListener);
        holder.loadData();
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance(context).getSavedNewsSize();
    }

    // constructor

    private Context context;

    public OfflineNewsListAdapter(final Context context) {
        this.context = context;

        holderListener = new NewsListViewHolder.Listener() {
            @Override
            public void onItemClick(News news) {
                listener.onNewsClick(news);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, final News news) { ;
                MenuItem delete = menu.add(Menu.NONE, 1, 1, R.string.delete);

                delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DataManager.getInstance(context).unsaveNewsById(news.getId());
                        notifyDataSetChanged();
                        return true;
                    }
                });
            }
        };
    }
}
