package com.mobileia.google.authentication;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.JsonObject;
import com.mobileia.authentication.core.MobileiaAuthBase;
import com.mobileia.authentication.core.listener.LoginResult;
import com.mobileia.authentication.core.rest.AuthRestBase;
import com.mobileia.core.entity.Error;
import com.mobileia.google.MobileiaGoogle;
import com.mobileia.google.builder.GoogleLoginBuilder;
import com.mobileia.google.listener.OnErrorGoogleLogin;
import com.mobileia.google.listener.OnSuccessGoogleLogin;

/**
 * Created by matiascamiletti on 1/2/18.
 */

public class GoogleMobileiaAuth extends MobileiaAuthBase {
    /**
     * Almacena el ID de google
     */
    protected String mGoogleId;
    /**
     * Almacena el Token de google
     */
    protected String mGoogleToken;
    /**
     * Almacena el ID del usuario de google
     */
    protected String mGoogleUserId;
    /**
     * Almacena instancia de MobileiaGoogle
     */
    protected MobileiaGoogle mMobileiaGoogle;

    /**
     * Constructor
     *
     * @param activity
     */
    public GoogleMobileiaAuth(Activity activity, String googleId) {
        super(activity);
        // Guardamos ID de google
        mGoogleId = googleId;
    }

    @Override
    public void requestAccessToken() {
        // Generamos objeto para enviar los parametros
        JsonObject params = new JsonObject();
        params.addProperty("grant_type", "google");
        params.addProperty("google_token", mGoogleToken);
        new AuthRestBase().oauth(params, mAccessTokenResult);
    }

    @Override
    public void requestNewAccount() {
        // Generamos objeto para enviar los parametros
        JsonObject params = new JsonObject();
        params.addProperty("register_type", "google");
        params.addProperty("google_token", mGoogleToken);
        new AuthRestBase().register(params, mRegisterResult);
    }

    @Override
    public void openSocial() {
        mMobileiaGoogle = new GoogleLoginBuilder()
                .withSuccessResult(new OnSuccessGoogleLogin() {
                    @Override
                    public void onSuccess(GoogleSignInAccount account) {
                        // Almacenamos el token de google
                        mGoogleToken = account.getIdToken();
                        mGoogleUserId = account.getId();
                        // Ya se logueo, realizamos peticion para generar AccessToken
                        requestAccessToken();
                    }
                })
                .withErrorResult(new OnErrorGoogleLogin() {
                    @Override
                    public void onError(int code, String message) {
                        if(mCallback != null){
                            // Llamamos al callback con error
                            mCallback.onError(new Error(code, message));
                        }
                    }
                })
                .withGoogleId(mGoogleId)
                .withActivity((FragmentActivity) mActivity)
                .build();
        mMobileiaGoogle.login();
    }

    /**
     * Devuelve el ID del usuario logueado
     * @return
     */
    public String getGoogleUserId(){ return mGoogleUserId; }

    /**
     * Devuelve el Token del usuario logueado
     * @return
     */
    public String getGoogleUserToken(){ return mGoogleToken; }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mMobileiaGoogle != null){
            mMobileiaGoogle.onActivityResult(requestCode, resultCode, data);
        }
    }
}
