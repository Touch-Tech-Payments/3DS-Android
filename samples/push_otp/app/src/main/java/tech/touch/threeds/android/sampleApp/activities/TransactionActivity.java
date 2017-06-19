package tech.touch.threeds.android.sampleApp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.touch.threeds.android.sampleApp.R;
import tech.touch.threeds.android.sdk.TTError;
import tech.touch.threeds.android.sdk.callbacks.TTAuthCallback;
import tech.touch.threeds.android.sdk.callbacks.TTCallback;
import tech.touch.threeds.android.sdk.callbacks.TTDetailsCallback;
import tech.touch.threeds.android.sdk.callbacks.resumers.TTResumer;
import tech.touch.threeds.android.sdk.entities.card.TTCardAuthMethod;
import tech.touch.threeds.android.sdk.entities.ticket.TTTransTicket;
import tech.touch.threeds.android.sdk.entities.transaction.TTTransDetails;
import tech.touch.threeds.android.sdk.usecases.transactions.TTTransAuthenticator;

public class TransactionActivity
        extends Activity
        implements TTDetailsCallback<TTTransDetails>, TTAuthCallback, TTCallback {
    public static final String TRANS_TICKET_KEY = "TRANS_TICKET";

    public static final int REQUEST_AUTHENTICATE = 1;

    private TTTransAuthenticator mTransAuthenticator;
    private TTTransDetails mTransDetails;
    private TTTransTicket mTransTicket;

    @BindView(R.id.authenticate_layout)
    RelativeLayout layout;
    @BindView(R.id.authenticate_amount)
    TextView tvAmount;
    @BindView(R.id.authenticate_merchant)
    TextView tvMerchant;

    public static void start(Activity activity, TTTransTicket transTicket) {
        Intent intent = new Intent(activity, TransactionActivity.class);
        intent.putExtra(TRANS_TICKET_KEY, transTicket);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mTransTicket = savedInstanceState.getParcelable(TRANS_TICKET_KEY);
        }
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mTransTicket == null) {
            mTransTicket = getIntent().getParcelableExtra(TRANS_TICKET_KEY);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(TRANS_TICKET_KEY, mTransTicket);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTransAuthenticator.destroy();
    }

    /* Button handling */

    @OnClick(R.id.authenticate_btn_accept)
    public void onAccept() {
        mTransAuthenticator.accept(mTransTicket, mTransDetails.getCard().getToken(), this);
    }

    @OnClick(R.id.authenticate_btn_decline)
    public void onCancel() {
        mTransAuthenticator.decline(mTransTicket, this);
    }

    /* Transaction details callbacks */

    @Override
    public void onDetailsRetrieved(TTTransDetails details) {
        tvAmount.setText(details.getAmount().toString());
        tvMerchant.setText(details.getMerchantName());
        layout.setVisibility(RelativeLayout.VISIBLE);
    }

    @Override
    public void onDetailsRetrievalError(TTError e) {
        Toast.makeText(this, "Impossible to get transaction details.", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED);
        finish();
    }

    /* Accept callbacks */

    @Override
    public void onAuthWaiting(TTCardAuthMethod ttCardAuthMethod, TTResumer ttResumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onAuthResumed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onAuthMethodFailure() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onAuthFailed(TTError e) {
        Toast.makeText(this, "Transaction successfully authenticated.", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onAuthSucceeded() {
        Toast.makeText(this, "Transaction successfully authenticated.", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    /* Decline callbacks */

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Transaction declined.", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onError(TTError e) {
        Toast.makeText(this, "The transaction couldn't be declined.", Toast.LENGTH_LONG).show();
    }
}