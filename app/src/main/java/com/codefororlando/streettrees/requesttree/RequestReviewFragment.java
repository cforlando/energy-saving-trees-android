package com.codefororlando.streettrees.requesttree;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codefororlando.streettrees.R;
import com.codefororlando.streettrees.api.models.Address;
import com.codefororlando.streettrees.api.models.ContactInfo;

public class RequestReviewFragment extends BlurredBackgroundFragment {


    Button nextButton;
    TextView addressLabel;
    TextView contactLabel;

    ReviewFragmentDelegate delegate;

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.request_review_fragment_title));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_tree_review, container, false);
        bindView(view);
        updateUi();
        initBlurredBackground(view, R.drawable.bg_house_center_trees, 25f, .05f);
        return view;
    }

    private void bindView(View view) {
        addressLabel = (TextView) view.findViewById(R.id.address_label);
        contactLabel = (TextView) view.findViewById(R.id.contact_label);
        nextButton = (Button) view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });
    }

    private void updateUi() {
        Address address = delegate.getAddress();
        ContactInfo contactInfo = delegate.getContactInfo();

        String addressStr = String.format("%s %s\n%s %s, %s", address.getStreetAddress(),
                address.getStreetAddressExtra(), address.getCity(), address.getState(), address.getZip());
        addressLabel.setText(addressStr);

        String contactStr = String.format("%s\n%s\n%s", contactInfo.getName(), contactInfo.getPhoneNumber(),
                contactInfo.getEmail());
        contactLabel.setText(contactStr);
    }

    private void nextFragment() {
        pageListener.next();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            delegate = (ReviewFragmentDelegate) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ReviewFragmentDelegate");
        }
    }

    public interface ReviewFragmentDelegate {
        Address getAddress();

        ContactInfo getContactInfo();
    }
}
