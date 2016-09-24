package com.codefororlando.streettrees.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by johnli on 9/24/16.
 */
public class PageFragment extends Fragment {

    protected PageFragmentListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (PageFragmentListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PageFragmentListener");
        }
    }

    public interface PageFragmentListener {
        void next(Bundle bundle);
        void previous();
    }
}
