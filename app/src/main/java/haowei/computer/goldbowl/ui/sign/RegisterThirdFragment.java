package haowei.computer.goldbowl.ui.sign;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qiniu.android.storage.UploadManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import haowei.computer.goldbowl.R;
import haowei.computer.goldbowl.base.BaseFragment;
import haowei.computer.goldbowl.data.remote.HttpClient;
import haowei.computer.goldbowl.data.remote.RemoteService;
import haowei.computer.goldbowl.model.post.QiNiuToken;
import haowei.computer.goldbowl.util.ImageUtils;
import haowei.computer.goldbowl.util.MyUtils;
import haowei.computer.goldbowl.util.PermissionManager;
import haowei.computer.goldbowl.util.PhotoUtil;
import haowei.computer.goldbowl.util.RxUtils;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RegisterThirdFragment extends BaseFragment {
    private String account;
    private String password;
    private static final int REQUEST_GALLERY_PHOTO = 0x01;
    private static final int REQUEST_CAMERA_PERMISSION = 0xa1;
    private static final int REQUEST_CAPTURE_PHOTO = 0x02;
    private static final int REQUEST_CROP_PHOTO = 0x03;
    private static final String TAG ="RegisterThirdFragment";
    private Uri mPhotoUri;

    @BindView(R.id.tv_register_title)TextView title;
    @BindView(R.id.tv_register_success)TextView registerSuccess;
    @BindView(R.id.img_upload_front)ImageView uploadFront;
    @BindView(R.id.img_upload_reverse)ImageView ploadReverse;
    @BindView(R.id.root_view)View rootView;
    @Override
    protected int getContentView() {
        return R.layout.fragment_register_third;
    }

    @Override
    protected void updateUI() {
        title.setText(R.string.tv_identity_verify);
        registerSuccess.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

    }



    @OnClick({R.id.back_left,R.id.btn_register_success,R.id.img_upload_front,R.id.img_upload_reverse,R.id.tv_skip})public void onClick(View view){
        switch (view.getId()){

            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.img_upload_front:
                //上传图片的权限
                checkHeaderPermission();
                break;
            case R.id.img_upload_reverse:

                break;
            case R.id.tv_skip:

                break;
            case R.id.btn_register_success:
                //调用注册信息的接口
                System.out.println("打印"+Math.sqrt(2016));
                MyUtils.showSnackbar(rootView,R.string.register_success_show);

                LoginFragment loginFragment=LoginFragment.newInstance(account);
                Single.just("").delay(2, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
                    fragmentMgr.beginTransaction()
                            .addToBackStack(TAG)
                            .replace(R.id.fragment_login_container, loginFragment)
                            .commit();

                });
                break;
        }
    }
    //上传图片的权限
    private void checkHeaderPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean hasPermission = PermissionManager.checkCameraPermission(context)
                    && PermissionManager.checkWriteExternalStoragePermission(context);
            if (hasPermission) {
                showPhotoSelectDialog();
            } else {
                showRequestPermissionDialog();
            }
        } else {
            showPhotoSelectDialog();
        }
    }

    /**
     * 请求权限Snackbar
     */
    @TargetApi(Build.VERSION_CODES.M) private void showRequestPermissionDialog() {
        if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Snackbar.make(rootView, "请提供摄像头及文件权限，以拍摄和预览相机图片!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", v -> {
                        PermissionManager.invokeCamera(RegisterThirdFragment.this, REQUEST_CAMERA_PERMISSION);
                    })
                    .show();
        } else {
            PermissionManager.invokeCamera(RegisterThirdFragment.this, REQUEST_CAMERA_PERMISSION);
        }
    }

    /**
     * 显示图片选择Dialog
     */
    @TargetApi(Build.VERSION_CODES.M) private void showPhotoSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("请选择图片来源")
                .setNegativeButton("相册", (dialog, which) -> {
                    startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"),
                            REQUEST_GALLERY_PHOTO);
                })
                .setPositiveButton("相机", (dialog, which) -> {
                    startCapturePhoto();
                    //  checkPermission();
                });
        builder.create().show();
    }


    /**
     * 权限申请回调
     *
     * @param requestCode 请求码
     * @param permissions 请求权限
     * @param grantResults 请求结果
     */
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:

                boolean isCanCapturePhoto = true;

                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        isCanCapturePhoto = false;
                    }
                }
                if (isCanCapturePhoto) {
                    showPhotoSelectDialog();
                } else {
                    Snackbar.make(rootView, "请完整的权限，以预览裁剪图片!", Snackbar.LENGTH_SHORT).show();
                }

                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     * 调用系统相机
     */
    private void startCapturePhoto() {
        File photoFile = PhotoUtil.createImageFile();
        boolean isCanCapturePhoto = PhotoUtil.isCanCapturePhoto(context, photoFile);
        if (isCanCapturePhoto) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mPhotoUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, REQUEST_CAPTURE_PHOTO);
        } else {
            Snackbar.make(rootView, "您的手机暂不支持相机拍摄，请选择相册图片！", Snackbar.LENGTH_SHORT).show();
        }
    }
    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE_PHOTO:
                    startCropImage(mPhotoUri, REQUEST_CROP_PHOTO);
                    break;
                case REQUEST_GALLERY_PHOTO:
                    System.out.println("Uri: " + data.getData());
                    startCropImage(data.getData(), REQUEST_CROP_PHOTO);
                    break;
                case REQUEST_CROP_PHOTO:
                    Single.just(ImageUtils.getScaledBitmap(context, UCrop.getOutput(data), uploadFront))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bitmap -> {
                                uploadFront.setImageBitmap(bitmap);
                            });
                    String imgUrl = ImageUtils.getRealPath(context, UCrop.getOutput(data));
                    uploadImage(imgUrl,uploadFront);

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片
     *
     * @param resUri 图片uri
     * @param requestCode 请求码
     */
    private void startCropImage(Uri resUri, int requestCode) {
        File cropFile = new File(context.getCacheDir(), "cropImg.png");
        UCrop.of(resUri, Uri.fromFile(cropFile))
                .withAspectRatio(3, 2)
                .withMaxResultSize(540, 360)
                .start(context, this, requestCode);
    }
    private void uploadImage(String filePath,ImageView image) {
        System.out.println("打印数据：" + filePath);
        HttpClient client = new HttpClient();
        String tokenUrl = RemoteService.DOMAIN + "Home/register/getuploadtoken";
        client.getData(tokenUrl).flatMap(response -> {
            String result = null;
            try {
                result = response.body().string();
            } catch (IOException e) {
                return Single.error(e);
            }
            if (response.isSuccessful() && !TextUtils.isEmpty(result)) {
                Gson gson = new Gson();
                QiNiuToken qiNiuToken = gson.fromJson(result, QiNiuToken.class);
                return Single.just(qiNiuToken.getToken());
            }
            return Single.just("");
        }).subscribe(s -> {
            UploadManager uploadManager = new UploadManager();
            String keys = UUID.randomUUID().toString().replace("-", "") + ".png";

            uploadManager.put(filePath, keys, s, (key, info, response) -> {
                String avatarName = RemoteService.QINIU_CDN + key;
                System.out.println(avatarName);
                Glide.with(context).load(avatarName).centerCrop().into(image);
            }, null);
        });
    }

}
