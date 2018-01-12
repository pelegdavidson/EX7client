package com.example.peleg.ex7client;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int MY_CALL_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.btcall:{
                if(checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    EditText etcall = (EditText)findViewById(R.id.input1);
                    callPhone(etcall.getText().toString());
                }
                else{
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},MY_CALL_REQUEST);
                }
                break;
            }
            case R.id.btsurf:{
                EditText etsurf = (EditText)findViewById(R.id.input2);
                surf(etsurf.getText().toString());
                break;
            }
            case R.id.btemail:{
                EditText etemail = (EditText)findViewById(R.id.input3);
                break;
            }
            case R.id.btregister:{
                EditText etregister = (EditText)findViewById(R.id.input4);
                break;
            }


        }
    }
    @SuppressWarnings({"MissingPermission"})
    private void callPhone(String phoneNumber){
        Uri call = Uri.parse("tel:" + phoneNumber);
        Intent c = new Intent(Intent.ACTION_CALL, call);
        startActivity(c);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_CALL_REQUEST:
                if(grantResults.length>0&&
                        permissions[0].equals(Manifest.permission.CALL_PHONE)&&
                        grantResults[0]==getPackageManager().PERMISSION_GRANTED){

                }
        }
    }
    private void surf(String url){
        if(!url.toLowerCase().contains("http://"))
        {
            url =("http://" + url);
        }
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }
}
