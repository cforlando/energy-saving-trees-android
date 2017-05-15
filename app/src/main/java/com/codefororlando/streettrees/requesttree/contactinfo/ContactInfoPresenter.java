// Copyright © 2017 Code for Orlando.
// 
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.codefororlando.streettrees.requesttree.contactinfo;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
