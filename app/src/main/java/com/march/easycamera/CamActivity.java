package com.march.easycamera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.march.easycameralibs.common.CameraConstant;
import com.march.easycameralibs.common.CameraInfo;
import com.march.easycameralibs.easycam.CameraNative;
import com.march.easycameralibs.widgets.CamContainerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CamActivity extends AppCompatActivity {


    @Bind(R.id.activity_mycamera_container)
    CamContainerView camContainerView;
    CameraNative cameraNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cam);
        ButterKnife.bind(this);
        cameraNative = CameraNative.getInst();
    }

    @OnClick({
            R.id.activity_mycamera_take,
            R.id.activity_mycamera_switch,
            R.id.activity_mycamera_flash,
            R.id.activity_mycamera_over,
            R.id.activity_mycamera_mode,
            R.id.activity_mycamera_size})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.activity_mycamera_take:
                cameraNative.doTakePic(System.currentTimeMillis() + ".jpg", new CameraNative.OnTakePicListener() {
                    @Override
                    public void onTakePic(byte[] data, CameraInfo info) {
                        Bitmap bitmap = CameraNative.getInst().handlePicData(data, 1, info);
                    }

                    @Override
                    public void onTakePic(Bitmap bit) {
                        //当isOnlyGetOriginData为true时，将不会返回bitmap
                    }

                    @Override
                    public boolean isOnlyGetOriginData() {
                        return true;
                    }

                    @Override
                    public int getInSampleSize(byte[] data) {
                        return data.length % 10000;
                    }
                });
                break;
            case R.id.activity_mycamera_switch:
                cameraNative.switchCameraFacing(CameraConstant.AutoSwitch);
                break;
            case R.id.activity_mycamera_flash:
                cameraNative.switchLightWithAuto();
                break;
            case R.id.activity_mycamera_over:
                cameraNative.takePicOver();
                break;
            case R.id.activity_mycamera_mode:
                cameraNative.switchTakeMode(CameraConstant.AutoSwitch);
                break;
            case R.id.activity_mycamera_size:
                cameraNative.switchPicSize(CameraConstant.AutoSwitch);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        CameraNative.newInst(CamActivity.this, camContainerView);
        CameraNative.getInst().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CameraNative.getInst().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraNative.getInst().onDestroy();
    }
}
