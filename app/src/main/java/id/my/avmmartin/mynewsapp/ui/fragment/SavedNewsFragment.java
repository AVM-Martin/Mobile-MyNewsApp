package id.my.avmmartin.mynewsapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.my.avmmartin.mynewsapp.R;
import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.ui.components.OfflineNewsListAdapter;

public class SavedNewsFragment extends Fragment {
    public interface Listener {
        void onNewsClick(News news);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private RecyclerView newsList;
    private OfflineNewsListAdapter adapter;

    public void reloadList() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    // overridden method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_saved, container, false);

        newsList = view.findViewById(R.id.fragment_newssaved_newslist);

        adapter = new OfflineNewsListAdapter(getContext());
        adapter.setListener(listener);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList.setAdapter(adapter);

        return view;
    }

    // constructor

    public SavedNewsFragment() {
        // required
    }
}
