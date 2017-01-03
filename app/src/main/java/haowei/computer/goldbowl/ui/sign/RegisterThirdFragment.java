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
import haowei.computer.goldbowl.util.SnackbarUtil;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/22.
 */

public class RegisterThirdFragment extends BaseFragment {
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String CAPTCHA = "captcha";
    public static final String NAME = "name";
    public static final String IDENTITY = "identity";
    private String firstUrl;
    private String secondUrl;

    private String mPhone;
    private String mCaptcha;
    private String mPassword;
    private String mName;
    private String mIdentity;

    private static final int REQUEST_GALLERY_PHOTO_FRONT = 0x01;
    private static final int REQUEST_CAMERA_PERMISSION_FRONT = 0xa1;
    private static final int REQUEST_CAPTURE_PHOTO_FRONT = 0x02;
    private static final int REQUEST_CROP_PHOTO_FRONT = 0x03;

    private static final int REQUEST_GALLERY_PHOTO_REVERSE = 0x04;
    private static final int REQUEST_CAPTURE_PHOTO_REVERSE = 0x05;
    private static final int REQUEST_CROP_PHOTO_REVERSE = 0x06;
    private static final int REQUEST_CAMERA_PERMISSION_REVERSE = 0xa2;


    private static final String TAG = "RegisterThirdFragment";
    private Uri mPhotoUri;

