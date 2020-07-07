package id.my.avmmartin.mynewsapp.ui.components;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import id.my.avmmartin.mynewsapp.R;
import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.utils.CommonUtils;

class NewsListViewHolder extends RecyclerView.ViewHolder {
    public interface Listener {
        void onItemClick(News news);
        void onCreateContextMenu(ContextMenu menu, News news);
    }

    private Listener listener;

    void setListener(Listener listener) {
        this.listener = listener;
    }

    private ImageView image;
    private TextView title;
    private TextView description;

    private News news;

    void bindData(News data) {
        this.news = data;
    }

    // constructor

    NewsListViewHolder(@NonNull View itemView) {
        super(itemView);

        initComponents();
        setEvents();
    }

    private void initComponents() {
        image = itemView.findViewById(R.id.single_newslist_image);
        title = itemView.findViewById(R.id.single_newslist_title);
        description = itemView.findViewById(R.id.single_newslist_description);
    }

    void loadData() {
        if (news.isOffline()) {
            Picasso
                .get()
                .load(CommonUtils.getInternalFilepath(
                    itemView.getContext(),
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
        description.setText(news.getDescription());
    }

    private void setEvents() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(news);
            }
        });

        itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                listener.onCreateContextMenu(menu, news);
            }
        });
    }
}
