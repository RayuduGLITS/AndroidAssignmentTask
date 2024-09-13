package com.rayudu.androidassignmenttask.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rayudu.androidassignmenttask.model.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AvatarConverter {

/*    @TypeConverter
    public byte[] fromBitmap(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @TypeConverter
    public Bitmap toBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }*/

    @TypeConverter
    public String fromUserModelList(List<UserModel> value){
        return new Gson().toJson(value);
    }

    @TypeConverter
    public List<UserModel> toUserModelList(String value){
        return new Gson().fromJson(value,new TypeToken<UserModel>(){
        }.getType());
    }

}
