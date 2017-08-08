package com.mobileia.google.builder;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.mobileia.google.MobileiaGoogle;
import com.mobileia.google.listener.OnErrorGoogleLogin;
import com.mobileia.google.listener.OnSuccessGoogleLogin;

/**
 * Created by matiascamiletti on 8/8/17.
 */

public class GoogleLoginBuilder {

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
     * Configura el activity
     * @param activity
     * @return
     */
    public GoogleLoginBuilder withActivity(FragmentActivity activity){
        mActivity = activity;
        return this;
    }
    /**
     * Configura el manejador cuando el usuario se logueado correctamente
     * @param listener
     * @return
     */
    public GoogleLoginBuilder withSuccessResult(OnSuccessGoogleLogin listener){
        mSuccessListener = listener;
        return this;
    }

    /**
     * Configura el manejador para cuando el usuario no se pudo loguear
     * @param listener
     * @return
     */
    public GoogleLoginBuilder withErrorResult(OnErrorGoogleLogin listener){
        mErrorListener = listener;
        return this;
    }

    /**
     * Crea el servicio para el login con Google
     */
    public MobileiaGoogle build(){
        MobileiaGoogle service = new MobileiaGoogle();
        service.setActivity(mActivity);
        service.setSuccessListener(mSuccessListener);
        service.setErrorListener(mErrorListener);

        return service;
    }
}
