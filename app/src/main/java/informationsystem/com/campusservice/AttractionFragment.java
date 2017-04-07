package informationsystem.com.campusservice;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Chen on 2017/3/23.
 */
public class AttractionFragment extends Fragment implements OnClickListener {
    private ImageButton select;
    private PopupWindow popupWindow;
    private TextView takePhoto;
    private TextView chooseFromAlbum;
    private ImageView photo;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int PHOTO_ALBUM = 3;
    public static final int SHOW_PHOTO_ALBUM = 4;
    private Uri imageUri;
    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attraction_frag, container, false);
        select = (ImageButton) view.findViewById(R.id.select);
        photo = (ImageView) view.findViewById(R.id.photo);

        View popupView = inflater.inflate(R.layout.layout_popupwindow, null);
        takePhoto = (TextView) popupView.findViewById(R.id.takephoto);
        chooseFromAlbum = (TextView) popupView.findViewById(R.id.select_photo);
        popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        select.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        chooseFromAlbum.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select:
                popupWindow.showAsDropDown(v);
                break;
            case R.id.takephoto:
                setTakePhoto();
                break;
            case R.id.select_photo:
                setChooseFromAlbum();
                break;
        }

    }

    private void setTakePhoto(){
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
//        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    public void setChooseFromAlbum(){
        File chooseImage = new File(Environment.getExternalStorageDirectory(),"choose_image.jpg");
        try {
            if(chooseImage.exists()){
                chooseImage.delete();
            }
            chooseImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        uri = Uri.fromFile(chooseImage);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        Intent intent = new Intent("Intent.ACTION_PICK",null);
        intent.setType("image/*");
        intent.putExtra("crop",true);
        intent.putExtra("scale",true);
//        intent.putExtra("aspectX", 1);// 裁剪框比例
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 300);// 输出图片大小
//        intent.putExtra("outputY", 300);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,PHOTO_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){
            Toast.makeText(getActivity(),"data为空",Toast.LENGTH_SHORT).show();
        }
        if(resultCode != Activity.RESULT_OK){
            Toast.makeText(getActivity(),"resultCode is error!",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    imageUri = data.getData();
                    Toast.makeText(getActivity(),"拍摄照片",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                Toast.makeText(getActivity(),"剪裁图片",Toast.LENGTH_SHORT).show();
                Bitmap bitmap = null;
                try {
                    if (resultCode == Activity.RESULT_OK) {
                        if(bitmap != null){
                            bitmap.recycle(); //如果不释放的话，不断取图片，将会内存不够
                        }
                        Toast.makeText(getActivity(),"显示图片",Toast.LENGTH_SHORT).show();
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"未找到图片",Toast.LENGTH_SHORT).show();
                }
                break;
            case PHOTO_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    uri = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    // 启动裁剪
                    startActivityForResult(intent, SHOW_PHOTO_ALBUM);
                }
                break;
            case SHOW_PHOTO_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                        // 将裁剪后的照片显示出来
                        photo.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }

}
