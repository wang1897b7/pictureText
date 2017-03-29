package com.wsd.text.pict_can.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.ns.mutiphotochoser.constant.Constant;
import com.wsd.text.pict_can.BuildConfig;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static com.wsd.text.pict_can.MyApplication.CHOOSE_PICTURE_REQUEST_CODE2;


/**
 * Created by frank on 15/10/26.
 */
public class ChoosePictureUtil {

    public static String SDPATH = Environment.getExternalStorageDirectory() + File.separator;
    public static String PICTURE_NAME = "image_cam.jpg";
    private static final String DATE_FORMAT_FOR_PIC_PATH = "yyyyMMdd_HHmmss";
    private final int REQUEST_CODE_GALLERY = 1001;

    private Context mContext;

    public ChoosePictureUtil(Context context) {
        mContext = context;
        SDPATH = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator;
    }

    public void showDialog(final int requestCode, final int requestCode2) {
        if (isSDCardStateOn() && !isSDCardReadOnly()) {
            new AlertDialog.Builder(mContext)
                    .setItems(mContext.getResources().getStringArray(R.array.crop_or_original_array), new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        if (CheckUtil.sdkVersion()) {
                                            TakePhoto tk = new TakePhoto(mContext, requestCode);
                                            tk.checkPermission();
                                        } else {
                                            takePhoto(requestCode);
                                        }

                                    } else if (which == 1) {
                                        choosePhoto(requestCode2);
                                    }
                                }
                            })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            CheckUtil.showShortMessage(mContext, "SD卡不可用");
        }

    }

    public void takePhoto(int requestCode) {
        if (checkCameraHardware(mContext)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDPATH, PICTURE_NAME)));
                    ((Activity) mContext).startActivityForResult(intent, requestCode);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, new File(SDPATH, PICTURE_NAME).getAbsolutePath());
                    Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    ((Activity) mContext).startActivityForResult(intent, requestCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            CheckUtil.showShortMessage(mContext, "相机不可用");
        }
    }

    public void choosePhoto(int requestCode) {
//        Intent intent;
//        if (Build.VERSION.SDK_INT >= 19) {
//            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        } else {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//        }
//        FunctionConfig config = new FunctionConfig.Builder()
//                .setMutiSelectMaxSize(5)
//                .build();
//        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, mOnHanlderResultCallback);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", getFile(app_name));
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//
//            intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 5);
//            ((Activity) mContext).startActivityForResult(intent, requestCode);
//
//        }
        Intent intent = new Intent("com.wsd.text.pict_can.action.CHOSE_PHOTOS");
        //指定图片最大选择数
        intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 5);
        ((Activity) mContext).startActivityForResult(intent, requestCode);

    }

    private File getFile(String director) {
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + director);
        return file;
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
               // mPhotoList.addAll(resultList);
                ArrayList<String> mStr =new ArrayList<>();
                for(int i=0;i<resultList.size();i++){
                    mStr.add(resultList.get(i).getPhotoPath());
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putStringArrayListExtra("photoList", mStr);
//                ((Activity) mContext).setResult(
//                        CHOOSE_PICTURE_REQUEST_CODE2,intent);
             //   ((Activity) mContext).startActivityForResult(intent, CHOOSE_PICTURE_REQUEST_CODE2);
                ((Activity) mContext).startActivityForResult(intent, CHOOSE_PICTURE_REQUEST_CODE2);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    public void cropPic(String fileUri, String cropPath, int requestCode) {

        //call the standard crop action intent (the user device may not support it)
        Intent intent = new Intent("com.android.camera.action.CROP");// 裁剪意图
        //indicate image type and Uri
        intent.setDataAndType(Uri.fromFile(new File(fileUri)), "image/*");// 格式
        //set crop properties
        intent.putExtra("crop", "true");// 发送裁剪信号
        //indicate aspect of desired crop
        intent.putExtra("aspectX", 1);// X方向上的比例
        intent.putExtra("aspectY", 1);// Y方向上的比例
        intent.putExtra("scale", true);// 是否保留比例
        //indicate output X and Y
        intent.putExtra("outputX", 150);// 裁剪区的宽
        intent.putExtra("outputY", 150);// 裁剪区的高
        //retrieve data on return
        intent.putExtra("return-data", false);// 是否将数据保留在Bitmap中返回
        intent.putExtra("circleCrop", false);// 圆形裁剪区域
        intent.putExtra("noFaceDetection", false); // 不检测人脸
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        /*ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.ImageColumns.DATA, cropPath);
        Uri cropUri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);*/

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cropPath)));// 将URI指向相应的file:///

        //start the activity - we handle returning in onActivityResult
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    public boolean isSDCardStateOn() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public boolean isSDCardReadOnly() {
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

    /**
     * Check if this device has a camera
     */
    private static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)
                && Camera.getNumberOfCameras() > 0;
    }

    public File getImageFile() {
        String path = getImageFilePath();
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
            file = new File(path);
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public String getImageFilePath() {
        return SDPATH + "image_face.jpg";
    }

    public static void deleteFile(String filePath) {
        if (null != filePath) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public String getCurrentTimeFormatToDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_FOR_PIC_PATH);
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 权限
     */

    class TakePhoto extends BaseActivity {
        public Context aContext;
        private int mPhotoRequestCode;


        public TakePhoto(Context context, int photoRequestCode) {
            aContext = context;
            mPhotoRequestCode = photoRequestCode;
            SDPATH = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator;
        }

        private void checkPermission() {
            int hasWriteContactsPermission = ContextCompat.checkSelfPermission(aContext, Manifest.permission.CAMERA);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) aContext, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                takePhoto(mPhotoRequestCode);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case 1:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // Permission Granted
                        takePhoto(mPhotoRequestCode);
                    } else {
                        // Permission Denied
                        ToastUtil.showAtUI("请在APP设置页面打开相应权限");
                        AppUtil.openAppInfo(this, BuildConfig.APPLICATION_ID);
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

        @Override
        protected int getContentView() {
            return 0;
        }

        @Override
        protected void obtainParam(Intent intent) {

        }

        @Override
        protected void initView() {

        }

        @Override
        protected void initData() {

        }
    }


}
