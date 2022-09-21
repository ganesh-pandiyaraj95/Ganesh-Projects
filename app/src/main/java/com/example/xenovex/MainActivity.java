package com.example.xenovex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button BtSubmit,BtlOCATION;
    EditText EdName,EdPhone,EdEmail;
    DataBaseHelper dataBaseHelper;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtSubmit=findViewById(R.id.BtSubmit);
        EdName=findViewById(R.id.EdName);
        EdPhone=findViewById(R.id.EdPhone);
        EdEmail=findViewById(R.id.EdEmail);





        BtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkFields= validation(EdName.getText().toString(),EdPhone.getText().toString(),EdEmail.getText().toString());

                if(checkFields==true){
                    userModel=new UserModel(-1,EdName.getText().toString(),EdPhone.getText().toString(),EdEmail.getText().toString(),"");
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        intent.putExtra("User", "UserDetails");
                        intent.putExtra("name",EdName.getText().toString());
                        intent.putExtra("phone",EdPhone.getText().toString());
                        intent.putExtra("email",EdEmail.getText().toString());
                        startActivity(intent);
                    }

                }




        });




    }

    private boolean validation(String Name, String Phone, String Email) {

        if(Name.length()==0){
            EdName.requestFocus();
            EdName.setError("Please Enter name");
            return false;
        }else if(!Name.matches("[a-zA-Z]+")){
            EdName.requestFocus();
            EdName.setError("Enter Only name");
            return false;
        } else if(!Phone.matches("^[6-9][0-9]{9}$+")){
            EdPhone.requestFocus();
            EdPhone.setError("Please Enter Valid Mobile Number");
            return false;
        }else if(Phone.length()==0){
            EdPhone.requestFocus();
            EdPhone.setError("Please Enter Mobile number");
            return false;
        }else if(Email.length()==0){
            EdEmail.requestFocus();
            EdEmail.setError("Please Enter Email");
            return false;
        }else if(!Email.matches("[a-zA-Z0-9_.]+@[a-zA-Z0-9]+.[a-zA-Z]{2,3}[.] {0,1}[a-zA-Z]+")){
            EdEmail.requestFocus();
            EdEmail.setError("Please Enter Valid email");
            return false;
        }
        else{
            return  true;
        }
    }
}