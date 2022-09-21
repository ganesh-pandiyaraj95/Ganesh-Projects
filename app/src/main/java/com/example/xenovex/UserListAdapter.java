package com.example.xenovex;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    List<UserModel> userModels;
    Context context;
    String name,phone,email,address;
    UserModel userModel;
    DataBaseHelper dataBaseHelper;
    CameraUpdate cameraUpdate = null;

    public UserListAdapter(List<UserModel> userModels, Context context) {
        this.userModels = userModels;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_list,parent,false);
        return new UserListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvUserName.setText(userModels.get(position).getuName());
        holder.tvPhone.setText(userModels.get(position).getuMobile());
        holder.tvEmail.setText(userModels.get(position).getuEmail());
        holder.tvAddress.setText(userModels.get(position).getUAddress());

        name=userModels.get(position).getuName();
        phone=userModels.get(position).getuMobile();
        email=userModels.get(position).getuEmail();
        address=userModels.get(position).getUAddress();


        holder.BtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callDialogue(position);
            }
        });
        holder.BtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = holder.getAdapterPosition();
                int v=userModels.get(id).getId();
                userModel=new UserModel(v,name,phone,email,"");
                dataBaseHelper=new DataBaseHelper(context);
                boolean result= dataBaseHelper.DeleteOne(v);

                if(result==true){

                    Toast.makeText(context,"Some thing went wrong",Toast.LENGTH_LONG).show();
                }else{
                    userModels.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, userModels.size());
                    Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private void callDialogue(int position) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.update_dialogue);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText EdName=dialog.findViewById(R.id.EdName);
        EditText EdPhone=dialog.findViewById(R.id.EdPhone);
        EditText EdEmail=dialog.findViewById(R.id.EdEmail);
        Button BtSubmit=dialog.findViewById(R.id.BtSubmit);

        name=userModels.get(position).getuName();
        phone=userModels.get(position).getuMobile();
        email=userModels.get(position).getuEmail();
        address=userModels.get(position).getUAddress();

        EdName.setText(name);
        EdPhone.setText(phone);
        EdEmail.setText(email);

        BtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//
//                Location loc = map.getMyLocation();
//                if (loc != null) {
//                    LatLng latLng = new LatLng(loc.getLatitude(), loc
//                            .getLongitude());
//                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
//                    map.animateCamera(cameraUpdate);
//
//                }

                if(EdName.getText().toString().isEmpty()||EdPhone.getText().toString().isEmpty()||EdEmail.getText().toString().isEmpty()){

                }else{
                    userModel=new UserModel(position+1,EdName.getText().toString(),EdPhone.getText().toString(),EdEmail.getText().toString(),address);

                    dataBaseHelper=new DataBaseHelper(context);
                    boolean success = dataBaseHelper.updateOne(userModel);

                    if(success==true){
                        userModels.set(position,userModel);
                        notifyItemChanged(position);
                        Toast.makeText(context,"Successfully",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName,tvPhone,tvEmail,tvAddress;
        Button BtUpdate,BtDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            tvEmail=itemView.findViewById(R.id.tvEmail);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            BtUpdate=itemView.findViewById(R.id.BtUpdate);
            BtDelete=itemView.findViewById(R.id.BtDelete);
            


        }
    }
}
