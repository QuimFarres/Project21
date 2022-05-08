package com.example.project21.viewmodel;


import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project21.GameActivity;
import com.example.project21.models.Account;
import com.example.project21.models.Result;
import com.example.project21.repo.AccountRepo;
import com.example.project21.utils.AccountUtils;

import org.bouncycastle.asn1.tsp.Accuracy;

public class LoginViewModel {

    private String TAG = "LoginViewModel";


    private  MutableLiveData<String> emailLiveData;
    private  MutableLiveData<String> errorEmailLiveData;
    private  MutableLiveData<String> passwordLiveData;
    private  MutableLiveData<String> errorPasswordLiveData;

    public MutableLiveData<Boolean> isLogged;

    private AccountRepo accountRepo;

    public LoginViewModel(){
        this.emailLiveData = new MutableLiveData<>();
        this.errorEmailLiveData = new MutableLiveData<>();
        this.passwordLiveData = new MutableLiveData<>();
        this.errorPasswordLiveData = new MutableLiveData<>();

        this.isLogged = new MutableLiveData<>();

        this.accountRepo = new AccountRepo();
    }




    public void login(){

        Log.d(TAG, "In Loggin");

        // Get the data from fields
        String email = emailLiveData.getValue();
        String password = passwordLiveData.getValue();

        // Form validator
        if (isFormValid(email,password)) {
            // Shows the progress bar, telling the user that we are making the communication with the API
            isLogged.postValue(true);

            // Call the repo passing the authorization token obtained from email and password
            this.accountRepo.login(AccountUtils.getAuthorizationToken(email, password));
        }
        // Shows the progress bar, telling the user that we are making the communication with the API
        isLogged.postValue(true);

        // Call the repo passing the authorization token obtained from email and password
        this.accountRepo.login(AccountUtils.getAuthorizationToken(email, password));


    }

    public MutableLiveData<String> getEmailLiveData() {
        return emailLiveData;
    }

    public void setEmailLiveData(MutableLiveData<String> emailLiveData) {
        this.emailLiveData = emailLiveData;
    }

    public MutableLiveData<String> getErrorEmailLiveData() {
        return errorEmailLiveData;
    }

    public void setErrorEmailLiveData(MutableLiveData<String> errorEmailLiveData) {
        this.errorEmailLiveData = errorEmailLiveData;
    }

    public MutableLiveData<String> getPasswordLiveData() {
        return passwordLiveData;
    }

    public void setPasswordLiveData(MutableLiveData<String> passwordLiveData) {
        this.passwordLiveData = passwordLiveData;
    }

    public MutableLiveData<String> getErrorPasswordLiveData() {
        return errorPasswordLiveData;
    }

    public void setErrorPasswordLiveData(MutableLiveData<String> errorPasswordLiveData) {
        this.errorPasswordLiveData = errorPasswordLiveData;
    }

    public LiveData<Result<String>> isUserLogged(){
        if(this.accountRepo.getLoginResult() != null){
            isLogged.postValue(false);
        }
        return this.accountRepo.getLoginResult();
    }

    private Boolean isFormValid(String email, String password){
        boolean isValid = true;

        String validEmail= AccountUtils.isEmailValid(email);
        if ( validEmail != null){
            isValid = false;
            errorEmailLiveData.postValue(validEmail);
        };

        /*String validPassword= AccountUtils.isPasswordValid(password);
        if ( validPassword != null){
            isValid = false;
            errorPasswordLiveData.postValue(validPassword);
        };
         */

        return isValid;
    }


}


