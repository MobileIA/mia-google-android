package com.mobileia.example.google;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobileia.google.MobileiaGoogle;
import com.mobileia.google.builder.GoogleLoginBuilder;
import com.mobileia.google.listener.OnErrorGoogleLogin;
import com.mobileia.google.listener.OnSuccessGoogleLogin;

public class MainActivity extends AppCompatActivity  {

    public static final String GOOGLE_ID = "484646117709-endoqs731lb285b4gvut7pdkqr28jvqs.apps.googleusercontent.com";

    protected MobileiaGoogle mMobileiaGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMobileiaGoogle = new GoogleLoginBuilder()
                .withActivity(this)
                .withGoogleId(GOOGLE_ID)
                .withSuccessResult(new OnSuccessGoogleLogin() {
                    @Override
                    public void onSuccess(GoogleSignInAccount acct) {
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getIdToken());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getDisplayName());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getFamilyName());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getGivenName());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getEmail());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getId());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getPhotoUrl());
                        Log.d("MobileiaGoogle", "handleSignInResult: data: " + acct.getServerAuthCode());
                    }
                })
                .withErrorResult(new OnErrorGoogleLogin() {
                    @Override
                    public void onError(int code, String message) {
                        Log.d("MobileiaGoogle", "handleSignInResult: Error");
                    }
                })
                .build();
    }

    public void onClick(View v){
        mMobileiaGoogle.login();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMobileiaGoogle.onActivityResult(requestCode, resultCode, data);
    }
}
