package com.wsd.text.pict_can.ui.zxing;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.wsd.text.pict_can.Constants;
import com.wsd.text.pict_can.Intents;
import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.ui.BaseActivity;
import com.wsd.text.pict_can.utils.CheckUtil;
import com.wsd.text.pict_can.view.ScanView;
import com.wsd.text.pict_can.zxing.ScanListener;
import com.wsd.text.pict_can.zxing.ScanManager;
import com.wsd.text.pict_can.zxing.decode.DecodeThread;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * 扫一扫页面
 * Initial the camera
 */
public class ScanCodeActivity extends BaseActivity implements ScanListener {

    ScanManager scanManager;
    final int PHOTO_REQUEST_CODE = 1111;
    @BindView(R.id.capture_preview)
    SurfaceView mCapturePreview;
    @BindView(R.id.capture_scan_line)
    ImageView mCaptureScanLine;
    @BindView(R.id.scan_image)
    ScanView mScanImage;
    @BindView(R.id.capture_crop_view)
    RelativeLayout mCaptureCropView;
    @BindView(R.id.capture_container)
    RelativeLayout mCaptureContainer;
    @BindView(R.id.zxing_qrcode_btn)
    TextView mOpenVerify;
    private int scanMode;//扫描模型（条形，二维码，全部）


    public static Intent intent(Context context) {
        return new Intents.Builder().setClass(context, ScanCodeActivity.class)
                .toIntent();
    }


    @Override
    protected void initData() {
      //  initToolbar("扫一扫");
        //构造出扫描管理器
        scanManager = new ScanManager(this, mCapturePreview, mCaptureContainer, mCaptureCropView, mCaptureScanLine,
                scanMode, this);
        mOpenVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                startActivity(FragmentsActivity.intent(getApplicationContext(), VerifyFragment.class.getCanonicalName
//                        (), true));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (scanManager != null) {
            scanManager.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected void obtainParam(Intent intent) {
        scanMode = intent.getIntExtra(Constants.REQUEST_SCAN_MODE, Constants.REQUEST_SCAN_MODE_ALL_MODE);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void scanResult(Result rawResult, Bundle bundle) {
        if (rawResult == null || bundle == null) return;
        if (!scanManager.isScanning()) { //如果当前不是在扫描状态
            mScanImage.setVisibility(View.VISIBLE);
            Bitmap barcode = null;
            byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
            }
            mScanImage.setImageBitmap(barcode);
        }
        mScanImage.setVisibility(View.VISIBLE);

        String result = rawResult.getText();
        if (CheckUtil.isNotEmpty(result)) {
            startScan();
        }

    }

    //再次扫面启动方法调用
    void startScan() {
        mScanImage.setVisibility(View.GONE);
        scanManager.reScan();
    }

    @Override
    public void scanError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        //相机扫描出错时
        if (e.getMessage() != null && e.getMessage().startsWith("相机")) {
            mCapturePreview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_CODE:
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        scanManager.scanningImage(path);
                        cursor.close();
                    }
                    break;
            }
        }
    }

}