package com.bohan.android.capstone.Helper.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import androidx.annotation.FloatRange;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.PAINT_FLAGS;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("deprecation")
public class GlideUtils extends BitmapTransformation {


    //Default Value
    private float xPercentage = 0.5f;
    private float yPercentage = 0.5f;

    public GlideUtils(Context context) {
        super(context);
    }

    public GlideUtils(Context context, @FloatRange(from = 0.0, to = 1.0) float xPercentage,
                      @FloatRange(from = 0.0, to = 1.0) float yPercentage) {
        super(context);
        this.xPercentage = xPercentage;
        this.yPercentage = yPercentage;
    }

    public GlideUtils(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap newBitmap, int outWidth, int outHeight) {
        final Bitmap oldBitmap = pool.get(outWidth, outHeight, newBitmap.getConfig() != null
                ? newBitmap.getConfig() : Bitmap.Config.ARGB_8888);
        Bitmap transformed = cropBitmap(oldBitmap, newBitmap, xPercentage, yPercentage,outWidth, outHeight);

        if (oldBitmap != null && oldBitmap != transformed && !pool.put(oldBitmap))
            oldBitmap.recycle();
        return transformed;
    }

    @Override
    public String getId() {
        return "Glide.load.resource.bitmap.x:" + xPercentage + ".y:" + yPercentage;
    }

    /**
     * A potentially expensive operation to crop the given Bitmap so that it fills the given
     * dimensions. This operation is significantly less expensive in terms of memory if a mutable
     * Bitmap with the given dimensions is passed in as well.
     *
     * @param recycled A mutable Bitmap with dimensions width and height that we can load the cropped
     * portion of toCrop into.
     * @param toCrop The Bitmap to resize.
     * @param width The width in pixels of the final Bitmap.
     * @param height The height in pixels of the final Bitmap.
     * @param xPercentage The horizontal percentage of the crop. 0.0f => left, 0.5f => center, 1.0f =>
     * right or anything in between 0 and 1
     * @param yPercentage The vertical percentage of the crop. 0.0f => top, 0.5f => center, 1.0f =>
     * bottom or anything in between 0 and 1
     * @return The resized Bitmap (will be recycled if recycled is not null).
     */
    private static Bitmap cropBitmap(Bitmap oldBitmap, Bitmap newBitmap, float xPercentage, float yPercentage,
                                     int width, int height) {
        //Corner case
        if (newBitmap == null)
            return null;
        else if (newBitmap.getHeight() == height && newBitmap.getWidth() == width)
            return newBitmap;

        // From ImageView/Bitmap.createScaledBitmap.
        final float scaleValue;
        float dx = 0, dy = 0;
        Matrix matrix = new Matrix();

        if (newBitmap.getHeight() * width < height * newBitmap.getWidth()) {
            scaleValue = (float) height / (float) newBitmap.getHeight();
            dx = (width - scaleValue * newBitmap.getWidth());
            dx *= xPercentage;
        }
        else {
            scaleValue = (float) width / (float) newBitmap.getWidth();
            dy = (height - scaleValue * newBitmap.getHeight());
            dy *= yPercentage;
        }

        matrix.setScale(scaleValue, scaleValue);
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

        final Bitmap resultBitmap;
        if (oldBitmap != null)
            resultBitmap = oldBitmap;
        else
            resultBitmap = Bitmap.createBitmap(width, height, getConfig(newBitmap));

        // We don't add or remove alpha, so keep the alpha setting of the Bitmap we were given.
        TransformationUtils.setAlpha(newBitmap, resultBitmap);

        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint(PAINT_FLAGS);
        canvas.drawBitmap(newBitmap, matrix, paint);
        return resultBitmap;
    }

    private static Bitmap.Config getConfig(Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

}
