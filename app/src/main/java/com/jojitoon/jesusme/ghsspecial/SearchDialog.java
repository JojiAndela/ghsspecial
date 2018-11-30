package com.jojitoon.jesusme.ghsspecial;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.jojitoon.jesusme.ghsspecial.HymnSection;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDialog extends DialogFragment implements View.OnClickListener {

    Button search, back;
    Communicator comm;
    EditText num;
    EditText test;
    int searchparam;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the Communicator so we can send events to the host
            comm = (Communicator) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_dialog, null);

        search = (Button) v.findViewById(R.id.search);
        back = (Button) v.findViewById(R.id.back);
        num = (EditText) v.findViewById(R.id.editsearch);
        test = (EditText) v.findViewById(R.id.edittest);


        search.setOnClickListener(this);
        back.setOnClickListener(this);
        setCancelable(false);


        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.search) {
            if (!num.getText().toString().isEmpty()) {
                searchparam = Integer.parseInt(num.getText().toString());
            } else {
                searchparam = 0;
            }

            if (searchparam == 0) {
                Toast.makeText(getActivity(), "Must Enter a Number! Try from 1 - 260...", Toast.LENGTH_LONG).show();

            } else {

                if (searchparam < 1 || searchparam > 260) {
                    Toast.makeText(getActivity(), "Out of Range! Try from 1 - 260...", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getContext(), Hymn.class);
                    int num = searchparam - 1;
                    PlaceholderFragment.state = true;

                    intent.putExtra("id", num);
                    startActivity(intent);

                    //comm.onDialogMessage(searchparam);
                    dismiss();
                }
            }
        } else {
            dismiss();
        }
    }


    interface Communicator {
        public void onDialogMessage(int i);
    }
}
