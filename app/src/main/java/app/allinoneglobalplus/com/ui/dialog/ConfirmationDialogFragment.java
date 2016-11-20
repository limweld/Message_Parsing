package app.allinoneglobalplus.com.ui.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.allinoneglobalplus.com.R;

/**
 * Created by ADMIN on 7/21/2016.
 */

public class ConfirmationDialogFragment extends DialogFragment {

    Button button_Ok;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false);

        getDialog().setTitle("Successfully Sent!");
        button_Ok = (Button) rootView.findViewById(R.id.dismiss);
        button_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        return rootView;

    }
}