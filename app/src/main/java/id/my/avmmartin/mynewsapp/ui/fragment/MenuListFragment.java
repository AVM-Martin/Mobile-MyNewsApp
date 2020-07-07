package id.my.avmmartin.mynewsapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.my.avmmartin.mynewsapp.R;

public class MenuListFragment extends Fragment {
    public interface Listener {
        void onSearchNewsClick();
        void onSavedMenuClick();
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private TextView searchNewsTab;
    private TextView savedNewsTab;

    private void clearSelection() {
        searchNewsTab.setTextAppearance(R.style.MenuList);
        searchNewsTab.setBackgroundResource(R.color.transparent);
        savedNewsTab.setTextAppearance(R.style.MenuList);
        savedNewsTab.setBackgroundResource(R.color.transparent);
    }

    private void onSearchNewsClick() {
        clearSelection();

        searchNewsTab.setTextAppearance(R.style.MenuList_Selected);
        searchNewsTab.setBackgroundResource(R.color.blue);

        listener.onSearchNewsClick();
    }

    private void onSavedMenuClick() {
        clearSelection();

        savedNewsTab.setTextAppearance(R.style.MenuList_Selected);
        savedNewsTab.setBackgroundResource(R.color.blue);

        listener.onSavedMenuClick();
    }

    // overridden method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);

        searchNewsTab = view.findViewById(R.id.fragment_menulist_search_news);
        savedNewsTab = view.findViewById(R.id.fragment_menulist_saved_news);

        searchNewsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchNewsClick();
            }
        });
        savedNewsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSavedMenuClick();
            }
        });

        onSearchNewsClick();

        return view;
    }

    // constructor

    public MenuListFragment() {
        // required
    }
}
