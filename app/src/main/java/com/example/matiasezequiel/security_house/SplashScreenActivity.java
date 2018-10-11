package com.example.matiasezequiel.security_house;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        RelativeLayout Splash = findViewById(R.id.Splash);

        appPref = getSharedPreferences("isFirstTime", 0);
        boolean isFirstTime = appPref.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            crearAccesoDirecto();
        }

        Animation animationSplash = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.transition);
        Splash.startAnimation(animationSplash);
        final Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                SplashScreenActivity.this.finish();
            }
        },2500);
    }


    //----------------------------------------ACCESO DIRECTO---------------------------------------------------------
    private void crearAccesoDirecto() {
        Intent shortcutIntent = new Intent();
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, getIntentShortcut());
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "WIMP?");
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this.getApplicationContext(), R.drawable.logo1));
        shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        this.sendBroadcast(shortcutIntent);
        SharedPreferences.Editor editor = appPref.edit();
        editor.putBoolean("isFirstTime", false);
        editor.apply();
    }

    public Intent getIntentShortcut(){
        Intent i = new Intent();
        i.setClassName(this.getPackageName(), this.getPackageName() + "." + this.getLocalClassName());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

}
