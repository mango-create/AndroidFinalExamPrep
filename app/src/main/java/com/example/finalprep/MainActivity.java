package com.example.finalprep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements INewTweet, IRegister, ITweets, ILogin{
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TweetsFireBaseFragment()).commit();
        }

    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TweetsFireBaseFragment()).commit();
    }

    @Override
    public void launchCreateNewAccountFrag() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CreateAccountFragment()).addToBackStack(null).commit();
    }

    @Override
    public void createNewAcctSubmit() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TweetsFireBaseFragment()).commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void logout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new LoginFragment()).commit();
    }

    @Override
    public void newTweet() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NewTweetFragment()).addToBackStack(null).commit();
    }

    @Override
    public void viewTweet(String docId) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ViewTweetFragment(docId)).addToBackStack(null).commit();
    }

    @Override
    public void JSON() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new JSONFragment()).addToBackStack(null).commit();
    }

    @Override
    public void sendTweet() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TweetsFireBaseFragment()).commit();
    }

    @Override
    public void cancelTweet() {
        getSupportFragmentManager().popBackStack();
    }
}