package com.dpk.fingerprintauthentication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Deepak Kumar on 21-01-2018.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;


    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authentication Successful.", true);
    }


    public void update(String e, Boolean success){
        TextView tvMessage = (TextView) ((Activity)context).findViewById(R.id.tvMessage);
        LinearLayout llFingerprint = (LinearLayout) ((Activity)context).findViewById(R.id.llFingerprint);
        tvMessage.setText(e);
        if(success){
            llFingerprint.setBackgroundTintList(context.getColorStateList(android.R.color.holo_green_light));
        }else{
            llFingerprint.setBackgroundTintList(context.getColorStateList(android.R.color.holo_red_dark));
        }
    }
}