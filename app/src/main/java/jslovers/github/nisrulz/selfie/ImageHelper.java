package jslovers.github.nisrulz.selfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

public class ImageHelper {

  public static void setImageFromFilePath(Context context, String imagePath, ImageView imageView,
      int targetW, int targetH) {
    // Get the dimensions of the bitmap
    BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
    bmpOptions.inJustDecodeBounds = true;
    int photoW = bmpOptions.outWidth;
    int photoH = bmpOptions.outHeight;

    // determine scale factor
    int scaleFactor = Math.max(photoW / targetW, photoH / targetH);

    // decode the image file into a Bitmap
    bmpOptions.inJustDecodeBounds = false;
    bmpOptions.inSampleSize = scaleFactor;

    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

    // Handle when the path is not valid
    if (bitmap == null) {
      bitmap = BitmapFactory.decodeResource(context.getResources(),
          R.drawable.ic_photo_camera_white_24dp);
      imageView.setBackgroundColor(
          ContextCompat.getColor(context, android.R.color.darker_gray));
    }
    imageView.setImageBitmap(bitmap);
    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
  }

  public static void setImageFromFilePath(Context context, String imagePath, ImageView imageView) {
    setImageFromFilePath(context, imagePath, imageView, 120, 160);
  }
}
