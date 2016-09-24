package com.codefororlando.streettrees.fragments.request_tree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Address;
import com.codefororlando.streettrees.api.models.ContactInfo;
import com.codefororlando.streettrees.view.PageFragment;

/**
 * Created by johnli on 9/24/16.
 */
public class ContactInfoFragment extends PageFragment {

    Button nextButton;
    EditText nameField;
    EditText phoneNumberField;
    EditText emailField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_contact_info, container, false);
        bindUi(view);
        return  view;
    }

    void bindUi(View view) {
        nameField = (EditText) view.findViewById(R.id.name_field);
        phoneNumberField = (EditText) view.findViewById(R.id.phone_number_field);
        emailField = (EditText) view.findViewById(R.id.email_field);

        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });
    }

    void nextFragment() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName(nameField.getText().toString());
        contactInfo.setPhoneNumber(phoneNumberField.getText().toString());
        contactInfo.setEmail(emailField.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ContactInfo.TAG, contactInfo);
        listener.next(bundle);
    }

}
