package tech.touch.threeds.android.sampleApp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.touch.threeds.android.sampleApp.R;
import tech.touch.threeds.android.sampleApp.fragments.CardDetailsDialogFragment;
import tech.touch.threeds.android.sampleApp.helpers.CardListAdapter;
import tech.touch.threeds.android.sdk.TTError;
import tech.touch.threeds.android.sdk.Touchtech;
import tech.touch.threeds.android.sdk.callbacks.TTCallback;
import tech.touch.threeds.android.sdk.callbacks.TTDetailsCallback;
import tech.touch.threeds.android.sdk.entities.card.TTCard;
import tech.touch.threeds.android.sdk.entities.financialInstitution.TTFinancialInstitutionDetails;
import tech.touch.threeds.android.sdk.usecases.cards.TTCardManager;

public class ManageCardsActivity extends AppCompatActivity
        implements CardListAdapter.CardActionListener, TTCallback, TTDetailsCallback<TTFinancialInstitutionDetails>{
    private TTCardManager mCardManager;
    private List<TTCard> mCards;
    private CardListAdapter mCardListAdapter;

    @BindView(R.id.manage_cards_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.manage_list)
    ListView lvCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cards);
        ButterKnife.bind(this);
        mCards = new ArrayList<>();
        mCardManager = Touchtech.client().getCardManager();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ManageCardsActivity.this.onRefresh();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardListAdapter = new CardListAdapter(this, this, mCards);
        ListView listView = (ListView) findViewById(R.id.manage_list);
        listView.setAdapter(mCardListAdapter);
    }

    private void onFabClick() {
        Intent intent = new Intent(this, CardRegistrationActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CardRegistrationActivity.REQUEST_REGISTER) {
            if (resultCode == RESULT_OK) {
                TTCard card = (TTCard) data.getExtras().get(CardRegistrationActivity.RESULT_CARD_KEY);
                mCards.add(card);
                Snackbar.make(findViewById(R.id.manage),
                        "Card successfully registered.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void onRefresh() {
        updateAdapter();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void updateAdapter() {
        mCardListAdapter.clear();
        mCardListAdapter.addAll(mCards);
        mCardListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickShowDetails(TTCard card) {
        mCardManager.getFinancialInstitutionDetails(card, this);
    }

    @Override
    public void onClickRemove(TTCard card) {
        mCardManager.unregisterCard(card.getToken(), this);
    }

    /* Show card details */

    @Override
    public void onDetailsRetrieved(TTFinancialInstitutionDetails details) {
        DialogFragment dialog = new CardDetailsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CardDetailsDialogFragment.FI_DETAILS_KEY, details);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "CardDetailsDialogFragment");
    }

    @Override
    public void onDetailsRetrievalError(TTError ttError) {
        Snackbar.make(findViewById(R.id.manage),
                "Something wrong happened.", Snackbar.LENGTH_LONG).show();
    }

    /* Remove a card */

    @Override
    public void onSuccess() {
        updateAdapter();
        Snackbar.make(findViewById(R.id.manage),
                "The card has been successfully unregistered.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onError(TTError ttError) {
        Snackbar.make(findViewById(R.id.manage),
                "Something wrong happened.", Snackbar.LENGTH_LONG).show();
    }

}