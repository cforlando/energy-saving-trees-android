package com.codefororlando.streettrees.fragments.request_tree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Address;
import com.codefororlando.streettrees.view.PageFragment;

/**
 * Created by johnli on 9/24/16.
 */
public class AddressFormFragment extends PageFragment {

    //TODO override onSavedInstance

    Button nextButton;
    EditText streetAddressField;
    EditText streetAddressExtraField;
    EditText cityField;
    EditText stateField;
    EditText zipField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_address, container, false);

        return  view;
    }

    void bindUi(View view) {
        streetAddressField = (EditText) view.findViewById(R.id.street_address_field);
        streetAddressExtraField = (EditText) view.findViewById(R.id.street_address_extra_field);
        cityField = (EditText) view.findViewById(R.id.city_field);
        stateField = (EditText) view.findViewById(R.id.state_field);
        zipField = (EditText) view.findViewById(R.id.zip_field);

        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });
    }

    void nextFragment() {
        Address address = new Address();
        address.setStreetAddress(streetAddressField.getText().toString());
        address.setStreetAddressExtra(streetAddressExtraField.getText().toString());
        address.setCity(cityField.getText().toString());
        address.setState(stateField.getText().toString());
        address.setZip(zipField.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Address.TAG, address);
        listener.next(bundle);
    }
}
