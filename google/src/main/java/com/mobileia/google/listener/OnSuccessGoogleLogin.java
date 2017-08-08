package com.mobileia.google.listener;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by matiascamiletti on 8/8/17.
 */

public interface OnSuccessGoogleLogin {
    void onSuccess(GoogleSignInAccount account);
}
