package com.delcache.hera.helper;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.delcache.hera.R;
import com.delcache.hera.utils.ConstantStore;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.MyLogger;
import com.delcache.hera.utils.StringTool;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

public class ImageHelper {

    protected static MyLogger logger = MyLogger.getLogger();
    private static ImageHelper imageHelper = null;

    private ImageHelper() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(false) //设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .showImageForEmptyUri(R.mipmap.holder_book_image)
                .showImageOnFail(R.mipmap.holder_book_image)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(StringTool.getInstance().getApplication())
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)// 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiskCache(new File(ConstantStore.getInstance().getImageCachePath())))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(StringTool.getInstance().getApplication(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }

    public static ImageHelper getInstance() {
        if (imageHelper == null) {
            imageHelper = new ImageHelper();
        }
        return imageHelper;
    }

    public void showImage(String imageUrl, ImageView imageView) {
        if (!imageUrl.equals(imageView.getTag())) {
            ImageLoader.getInstance().displayImage(imageUrl, imageView, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setTag(imageUrl);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });

        }
    }

}