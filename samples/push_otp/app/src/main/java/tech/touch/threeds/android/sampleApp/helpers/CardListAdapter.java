package tech.touch.threeds.android.sampleApp.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tech.touch.threeds.android.sampleApp.R;
import tech.touch.threeds.android.sdk.entities.card.TTCard;

public class CardListAdapter extends ArrayAdapter<TTCard> {
    private final CardActionListener mListener;

    public CardListAdapter(@NonNull Context context,
                           @NonNull CardActionListener listener,
                           @NonNull List<TTCard> cards) {
        super(context, 0, cards);
        mListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final TTCard card = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.content_card_actions, parent, false);
        }
        // Pan
        TextView tvPan = (TextView) convertView.findViewById(R.id.card_options_pan);
        String cardTokenStr = card.getToken().getValue();
        tvPan.setText("**** " + cardTokenStr.substring(cardTokenStr.length()));
        // Show details button
        Button btnDetails = (Button) convertView.findViewById(R.id.card_options_btn_details);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickShowDetails(card);
            }
        });
        // Remove button
        Button btnRemove = (Button) convertView.findViewById(R.id.card_options_btn_remove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickRemove(card);
            }
        });
        return convertView;
    }

    public interface CardActionListener {
        void onClickShowDetails(TTCard card);
        void onClickRemove(TTCard card);
    }

}
