package com.kotlinlib.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import id.zelory.compressor.Compressor;

/**
 * 系统选图工具类
 *
 * cao_cao_zi_yi_de
 */
public class SystemPicSelectUtils {

    //相册请求码
    public static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    public static final int CAMERA_REQUEST_CODE = 2;

    public static final String AUTHORITY = "com.fangzhou.app.fileprovider";

    /**
     * 从相机获取图片
     * 需要实现Activity的onActivityResulted方法
     * 注意在onActivityResult中无需获取intent
     */
    public static void getPicFromCamera(Activity ctx) {
        //用于保存调用相机拍照后所生成的文件
        File tempFile = new File(Environment.getExternalStorageDirectory().getPath()+"/temp", System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent;
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            File file=new File(Environment.getExternalStorageDirectory()+"/temp/" + "header.jpg");
            if (!file.getParentFile().exists())file.getParentFile().mkdirs();
            Uri imageUri = FileProvider.getUriForFile(ctx, AUTHORITY, file);//通过FileProvider创建一个content类型的Uri
            intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        ctx.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相机获取图片
     * 需要实现Fragment的onActivityResulted方法
     * 注意在onActivityResult中无需获取intent
     */
    public static void getPicFromCamera(Fragment fragment) {
        //用于保存调用相机拍照后所生成的文件
        File tempFile = new File(Environment.getExternalStorageDirectory().getPath()+"/temp", System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent;
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            File file=new File(Environment.getExternalStorageDirectory()+"/temp/" + "header.jpg");
            if (!file.getParentFile().exists())file.getParentFile().mkdirs();
            Uri imageUri = FileProvider.getUriForFile(Objects.requireNonNull(fragment.getActivity()), AUTHORITY, file);//通过FileProvider创建一个content类型的Uri
            intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        fragment.startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    public static void getPicFromAlbm(Activity ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent albumIntent = new Intent(Intent.ACTION_PICK);
            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            ctx.startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);
        } else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            ctx.startActivityForResult(intent, ALBUM_REQUEST_CODE);//打开相册
        }
    }

    /**
     * 剪切相册图片
     */
    public static void cropAlbum(Intent data, Activity ctx){
//        UCrop.of(Objects.requireNonNull(data.getData()),
//                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString()+".jpg")))
//                            .withAspectRatio(4f, 4f)
//                .withOptions(SystemPicSelectUtils.getOptions())
//                .start(ctx);
    }

    /**
     * 裁剪图片
     */
    public static void cropPhoto(Activity ctx) {
//            String fileName = Environment.getExternalStorageDirectory()+"/temp/header.jpg";
//            File file=new File(fileName);
//            try {
//                File newFile = new Compressor(ctx)
//                        .setDestinationDirectoryPath(Environment.getExternalStorageDirectory()+"/temp/")
//                        .compressToFile(file,UUID.randomUUID().toString()+".jpg");
//                //由于系统的裁剪兼容性很差，所以采用第三方
//                UCrop.of(Uri.fromFile(newFile), Uri.fromFile(newFile))
//                        .withAspectRatio(4, 4)
//                        .withOptions(getOptions())
//                        //.withMaxResultSize(maxWidth, maxHeight)
//                        .start(ctx);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
    }

    public static String getPath() {
        return Environment.getExternalStorageDirectory()+"/temp/header.jpg";
    }

    public static String getPath(Activity ctx) throws IOException {
        String fileName = Environment.getExternalStorageDirectory()+"/temp/header.jpg";
        File file=new File(fileName);
        File newFile = new Compressor(ctx)
                .setDestinationDirectoryPath(Environment.getExternalStorageDirectory()+"/temp/")
                .compressToFile(file,UUID.randomUUID().toString()+".jpg");
        return newFile.getAbsolutePath();
    }



    /**
     * 设置裁剪选项
     */
//    private static UCrop.Options getOptions(){
//        UCrop.Options options = new UCrop.Options();
//        options.setCircleDimmedLayer(true);
//        options.setStatusBarColor(Color.BLACK);
//        options.setToolbarColor(Color.BLACK);
//        options.setCropFrameStrokeWidth(0);
//        options.setActiveWidgetColor(Color.parseColor("#12B38D"));
//        options.setCropGridColumnCount(0);
//        options.setCropGridRowCount(0);
//        options.setToolbarTitle("修剪");
//        return options;
//    }

    /**
     * 清空临时图片
     */
    public static void clear(){
        File file = new File(Environment.getExternalStorageDirectory().toString()+"/temp/");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    /**
     * 保存图片到本地
     * @param name
     * @param bmp
     * @return
     */
    public static String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
 /*
    第一步：在AndroidManifest添加如下节点
    <provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="applicationId.fileprovider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/provider_paths"/>
    </provider>
    如果有其他第三方包配置过provider,可以自定义继承FileProvider例如
    public class MyFileProvider extends FileProvider{}
    <provider
            android:name=".MyFileProvider"
            android:authorities="${applicationId}.provider"
            android:grantUriPermissions="true"
            android:exported="false">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/filepaths" />
        </provider>
     */
/*
 override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            when(requestCode){
                // 调用相机后返回
                SystemPicSelectUtils.CAMERA_REQUEST_CODE->SystemPicSelectUtils.cropPhoto(this)
                // 调用相册后返回
                SystemPicSelectUtils.ALBUM_REQUEST_CODE->SystemPicSelectUtils.cropAlbum(data, this)
                //裁剪显示
                UCrop.REQUEST_CROP->engine.getFragments()[4].ivHeader.setImageURI(UCrop.getOutput(data!!))
            }
        }
    }
 */
