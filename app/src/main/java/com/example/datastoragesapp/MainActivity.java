package com.example.datastoragesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME="com.example.datastoragesapp.PREF";
    public static final String KEYNAME="SAVETEXT";
    public static final String FILENAME="savetext3.txt";
    private EditText editText;
    private TextView textBuka;
    private static final String[] PERMISSION={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= findViewById(R.id.input);
        textBuka= findViewById(R.id.output);
    }

    private static boolean hasPermission(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null){
            for(String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context,permission)
                        != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }
    public void simpanES(){
        if(hasPermission(this, PERMISSION)){
            String input=editText.getText().toString();
            File path= Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            File file=new File(path.toString(), FILENAME);
            FileOutputStream outputStream=null;
            try {
                file.createNewFile();
                outputStream=new FileOutputStream(file, false);
                outputStream.write(input.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            ActivityCompat.requestPermissions(this, PERMISSION, 200);
        }
    }
     public void bukaES(){
         File path= Environment.getExternalStoragePublicDirectory(
                 Environment.DIRECTORY_DOWNLOADS);
         File file=new File(path.toString(), FILENAME);
         if(file.exists()){
             StringBuilder text=new StringBuilder();
             try {
                 BufferedReader br=new BufferedReader(new FileReader(file));
                 String line=br.readLine();
                 while (line!=null){
                     text.append(line);
                     line=br.readLine();
                 }
                 br.close();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             textBuka.setText(text.toString());
         }else {
             textBuka.setText("");
         }
     }
     public void hapusES(){
         File path= Environment.getExternalStoragePublicDirectory(
                 Environment.DIRECTORY_DOWNLOADS);
         File file=new File(path.toString(), FILENAME);
         if(file.exists()){
             file.delete();
         }
     }


    public void simpanIS(){
        String input=editText.getText().toString();
        File path= getDir("NEWDIR",MODE_PRIVATE);
        File file=new File(path.toString(), FILENAME);
        FileOutputStream outputStream=null;
        try {
            file.createNewFile();
            outputStream=new FileOutputStream(file, false);
            outputStream.write(input.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bukaIS(){
        File path= getDir("NEWDIR",MODE_PRIVATE);
        File file=new File(path.toString(), FILENAME);
        if(file.exists()){
            StringBuilder text=new StringBuilder();
            try {
                BufferedReader br=new BufferedReader(new FileReader(file));
                String line=br.readLine();
                while (line!=null){
                    text.append(line);
                    line=br.readLine();
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            textBuka.setText(text.toString());
        }else {
            textBuka.setText("");
        }
    }
    public void hapusIS(){
        File path= getDir("NEWDIR",MODE_PRIVATE);
        File file=new File(path.toString(), FILENAME);
        if(file.exists()){
            file.delete();
        }
    }

    public void simpanSP(){
        String input=editText.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEYNAME, input);
        editor.commit();
    }

    public void bukaSP(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREFNAME, MODE_PRIVATE);
        if(sharedPreferences.contains(KEYNAME)){
            String input= sharedPreferences.getString(KEYNAME,"");
            textBuka.setText(input);
        }else {
            textBuka.setText("");
        }
    }

    public void hapusSP(){
        SharedPreferences sharedPreferences=getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void simpan(View view) {
        simpanES();
    }

    public void buka(View view) {
        bukaES();
    }

    public void hapus(View view) {
        hapusES();
    }
}