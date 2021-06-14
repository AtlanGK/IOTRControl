//package com.autohome.iotrcontrol.view;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.autohome.videoimageloader.core.DisplayImageOptions;
//import com.autohome.videoimageloader.core.VideoImageLoader;
//import com.autohome.videoimageloader.core.assist.FailReason;
//import com.autohome.videoimageloader.core.listener.ImageLoadingListener;
//
//import java.io.File;
//
//
//@SuppressLint("AppCompatCustomView")
//public class IOTImageView extends ImageView {
//	private static final String TAG = IOTImageView.class.getSimpleName();
//	private Context mContext;
//
//	/** 图片的URI标识： (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")*/
//	private String mUri;
//
//	public String getUri() {
//		return mUri;
//	}
//
//	/** 图片日志 **/
//	private ImageInfo mImageInfo;
//
//	/** 图片加载失败的监听器 */
//	private ImageLoadFailListener mImageLoadFailListener;
//	/** 图片加载开始的监听器 */
//	private ImageLoadStartListener mLoadStartListener;
//	/** 图片加载完成监听器 */
//	private ImageLoadCompletedListener mImageLoadCompletedListener;
//
//	/**
//	 * 构造函数
//	 * @param context
//	 * @param attrs
//	 * @param defStyle
//	 */
//	public IOTImageView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		mContext = context;
//	}
//
//	/**
//	 * 构造函数
//	 * @param context
//	 * @param attrs
//	 */
//	public IOTImageView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		mContext = context;
//	}
//
//	/**
//	 *
//	 * @param context
//	 */
//	public IOTImageView(Context context) {
//		super(context);
//		mContext = context;
//	}
//
//	/**
//	 * 加载网络图片
//	 * @param url
//	 */
//	public void setImageUrl(String url) {
//		setImageUrl(url, null, null);
//	}
//
//	/**
//	 * 加载网络图片
//	 *
//	 * @param url
//	 * @param imageInfo 图片信息
//	 */
//	public void setImageUrl(String url, ImageInfo imageInfo) {
//		setImageUrl(url, null, imageInfo);
//	}
//
//	/**
//	 * 加载网络图片
//	 *
//	 * @param url
//	 */
//	public void setImageUrl(String url, DisplayImageOptions options) {
//		setImageUrl(url, options, null);
//	}
//
//
//
//	/**
//	 * 加载网络图片
//	 *
//	 * @param url
//	 * @param imageInfo 图片错误、性能上报时，使用的扩展信息字段
//	 */
//	public void setImageUrl(String url, DisplayImageOptions options, ImageInfo imageInfo) {
//		if (TextUtils.isEmpty(url)) {
//			return;
//		}
//		mUri = url;
//		mImageInfo = imageInfo;
//		displayImage(options);
//	}
//
//	/**
//	 * 加载本地图片
//	 * @param imagePath
//	 */
//	public void setLocalImagePath(String imagePath) {
//		setLocalImagePath(imagePath,null);
//	}
//
//	/**
//	 * 加载本地图片
//	 * @param imagePath
//	 */
//	public void setLocalImagePath(String imagePath, DisplayImageOptions options) {
//		if (TextUtils.isEmpty(imagePath)) {
//			return;
//		}
//		File file = new File(imagePath);
//		if (file.exists()) {
//			mUri = "file://" + imagePath;
//		} else {
//			return;
//		}
//		displayImage(options);
//	}
//
//	/**
//	 * 开始加载图片
//	 * @param options
//	 */
//	private void displayImage(DisplayImageOptions options) {
//		if (TextUtils.isEmpty(mUri)) {
//			return;
//		}
//		if (!VideoImageLoader.getInstance().isInited()) {
//			return;
//		}
//		IOTImageView.this.setTag(mUri);
////		AHImageView.this.setTag(AHImageConst.KEY_EXTRAINFO_NAME, mImageInfo != null ? mImageInfo.extrainfo : "");
////		AHImageView.this.setTag(AHImageConst.KEY_SOURCEURL_NAME, mImageInfo != null ? mImageInfo.sourceurl : "");
//
//		VideoImageLoader.getInstance().displayImage(mUri,this,options,mImageLoadingListener);
//	}
//
//	private ImageLoadingListener mImageLoadingListener = new ImageLoadingListener() {
//
//		@Override
//		public void onLoadingStarted(String imageUri, View view) {
//		    if(mLoadStartListener!=null)
//		        mLoadStartListener.onLoadStarted();
//		}
//
//		@Override
//		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//			if (mImageLoadFailListener != null) {
//				mImageLoadFailListener.onLoadFail();
//			}
//		}
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//			if (loadedImage != null) {
//				if (mImageLoadCompletedListener != null) {
//					mImageLoadCompletedListener.onLoadCompleted(loadedImage);
//				}
//			} else {
//				if (mImageLoadFailListener != null) {
//					mImageLoadFailListener.onLoadFail();
//				}
//			}
//		}
//
//		@Override
//		public void onLoadingCancelled(String imageUri, View view) {
//
//		}
//
//	};
//
//	/**
//	 * 设置默认的图片资源
//	 * @param resid
//	 */
//	public void setDefaultImage(int resid) {
//		setBackgroundResource(resid);
//	}
//
//	/**
//	 * 设置默认的图片资源
//	 * @param drawable
//	 */
//	public void setDefaultImage(Drawable drawable) {
//		setImageDrawable(drawable);
//	}
//
////	/**
////	 * 手动设置加载网络图片时图片剪裁的宽高
////	 *
////	 * @param loadImageWidth
////	 */
////	public void setLoadImageWidth(int loadImageWidth) {
////		this.loadImageWidth = loadImageWidth;
////	}
//
////	/**
////	 * 手动设置加载网络图片时图片剪裁的宽高
////	 *
////	 * @param loadImageHeight
////	 */
////	public void setLoadImageHeight(int loadImageHeight) {
////		this.loadImageHeight = loadImageHeight;
////	}
//
//	/**
//     * 图片加载开始回调
//     */
//    public static interface ImageLoadStartListener {
//    	/**
//    	 * 图片加载开始时回调该方法
//    	 */
//        public void onLoadStarted();
//    }
//
//
//	/**
//	 * 图片加载完成回调
//	 */
//	public static interface ImageLoadCompletedListener {
//		/**
//		 * 图片加载完成后的回调该方法
//		 * @param bm
//		 */
//		public void onLoadCompleted(Bitmap bm);
//	}
//
//	/**
//	 * 图片加载失败回调
//	 */
//	public static interface ImageLoadFailListener {
//		/**
//		 * 图片加载失败回调该方法
//		 */
//		public void onLoadFail();
//	}
//
//	/**
//	 * 设置图片加载开始回调
//	 * @param loadStartListener
//	 */
//	public void setImageLoadStartListener(ImageLoadStartListener loadStartListener) {
//	    this.mLoadStartListener = loadStartListener;
//	}
//
//	/**
//	 * 设置图片加载完成回调
//	 * @param loadImageCompletedListener
//	 */
//	public void setImageLoadCompletedListener(ImageLoadCompletedListener loadImageCompletedListener) {
//		this.mImageLoadCompletedListener = loadImageCompletedListener;
//	}
//
//	/**
//	 * 设置图片加载失败回调
//	 * @param mLoadImageFailListener
//	 */
//	public void setImageLoadFailListener(ImageLoadFailListener mLoadImageFailListener) {
//		this.mImageLoadFailListener = mLoadImageFailListener;
//	}
//
//	@Override
//	protected void drawableStateChanged() {
//		super.drawableStateChanged();
//		invalidate();
//	}
//
//	@Override
//	protected void onDetachedFromWindow() {
//		if (mAutoCancelDisplayTask) {
//			VideoImageLoader.getInstance().cancelDisplayTask(this);
//		}
////		setImageBitmap(null);
//		super.onDetachedFromWindow();
//	}
//
//	private boolean mAutoCancelDisplayTask = true;
//
//	public void setAutoCancelDisplayTask(boolean autoCancelDisplayTask) {
//		mAutoCancelDisplayTask = autoCancelDisplayTask;
//	}
//}
