package com.rayudu.androidassignmenttask.adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rayudu.androidassignmenttask.databinding.UserCardBinding;
import com.rayudu.androidassignmenttask.model.UserModel;
import com.rayudu.androidassignmenttask.ui.MainActivity;
import com.rayudu.androidassignmenttask.utils.BitmapUtils;
import com.rayudu.androidassignmenttask.utils.Constants;
import com.rayudu.androidassignmenttask.utils.OnCardClick;

import java.util.ArrayList;
import java.util.List;

public class UserRvAdapter extends RecyclerView.Adapter<UserRvAdapter.ViewHolder> {

    Context context;

    List<UserModel> userModelList;
    OnCardClick onCardClick;

    public UserRvAdapter(Context context, List<UserModel> userModelList, OnCardClick onCardClick) {
        this.context = context;
        this.userModelList = userModelList;
        this.onCardClick = onCardClick;
    }

    @NonNull
    @Override
    public UserRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserCardBinding userCardBinding = UserCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(userCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRvAdapter.ViewHolder holder, int position) {

        int pos = position;
        UserModel tag = userModelList.get(position);
        holder.binding.tvName.setText(""+tag.getFirst_name()+tag.getLast_name());
        holder.binding.tvEmail.setText(""+tag.getEmail());


        if (tag.isImageUploaded()) {
            Glide.with(context)
                    .load(BitmapUtils.stringToBitmap(tag.getAvatar()))
                    .into(holder.binding.ivAvatar);
        }else {
            Glide.with(context)
                    .load(tag.getAvatar())
                    .into(holder.binding.ivAvatar);
        }




        holder.binding.ivUploadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMediaPermission()){
                    onCardClick.OnItemClick(pos);
                }else {
                    Toast.makeText(context, "Please allow permissions ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private UserCardBinding binding;

        public ViewHolder(@NonNull UserCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


    // Check if media permissions are granted
    private boolean checkMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

}
