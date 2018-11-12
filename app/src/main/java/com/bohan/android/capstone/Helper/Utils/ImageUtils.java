package com.bohan.android.capstone.Helper.Utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bohan.android.capstone.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Bo Han.
 */
public class ImageUtils {

    /**
     * External image loading function with custom image positioning (top crop)
     *
     * @param view Target imageView
     * @param url Image url
     */
    public static void imageWithCropOnTop(ImageView view, String imageUrl) {

        Glide.with(view.getContext())
                .load(imageUrl)
                .crossFade()
                .transform(new GlideUtils(view.getContext(), 0, 0))
                .error(R.drawable.placeholder_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * External image loading function which handles ProgressBar as loading placeholder
     *
     * @param view Target imageView
     * @param url Image url
     * @param progressBar ProgressBar view which shown while image loading
     */
    public static void fetchingImageWithProgress(ProgressBar progressBar, ImageView view, String imageurl) {

        Glide.with(view.getContext())
                .load(imageurl)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.placeholder_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    /**
     * External image loading function which runs custom callback on any loading result
     *
     * @param view Target imageView
     * @param url Image url
     * @param callback Callback which runs when image loading finished with any result
     */
    public static void imageWithCallback(ImageCallback callback, ImageView view, String imageUrl){

        Glide.with(view.getContext())
                .load(imageUrl)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        callback.onFinish(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        callback.onFinish(true);
                        return false;
                    }
                })
                .error(R.drawable.placeholder_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    interface ImageCallback {

        void onFinish(boolean successful);
    }
}
