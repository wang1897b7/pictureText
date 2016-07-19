package com.wsd.text.pict_can.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.wsd.text.pict_can.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frank on 15/10/26.
 */
public class ChoosePictureUtil {

    public static String SDPATH = Environment.getExternalStorageDirectory() + File.separator;
    public static String PICTURE_NAME = "image_cam.jpg";
    //private static String APP_PATH = SDPATH + "superwan" + File.separator;
    //private static String IMAGE_PATH = APP_PATH + "chaojiwan" + File.separator;
    private static final String DATE_FORMAT_FOR_PIC_PATH = "yyyyMMdd_HHmmss";

    private Context mContext;


    public ChoosePictureUtil(Context context){
        mContext = context;
        SDPATH = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator;
    }

    public void showDialog(final int requestCode,final int requestCode2){
        if (isSDCardStateOn() && !isSDCardReadOnly()) {
            new AlertDialog.Builder(mContext)
                    .setItems(mContext.getResources().getStringArray(R.array.crop_or_original_array), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0){
                                takePhoto(requestCode);
                            }else if(which == 1){
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
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            Uri imageUri = Uri.fromFile(new File(SDPATH,PICTURE_NAME));
            //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://" + photoPath));
            ((Activity)mContext).startActivityForResult(intent, requestCode);
        } else {
            CheckUtil.showShortMessage(mContext,"相机不可用");
        }
    }

    public void choosePhoto(int requestCode) {
//        Intent intent;
//        intent = new Intent(mContext, AddSinglePicGridActivity.class);
//        ((Activity)mContext).startActivityForResult(intent, requestCode);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//Pick an item fromthe data
        intent.setType("image/*");//从所有图片中进行选择
        ((Activity)mContext).startActivityForResult(intent, requestCode);

    }

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
        /*
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.ImageColumns.DATA, cropPath);
        Uri cropUri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        */
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cropPath)));// 将URI指向相应的file:///


        //start the activity - we handle returning in onActivityResult
        ((Activity)mContext).startActivityForResult(intent, requestCode);
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
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
            file = new File(path);
        }else{
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


}
