package tech.touch.threeds.android.sampleApp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import tech.touch.threeds.android.sampleApp.R;
import tech.touch.threeds.android.sdk.entities.registration.TTDac;

public class DacDialogFragment extends DialogFragment {
    private DacDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setMessage(R.string.dac_dialog_message)
                .setTitle(R.string.dac_dialog_title)
                .setView(inflater.inflate(R.layout.fragment_dac_dialog, null))
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText) getDialog().findViewById(R.id.dac_dialog_input);
                        TTDac dac = new TTDac(editText.getText().toString());
                        mListener.onDacDialogPositiveClick(dac);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {}
                });
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DacDialogListener) context;
    }

    public interface DacDialogListener {
        void onDacDialogPositiveClick(TTDac dac);
    }
}
