package id.my.avmmartin.mynewsapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.my.avmmartin.mynewsapp.R;
import id.my.avmmartin.mynewsapp.data.DataManager;
import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.ui.components.OnlineNewsListAdapter;
import id.my.avmmartin.mynewsapp.utils.OnlineDataLoaderUtils;

public class SearchNewsFragment extends Fragment {
    public interface Listener {
        void onNewsClick(News news);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private EditText etSearch;
    private RecyclerView newsList;
    private OnlineNewsListAdapter adapter;

    public void reloadList() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void fetchOnlineData() {
        OnlineDataLoaderUtils loaderUtils = new OnlineDataLoaderUtils(getContext()) {
            @Override
            public void onPostExecute() {
                super.onPostExecute();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorExecute() {
                super.onErrorExecute();
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        };
        String query = etSearch.getText().toString().trim();

        if (query.length() > 0) {
            DataManager.getInstance(getContext()).fetchNewsByQuery(loaderUtils, query);
        }
    }

    // overridden method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_search, container, false);

        etSearch = view.findViewById(R.id.fragment_newssearch_et_search);
        newsList = view.findViewById(R.id.fragment_newssearch_newslist);

        adapter = new OnlineNewsListAdapter(getContext());
        adapter.setListener(listener);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList.setAdapter(adapter);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputManager = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                    );

                    fetchOnlineData();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    // constructor

    public SearchNewsFragment() {
        // required
    }
}
