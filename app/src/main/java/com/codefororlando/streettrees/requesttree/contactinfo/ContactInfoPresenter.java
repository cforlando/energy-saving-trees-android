package com.codefororlando.streettrees.requesttree.contactinfo;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnli on 12/10/16.
 */

public class ContactInfoPresenter {

    ContactInfoView view;
    boolean nameValidState = false;
    boolean numberValidState = false;
    boolean emailValidState = false;

    public void attach(ContactInfoView view) {
        this.view = view;
    }

    public void detach() {
        view = null;
    }

    public boolean validateEmail(String email) {
        emailValidState = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(!emailValidState) {
            view.setEmailError("Invalid email address.");
        }
        return emailValidState;
    }

    public boolean validateNumber(String number) {
        numberValidState = Patterns.PHONE.matcher(number).matches();
        if(!numberValidState) {
            view.setPhoneNumberError("Invalid phone number.");
        }
        return numberValidState;
    }

    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(name);
        nameValidState = (matcher.matches() && !TextUtils.isEmpty(name));
        if(!nameValidState) {
            view.setNameError("Invalid name");
        }
        return nameValidState;
    }

    public boolean canProceed() {
        return nameValidState && numberValidState && emailValidState;
    }

    public interface ContactInfoView {

        void setNameError(String errorMsg);
        void setEmailError(String errorMsg);
        void setPhoneNumberError(String errorMsg);
    }
}
