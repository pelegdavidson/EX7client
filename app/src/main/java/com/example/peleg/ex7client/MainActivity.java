package com.example.peleg.ex7client;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    private final int MY_CALL_REQUEST=1;
    private final int REGISTER_REQUEST=5;
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
                email(etemail.getText().toString());
                break;
            }
            case R.id.btregister:{
                EditText etregister = (EditText)findViewById(R.id.input4);
                Intent reg = new Intent("com.ex7.action.REGISTER");
                startActivityForResult(reg, REGISTER_REQUEST);
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
                    EditText etcall = (EditText)findViewById(R.id.input1);
                    callPhone(etcall.getText().toString());
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
    private void email(String url){
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.setType("plain/text");
        mail.putExtra(Intent.EXTRA_EMAIL, new String[]{url});
        mail.putExtra(Intent.EXTRA_SUBJECT, "subject");
        mail.putExtra(Intent.EXTRA_TEXT, "message");
        startActivity(Intent.createChooser(mail, "send email"));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Resources res =getResources();
        String gender="";
        TextView tv = (TextView) findViewById(R.id.input4);
        if (requestCode == REGISTER_REQUEST && resultCode == RESULT_OK) {
            if (data.getStringExtra("Gender").compareTo("Male") == 0) {
                gender ="Mr.";
            } else
                gender = "Mrs.";
        }
        tv.setText(res.getString(R.string.Welcome ,gender,data.getStringExtra("First Name"),data.getStringExtra("Last Name")));

    }
}
