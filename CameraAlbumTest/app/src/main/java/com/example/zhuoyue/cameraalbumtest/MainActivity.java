package com.example.zhuoyue.cameraalbumtest;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int TAKE_PTOTO = 1;
    private static final int CHOOSE_FROM_ALBUM = 2;

    private ImageView ivPic;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
        Button btnChooseFromAlbum = (Button) findViewById(R.id.btn_choose_from_album);
        ivPic = (ImageView) findViewById(R.id.iv_pic);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //生成图片文件，放在SD卡中应用缓存目录
                File output_img = new File(getExternalCacheDir(), "out_put_image.jpg");
                if (output_img.exists()) {
                    output_img.delete();
                }
                try {
                    output_img.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //获取文件URI
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.zhuoyue.cameraalbumtest.fileprovider", output_img);
                } else {
                    imageUri = Uri.fromFile(output_img);
                }

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PTOTO);
            }
        });

        btnChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PTOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //解析bitmap并填充控件
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        ivPic.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_FROM_ALBUM:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        hundleImageOnKitKat(data);
                    } else {
                        hundleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void hundleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imgPath = getImagePath(uri,null);
        displayImage(imgPath);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hundleImageOnKitKat(Intent data) {
        String imgPath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的额Uri,使用document id处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docID.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imgPath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docID));
                imgPath = getImagePath(uri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,使用普通方式处理
            imgPath = getImagePath(uri, null);
        } else {
            //如果是file类型的Uri,直接获取图片路径
            imgPath = uri.getPath();
        }
        displayImage(imgPath);
    }

    /**
     * 将图片装载起来
     *
     * @param imgPath
     */
    private void displayImage(String imgPath) {
        if (imgPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            ivPic.setImageBitmap(bitmap);
        } else {
            Toast.makeText(MainActivity.this, "failed to get photo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取图片的真实路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和Selecion来获取真实图片的路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(MainActivity.this, "打开相册失败:无权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