    @BindView(R.id.tv_register_title)
    TextView title;
    @BindView(R.id.tv_register_success)
    TextView registerSuccess;
    @BindView(R.id.img_upload_front)
    ImageView uploadFront;
    @BindView(R.id.img_upload_reverse)
    ImageView uploadReverse;
    @BindView(R.id.root_view)
    View rootView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_register_third;
    }

    @Override
    protected void updateUI() {
        title.setText(R.string.tv_identity_verify);
        registerSuccess.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

    }

    //fragment 传递数据
    public static RegisterThirdFragment newInstance(String phone, String captcha, String password, String name, String identity) {
        RegisterThirdFragment fragment = new RegisterThirdFragment();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(captcha) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(identity)) {
            Bundle args = new Bundle();
            args.putString(PHONE, phone);
            args.putString(CAPTCHA, captcha);
            args.putString(PASSWORD, password);
            args.putString(NAME, name);
            args.putString(IDENTITY, identity);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPhone = getArguments().getString(PHONE);
            mCaptcha = getArguments().getString(CAPTCHA);
            mPassword = getArguments().getString(PASSWORD);
            mIdentity = getArguments().getString(IDENTITY);
            mName = getArguments().getString(NAME);

        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @OnClick({R.id.back_left, R.id.btn_register_success, R.id.img_upload_front, R.id.img_upload_reverse, R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back_left:
                onBackPressed();
                break;
            case R.id.img_upload_front:
                //上传图片的权限

                checkHeaderPermission(REQUEST_GALLERY_PHOTO_FRONT, REQUEST_CAPTURE_PHOTO_FRONT, REQUEST_CAMERA_PERMISSION_FRONT);
                break;
            case R.id.img_upload_reverse:
                checkHeaderPermission(REQUEST_GALLERY_PHOTO_REVERSE, REQUEST_CAPTURE_PHOTO_REVERSE, REQUEST_CAMERA_PERMISSION_REVERSE);
                break;
            case R.id.tv_skip:
                //提交部分注册信息然后跳转到登录页面
                showSkipDialog();


                break;
            case R.id.btn_register_success:
                //调用注册信息的接口
                System.out.println("打印" + Math.sqrt(2016));
               // SnackbarUtil.LongSnackbar(rootView,getResources().getString(R.string.register_success_show),SnackbarUtil.black,SnackbarUtil.yellow);
                MyUtils.showSnackbar(rootView, R.string.register_success_show);

                LoginFragment loginFragment = LoginFragment.newInstance(mPhone);
                Single.just("").delay(2, TimeUnit.SECONDS).compose(RxUtils.applySchedulers()).subscribe(s -> {
                    fragmentMgr.beginTransaction()
                            .addToBackStack(TAG)
                            .replace(R.id.fragment_login_container, loginFragment)
                            .commit();

                });
                break;
        }
    }

    //跳过上传身份证的dialog
    private void showSkipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("是否要跳过身份认证？")
                .setNegativeButton("否", (dialog, which) -> {

                })
                .setPositiveButton("是", (dialog, which) -> {

                    //调用提交的接口

                    //  checkPermission();
                });
        builder.create().show();

    }


    //上传图片的权限
    private void checkHeaderPermission(int requestCode, int responseCode, int permissionCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean hasPermission = PermissionManager.checkCameraPermission(context)
                    && PermissionManager.checkWriteExternalStoragePermission(context);
            if (hasPermission) {
                showPhotoSelectDialog(requestCode, responseCode);
            } else {
                showRequestPermissionDialog(permissionCode);
            }
        } else {
            showPhotoSelectDialog(requestCode, responseCode);
        }
    }

    /**
     * 请求权限Snackbar
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void showRequestPermissionDialog(int code) {
        if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Snackbar.make(rootView, "请提供摄像头及文件权限，以拍摄和预览相机图片!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", v -> {
                        PermissionManager.invokeCamera(RegisterThirdFragment.this, code);
                    })
                    .show();
        } else {
            PermissionManager.invokeCamera(RegisterThirdFragment.this, code);
        }
    }

    /**
     * 显示图片选择Dialog
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void showPhotoSelectDialog(int photoCode, int cameraCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("请选择图片来源")
                .setNegativeButton("相册", (dialog, which) -> {
                    startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"),
                            photoCode);
                })
                .setPositiveButton("相机", (dialog, which) -> {
                    startCapturePhoto(cameraCode);
                    //  checkPermission();
                });
        builder.create().show();
    }


    /**
     * 权限申请回调
     *
     * @param requestCode  请求码
     * @param permissions  请求权限
     * @param grantResults 请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION_FRONT:

                boolean isCanCapturePhoto = true;

                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        isCanCapturePhoto = false;
                    }
                }
                if (isCanCapturePhoto) {
                    showPhotoSelectDialog(REQUEST_GALLERY_PHOTO_FRONT, REQUEST_CAPTURE_PHOTO_FRONT);
                } else {
                    Snackbar.make(rootView, "请完整的权限，以预览裁剪图片!", Snackbar.LENGTH_SHORT).show();
                }

                break;
            case REQUEST_CAMERA_PERMISSION_REVERSE:
                boolean isCanCapturePhotos = true;

                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        isCanCapturePhotos = false;
                    }
                }
                if (isCanCapturePhotos) {
                    showPhotoSelectDialog(REQUEST_GALLERY_PHOTO_REVERSE, REQUEST_CAPTURE_PHOTO_REVERSE);
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
    private void startCapturePhoto(int code) {
        File photoFile = PhotoUtil.createImageFile();
        boolean isCanCapturePhoto = PhotoUtil.isCanCapturePhoto(context, photoFile);
        if (isCanCapturePhoto) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mPhotoUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, code);
        } else {
            Snackbar.make(rootView, "您的手机暂不支持相机拍摄，请选择相册图片！", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAPTURE_PHOTO_FRONT:
                    startCropImage(mPhotoUri, REQUEST_CROP_PHOTO_FRONT);
                    break;
                case REQUEST_GALLERY_PHOTO_FRONT:
                    System.out.println("Uri: " + data.getData());
                    startCropImage(data.getData(), REQUEST_CROP_PHOTO_FRONT);
                    break;
                case REQUEST_CROP_PHOTO_FRONT:
                    Single.just(ImageUtils.getScaledBitmap(context, UCrop.getOutput(data), uploadFront))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bitmap -> {
                                uploadFront.setImageBitmap(bitmap);
                            });
                    String imgUrl = ImageUtils.getRealPath(context, UCrop.getOutput(data));
                    uploadImage(imgUrl, uploadFront);

                    break;

                case REQUEST_CAPTURE_PHOTO_REVERSE:
                    startCropImage(mPhotoUri, REQUEST_CROP_PHOTO_REVERSE);
                    break;
                case REQUEST_GALLERY_PHOTO_REVERSE:
                    System.out.println("Uri: " + data.getData());
                    startCropImage(data.getData(), REQUEST_CROP_PHOTO_REVERSE);
                    break;
                case REQUEST_CROP_PHOTO_REVERSE:
                    Single.just(ImageUtils.getScaledBitmap(context, UCrop.getOutput(data), uploadReverse))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(bitmap -> {
                                uploadReverse.setImageBitmap(bitmap);
                            });
                    String imgUrls = ImageUtils.getRealPath(context, UCrop.getOutput(data));
                    uploadImage(imgUrls, uploadReverse);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片
     *
     * @param resUri      图片uri
     * @param requestCode 请求码
     */
    private void startCropImage(Uri resUri, int requestCode) {
        File cropFile = new File(context.getCacheDir(), "cropImg.png");
        UCrop.of(resUri, Uri.fromFile(cropFile))
                .withAspectRatio(3, 2)
                .withMaxResultSize(540, 360)
                .start(context, this, requestCode);
    }

    //上传照片到七牛
    private void uploadImage(String filePath, ImageView image) {
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
                if (image == uploadFront) {
                    firstUrl = avatarName;
                }
                if (image == uploadReverse) {
                    secondUrl = avatarName;
                }
                Glide.with(context).load(avatarName).centerCrop().into(image);
            }, null);
        });
    }

}
