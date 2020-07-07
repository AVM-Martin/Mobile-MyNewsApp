package id.my.avmmartin.mynewsapp.data.manager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import id.my.avmmartin.mynewsapp.data.model.News;
import id.my.avmmartin.mynewsapp.utils.Constants;
import id.my.avmmartin.mynewsapp.utils.OnlineDataLoaderUtils;

public class OnlineNewsManager extends Vector<News> {
    private static final String TITLE = "title";
    private static final String IMAGE_URL = "urlToImage";
    private static final String DESCRIPTION = "description";
    private static final String CONTENT = "content";
    private static final String URL = "url";

    public void fetchOnlineData(final OnlineDataLoaderUtils loaderUtils, final String query) {
        loaderUtils.onPreExecute();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            String.format(Constants.JSON_URL, query),
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray news = response.getJSONArray("articles");

                        clear();

                        for (int i = 0; i < news.length(); i++) {
                            try {
                                add(toObject(i, news.getJSONObject(i)));
                            } catch (Exception e) {
                                throw e;
                            }
                        }

                    } catch (JSONException e) {
                        //
                    } finally {
                        requestQueue.getCache().clear();
                        loaderUtils.onSuccessExecute();
                    }

                    loaderUtils.onPostExecute();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    loaderUtils.onErrorExecute();
                }
            }
        );

        requestQueue.add(jsonObjectRequest);
    }

    // helper

    private News toObject(int index, JSONObject jsonObject) throws JSONException {
        return new News(
            index,
            jsonObject.getString(TITLE),
            jsonObject.getString(IMAGE_URL),
            jsonObject.getString(DESCRIPTION),
            jsonObject.getString(CONTENT),
            jsonObject.getString(URL)
        );
    }

    // constructor

    private RequestQueue requestQueue;

    public OnlineNewsManager(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;

        clear();
    }
}
