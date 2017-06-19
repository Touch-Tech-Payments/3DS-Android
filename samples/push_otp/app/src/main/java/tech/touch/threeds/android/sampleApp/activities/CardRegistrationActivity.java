package tech.touch.threeds.android.sampleApp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.touch.threeds.android.sampleApp.R;
import tech.touch.threeds.android.sampleApp.fragments.DacDialogFragment;
import tech.touch.threeds.android.sdk.TTError;
import tech.touch.threeds.android.sdk.Touchtech;
import tech.touch.threeds.android.sdk.callbacks.TTCallback;
import tech.touch.threeds.android.sdk.callbacks.TTRegCallback;
import tech.touch.threeds.android.sdk.callbacks.resumers.TTResumer;
import tech.touch.threeds.android.sdk.entities.card.TTCard;
import tech.touch.threeds.android.sdk.entities.card.TTCardAuthMethod;
import tech.touch.threeds.android.sdk.entities.registration.TTDac;
import tech.touch.threeds.android.sdk.usecases.cards.TTCardManager;
import tech.touch.threeds.android.sdk.entities.card.TTCardToken;

public class CardRegistrationActivity
        extends AppCompatActivity implements TTRegCallback {
    public static final int REQUEST_REGISTER = 1;

    public static final String RESULT_CARD_KEY = "RESULT_CARD_KEY";

    private TTCardToken mCardToken;
    private TTDac mDac;
    private TTCardManager mCardManager;

    @BindView(R.id.cardRegistration_cardToken)
    TextView tvPan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_registration);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardManager = Touchtech.client().getCardManager();
        mCardToken = new TTCardToken("2763ed8840d8a1536858186d58e1543c92c9afa1b42a0da4409dfc33f06a0a71");
        mDac = new TTDac("806697"); // This should be retrieved from your backend
        tvPan.setText("****");
    }

    /* Button handling */

    @OnClick(R.id.cardRegistration_btn_confirm)
    public void onConfirm() {
        mCardManager.registerCard(mCardToken, mDac, this);
    }

    @OnClick(R.id.cardRegistration_btn_cancel)
    public void onDismiss() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /* Card registration */

    @Override
    public void onRegWaiting(TTCardAuthMethod ttCardAuthMethod, TTResumer ttResumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onRegResumed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onAuthMethodFailure() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onRegFailed(TTError e) {
        Snackbar snackbar;
        switch (e) {
            case CARD_ALREADY_REGISTERED:
                snackbar = Snackbar.make(findViewById(R.id.register_layout),
                        "The card has already been registered.", Snackbar.LENGTH_LONG);
                break;
            case INCORRECT_DAC:
                snackbar = Snackbar.make(findViewById(R.id.register_layout),
                        "The dac is incorrect.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Resend", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onConfirm();
                    }
                });
                break;
            case CARD_BLOCKED:
                snackbar = Snackbar.make(findViewById(R.id.register_layout),
                        "The card is blocked. Please contact the card issuer.", Snackbar.LENGTH_LONG);
                break;
            default:
                snackbar = Snackbar.make(findViewById(R.id.register_layout),
                        "Something wrong happened. Please try again later.", Snackbar.LENGTH_LONG);
        }
        snackbar.show();
    }

    @Override
    public void onRegSucceeded(TTCard card) {
        setResult(RESULT_OK);
        Intent intent = new Intent();
        intent.putExtra(RESULT_CARD_KEY, card);
        setIntent(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCardManager.destroy();
    }
}
