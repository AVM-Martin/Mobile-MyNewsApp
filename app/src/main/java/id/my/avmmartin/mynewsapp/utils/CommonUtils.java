package id.my.avmmartin.mynewsapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class CommonUtils {
    public static long getCurrentEpochTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static File getInternalFilepath(Context context, String filepath) {
        return new File(context.getFilesDir(), filepath);
    }

    public static String saveImage(final Context context, String url) {
        final String filename = Long.toString(getCurrentEpochTime());

        Picasso
            .get()
            .load(url)
            .into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    File filepath = getInternalFilepath(context, filename);

                    try (FileOutputStream out = new FileOutputStream(filepath)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();

                    } catch (FileNotFoundException e) {
                        // unhandled
                    } catch (IOException e) {
                        // unhandled
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    // none
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    // none
                }
            });

        return filename;
    }

    // constructor

    private CommonUtils() {
        // none
    }
}
