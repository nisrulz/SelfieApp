package jslovers.github.nisrulz.selfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageHelper {

  public static void setImageFromFilePath(String imagePath, ImageView imageView, int targetW,
      int targetH) {
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
    imageView.setImageBitmap(bitmap);
    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
  }

  public static void setImageFromFilePath(String imagePath, ImageView imageView) {
    setImageFromFilePath(imagePath, imageView, 120, 160);
  }
}
