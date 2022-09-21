package com.example.xenovex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_MOBILE = "USER_MOBILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_ADD = "USER_ADD";
    public static final String ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context,"user.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTble= "CREATE TABLE " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_NAME + " TEXT," + USER_MOBILE + " TEXT," + USER_EMAIL + " TEXT ," + USER_ADD + " TEXT )";
        sqLiteDatabase.execSQL(createTble);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(UserModel userModel){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(USER_NAME,userModel.getuName());
        cv.put(USER_MOBILE,userModel.getuMobile());
        cv.put(USER_EMAIL,userModel.getuEmail());
        cv.put(USER_ADD,userModel.getUAddress());

        long insert = sqLiteDatabase.insert(USER_TABLE, null, cv);
        if(insert==-1){
            return false;
        }else{
            return true;
        }



    }

    public boolean updateOne(UserModel userModel){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        int id=userModel.getId();
        //cv.put(ID,userModel.getId());
        cv.put(USER_NAME,userModel.getuName());
        cv.put(USER_MOBILE,userModel.getuMobile());
        cv.put(USER_EMAIL,userModel.getuEmail());
        //cv.put(USER_ADD,userModel.getUAddress());

        long insert = sqLiteDatabase.update(USER_TABLE,cv,ID + "=" + id,null);
        if(insert==-1){
            return false;
        }else{
            return true;
        }



    }


    public boolean DeleteOne(int id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        //sqLiteDatabase.delete(USER_TABLE,ID + "=" + id,null) ;
       // int id=userModel.getId();
//      long delete=  sqLiteDatabase.delete(USER_TABLE, ID + " = ?",new String[] {String.valueOf(id)});
//      if(delete==-1){
//          return true;
//      }else {
//          return false;
//      }
        String queryString=" DELETE FROM "+ USER_TABLE + " WHERE " + ID + "=" + id ;
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }


    public List<UserModel> EveryOne(){
        List<UserModel>returnList=new ArrayList<>();
        String queryString="SELECT * FROM " + USER_TABLE;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int userId=cursor.getInt(0);
                String userName=cursor.getString(1);
                String userPhone=cursor.getString(2);
                String userEmail=cursor.getString(3);
                String userAddress=cursor.getString(4);

                UserModel userModel
                        =new UserModel(userId,userName,userPhone,userEmail,userAddress);

                returnList.add(userModel);

            }while (cursor.moveToNext());



        }else{

        }
        cursor.close();
        sqLiteDatabase.close();

        return returnList;
    }
}
