package tech.touch.threeds.android.sampleApp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tech.touch.threeds.android.sampleApp.R;
import tech.touch.threeds.android.sdk.entities.financialInstitution.TTFinancialInstitutionDetails;

public class CardDetailsDialogFragment extends DialogFragment {
    public static final String FI_DETAILS_KEY = "FI_DETAILS_KEY";

    private TTFinancialInstitutionDetails mFiDetails;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle;
        if(savedInstanceState != null) {
            bundle = savedInstanceState;
        } else if ((bundle = getArguments()) == null) {
            throw new IllegalStateException();
        }
        mFiDetails = (TTFinancialInstitutionDetails) bundle.getParcelable(FI_DETAILS_KEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        RelativeLayout dialogView = (RelativeLayout) inflater.inflate(R.layout.fragment_card_details_dialog, null);
        TextView tvIssuer = (TextView) dialogView.findViewById(R.id.card_details_issuer);
        tvIssuer.setText(mFiDetails.getName());
        TextView tvPhoneNumber = (TextView) dialogView.findViewById(R.id.card_details_phoneNumber);
        tvPhoneNumber.setText(mFiDetails.getPhoneNumber());
        builder.setTitle(R.string.cardDetails_dialog_title)
                .setView(dialogView)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(FI_DETAILS_KEY, mFiDetails);
    }
}
