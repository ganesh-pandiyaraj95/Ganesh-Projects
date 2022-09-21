package com.example.xenovex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    TextView TvName,TvPhone,TvEmail,TvAddress;
    String Name,Phone,Email,address;
    ListView LvValue;

    List<UserModel> userModels;
    DataBaseHelper dataBaseHelper;
    RecyclerView RvValue;
    UserListAdapter userListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        initUI();
        dataBaseHelper=new DataBaseHelper(DetailedActivity.this);


        if (getIntent().hasExtra("UserWithLocation")) {

            Name=getIntent().getStringExtra("name");
            Phone=getIntent().getStringExtra("phone");
            Email=getIntent().getStringExtra("email");
            address=getIntent().getStringExtra("Address");

        }
//        TvName.setText(Name);
//        TvPhone.setText(Phone);
//        TvEmail.setText(Email);
//        TvAddress.setText(address);



        dataBaseHelper=new DataBaseHelper(DetailedActivity.this);
        userModels=dataBaseHelper.EveryOne();

//        ArrayAdapter UserAdapter=new ArrayAdapter<UserModel>(DetailedActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.EveryOne());
//        LvValue.setAdapter(UserAdapter);


        RvValue.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter = new UserListAdapter(dataBaseHelper.EveryOne(),this);
        RvValue.setAdapter(userListAdapter);
        userListAdapter.notifyDataSetChanged();






    }

    private void initUI() {
//        TvName=findViewById(R.id.TvName);
//        TvPhone=findViewById(R.id.TvPhone);
//        TvEmail=findViewById(R.id.TvEmail);
//        TvAddress=findViewById(R.id.TvAddress);
        RvValue=findViewById(R.id.RvValue);



    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        Intent intent=new Intent(DetailedActivity.this, MainActivity.class);
//      //  startActivity(new Intent(DetailedActivity.this, MainActivity.class));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//
//    }

    @Override
    public void onBackPressed()
    {
        Intent it = new Intent(DetailedActivity.this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }
}