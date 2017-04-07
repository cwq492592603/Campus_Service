package informationsystem.com.campusservice;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Attraction extends Activity implements OnClickListener {

    TextView user;
    ImageView photo;
    ImageView getPhoto;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        user = (TextView) findViewById(R.id.userName);
        photo = (ImageView) findViewById(R.id.photo);
        getPhoto = (ImageView) findViewById(R.id.takephoto);
        getPhoto.setOnClickListener(this);
    }

    public void onClick(View v) {
        //创建File对象，用于存储拍照后的图片
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image,jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                try {
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
