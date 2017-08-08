package com.mobileia.google;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobileia.google.listener.OnErrorGoogleLogin;
import com.mobileia.google.listener.OnSuccessGoogleLogin;

/**
 * Created by matiascamiletti on 8/8/17.
 */

public class MobileiaGoogle implements GoogleApiClient.OnConnectionFailedListener {
    /**
     * Codigo para identificar el google login
     */
    public static final int GOOGLE_LOGIN = 5324;
    /**
     * Almacena la Activity que lo inicia
     */
    protected FragmentActivity mActivity;
    /**
     * Almacena listener para las respuestas correctas
     */
    protected OnSuccessGoogleLogin mSuccessListener;
    /**
     * Almacena listener para las respuestas erroneas
     */
    protected OnErrorGoogleLogin mErrorListener;
    /**
     * Objeto para el servicio de google
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Almacena el ID de google
     */
    protected String mGoogleId;

    /**
     * Manejador si no se pudo conectar a Google Play Services
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Llamamos al callback para informar error
        if(mErrorListener != null){
            mErrorListener.onError();
        }
    }

    /**
     * Comienza el login con Google
     */
    public void login(){
        // Iniciamos Intent de Google SignIn
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, GOOGLE_LOGIN);
    }

    /**
     * Manejador de la respuesta del login
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /**
     * Setea el Activity
     * @param activity
     */
    public void setActivity(FragmentActivity activity){
        this.mActivity = activity;
        // Crear servicio de Google
        createGoogleService();
    }

    /**
     * Setea el success
     * @param listener
     */
    public void setSuccessListener(OnSuccessGoogleLogin listener){
        mSuccessListener = listener;
    }

    /**
     * Setea el error listener
     * @param listener
     */
    public void setErrorListener(OnErrorGoogleLogin listener){
        mErrorListener = listener;
    }

    /**
     * Setea el ID de google
     * @param googleId
     */
    public void setGoogleId(String googleId){ mGoogleId = googleId; }

    /**
     * Constructor
     */
    public MobileiaGoogle(){}

    /**
     * Funcion que se encarga de obtener el resultado y procesarlo
     * @param result
     */
    protected void handleSignInResult(GoogleSignInResult result) {
        // Verificamos si la respuesta es correcta
        if(!result.isSuccess()){
            Log.d("MobileiaGoogle", "handleSignInResult: data: " + result.getStatus().toString());
            // Llamamos al callback para informar error
            if(mErrorListener != null){
                mErrorListener.onError();
                return;
            }
        }
        // Llamar al callback con la cuenta
        if(mSuccessListener != null){
            mSuccessListener.onSuccess(result.getSignInAccount());
        }
    }

    /**
     * Funcion que se encarga de crear el servicio de google
     */
    protected void createGoogleService(){
        Log.d("MobileiaGoogle", "handleSignInResult: data: " + mGoogleId);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(mGoogleId)
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
}
