package com.example.mimoh.apidemoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.mimoh.apidemoapp.Fragment.Login;
import com.example.mimoh.apidemoapp.Fragment.LoginStart;
import com.example.mimoh.apidemoapp.Fragment.SignUp;
import com.example.mimoh.apidemoapp.R;

public class LoginPage extends AppCompatActivity {

    LoginStart loginStart;
    Login login;
    SignUp signUp;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        loginStart = new LoginStart();
        login = new Login();
        signUp = new SignUp();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.FLlogin,loginStart);
        transaction.commit();

//        FacebookSdk.sdkInitialize(this.getApplicationContext());
//        AppEventsLogger.activateApp(this);
//        printkeyHash();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            //System.out.println("@#@");
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
        System.exit(1);
    }

    //    private void printkeyHash() {
//        try {
//            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.mimoh.apidemoapp",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException ignored) {
//
//        } catch (NoSuchAlgorithmException ignored) {
//
//        }
//
//    }
}
