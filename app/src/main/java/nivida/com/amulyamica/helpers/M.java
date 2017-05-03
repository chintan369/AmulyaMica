package nivida.com.amulyamica.helpers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by Nivida new on 05-Jan-17.
 */

public class M {
    public static Bitmap createContrast(Bitmap src, double value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        //Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return bmOut;
    }

   /* public static Rect searchBitmap(Bitmap smallBmp, Bitmap bigBmp, double tolerance)
    {

        BitmapData smallData =
                smallBmp.LockBits(new Rectangle(0, 0, smallBmp.Width, smallBmp.Height),
                        System.Drawing.Imaging.ImageLockMode.ReadOnly,
                        System.Drawing.Imaging.PixelFormat.Format24bppRgb);
        BitmapData bigData =
                bigBmp.LockBits(new Rectangle(0, 0, bigBmp.Width, bigBmp.Height),
                        System.Drawing.Imaging.ImageLockMode.ReadOnly,
                        System.Drawing.Imaging.PixelFormat.Format24bppRgb);

        int smallStride = smallData.Stride;
        int bigStride = bigData.Stride;

        int bigWidth = bigBmp.getWidth();
        int bigHeight = bigBmp.getHeight() - smallBmp.getHeight() + 1;
        int smallWidth = smallBmp.getWidth() * 3;
        int smallHeight = smallBmp.getHeight();

        Rect location = new Rect();
        int margin = (int)(255 * tolerance);

        unsafe
        {
            byte* pSmall = (byte*)(void*)smallData.Scan0;
            byte* pBig = (byte*)(void*)bigData.Scan0;

            int smallOffset = smallStride - smallBmp.Width * 3;
            int bigOffset = bigStride - bigBmp.Width * 3;

            bool matchFound = true;

            for (int y = 0; y < bigHeight; y++)
            {
                for (int x = 0; x < bigWidth; x++)
                {
                    byte* pBigBackup = pBig;
                    byte* pSmallBackup = pSmall;

                    //Look for the small picture.
                    for (int i = 0; i < smallHeight; i++)
                    {
                        int j = 0;
                        matchFound = true;
                        for (j = 0; j < smallWidth; j++)
                        {
                            //With tolerance: pSmall value should be between margins.
                            int inf = pBig[0] - margin;
                            int sup = pBig[0] + margin;
                            if (sup < pSmall[0] || inf > pSmall[0])
                            {
                                matchFound = false;
                                break;
                            }

                            pBig++;
                            pSmall++;
                        }

                        if (!matchFound) break;

                        //We restore the pointers.
                        pSmall = pSmallBackup;
                        pBig = pBigBackup;

                        //Next rows of the small and big pictures.
                        pSmall += smallStride * (1 + i);
                        pBig += bigStride * (1 + i);
                    }

                    //If match found, we return.
                    if (matchFound)
                    {
                        location.X = x;
                        location.Y = y;
                        location.Width = smallBmp.Width;
                        location.Height = smallBmp.Height;
                        break;
                    }
                    //If no match found, we restore the pointers and continue.
                    else
                    {
                        pBig = pBigBackup;
                        pSmall = pSmallBackup;
                        pBig += 3;
                    }
                }

                if (matchFound) break;

                pBig += bigOffset;
            }
        }

        bigBmp.UnlockBits(bigData);
        smallBmp.UnlockBits(smallData);

        return location;
    }*/
}
