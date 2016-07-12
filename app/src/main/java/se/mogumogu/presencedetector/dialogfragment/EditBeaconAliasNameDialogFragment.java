package se.mogumogu.presencedetector.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import se.mogumogu.presencedetector.R;
import se.mogumogu.presencedetector.model.SubscribedBeacon;

public class EditBeaconAliasNameDialogFragment extends DialogFragment {

    private static final String TAG = EditBeaconAliasNameDialogFragment.class.getSimpleName();
    public static final String SUBSCRIBED_BEACON = "se.mogumogu.presencedetector.SUBSCRIBED_BEACON";


    private EditBeaconAliasNameDialogListener listener;
    private SubscribedBeacon subscribedBeacon;

    public static DialogFragment newInstance(SubscribedBeacon subscribedBeacon) {

        DialogFragment dialogFragment = new EditBeaconAliasNameDialogFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(SUBSCRIBED_BEACON, subscribedBeacon);
        dialogFragment.setArguments(arguments);

        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EditBeaconAliasNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SubscriptionDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        subscribedBeacon = (SubscribedBeacon) getArguments().getSerializable(SUBSCRIBED_BEACON);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        final View view = View.inflate(getContext(), R.layout.dialog_fragment_edit_beacon_alias_name, null);
        final EditText aliasNameEditText = (EditText) view.findViewById(R.id.alias_name_edit);

        if (subscribedBeacon != null) {

            aliasNameEditText.setText(subscribedBeacon.getAliasName());

        } else {

            Log.d(TAG, "subscribed beacon is null");
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.dialog_fragment_edit_beacon_alias_name_title);

        dialogBuilder.setView(view)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        listener.onDialogPositiveClick(EditBeaconAliasNameDialogFragment.this, view);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        listener.onDialogNegativeClick(EditBeaconAliasNameDialogFragment.this);
                    }
                });

        return dialogBuilder.create();
    }

    public interface EditBeaconAliasNameDialogListener {

        void onDialogPositiveClick(DialogFragment dialog, View view);

        void onDialogNegativeClick(DialogFragment dialog);
    }
}
