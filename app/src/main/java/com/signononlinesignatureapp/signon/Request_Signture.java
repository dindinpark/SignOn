package com.signononlinesignatureapp.signon;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Request_Signture extends ListActivity {

    Intent EditRequestActivity;
    private RequestArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_request_signature);
        EditRequestActivity= new Intent(this,main_request__edit.class);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(new MyMultiClickListener());
        mAdapter = new RequestArrayAdapter(this);
        setListAdapter(mAdapter);
    }

    private class MyMultiClickListener implements AbsListView.MultiChoiceModeListener {

        private ArrayList<Request> mRequestsToDelete = new ArrayList<Request>();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context2, menu);
            mode.setTitle("Select request to delete");
            return true; // gives tactile feedback
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.context_delete:
                    deleteSelectedItems();
                    mode.finish();
                    return true;
                case R.id.context_update:
                    updateSelectedItems();
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            Request item = (Request) getListAdapter().getItem(position);
            if (checked) {
                mRequestsToDelete.add(item);
            } else {
                mRequestsToDelete.remove(item);
            }
            mode.setTitle("Selected " + mRequestsToDelete.size() + " requests");
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // purposefully empty
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mRequestsToDelete = new ArrayList<Request>();
            return true;
        }

        private void deleteSelectedItems() {
            for (Request request : mRequestsToDelete) {
                mAdapter.removeItem(request);
            }
            mAdapter.notifyDataSetChanged();
        }

        private void updateSelectedItems() {
            for (final Request request : mRequestsToDelete) {
                EditRequestActivity.putExtra("currentRequestKey",request.getKey());
                startActivity(EditRequestActivity);
            }
        }

    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        final Request currentRequest = (Request) getListAdapter().getItem(position);

        DialogFragment df = new DialogFragment() {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.activity_request__edit, container);
                getDialog().setTitle("Update Request");
                final Button cancelButton = (Button) view.findViewById(R.id.add_dialog_ok);
                final EditText SignerEmailEditText = (EditText) view.findViewById(R.id.add_dialog_signer_email);
                final EditText SignerOrderEditText = (EditText) view.findViewById(R.id.add_dialog_signer_order);

                // pre-populate
                SignerEmailEditText.setText(currentRequest.getSignerEmail());
                SignerOrderEditText.setText(currentRequest.getOrder());

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String SignerEmail = SignerEmailEditText.getText().toString();
                        String SignerOrder = SignerOrderEditText.getText().toString();
                        if ( EmailExist(SignerEmail)) {
                            mAdapter.updateItem(currentRequest, SignerEmail, SignerOrder);

                        }
                        else
                            Toast.makeText(Request_Signture.this, "email not found", Toast.LENGTH_SHORT).show();
                    }
                };

                SignerEmailEditText.addTextChangedListener(textWatcher);
                SignerOrderEditText.addTextChangedListener(textWatcher);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                return view;
            }
        };
        df.show(getFragmentManager(), "");

        super.onListItemClick(l, v, position, id);*/
    }


        public void AddSignerButtonOnClick(View view) {
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("Add Signer");
            dialog.setContentView(R.layout.activity_request__signture);
            dialog.show();

            final EditText etEmail, etOrder;
            etEmail = (EditText) dialog.findViewById(R.id.add_dialog_signer_email);
            etOrder = (EditText) dialog.findViewById(R.id.add_dialog_signer_order);

            Button btnRequest = (Button) dialog.findViewById(R.id.add_dialog_ok);

            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email, order;
                    email = etEmail.getText().toString();
                    order = etOrder.getText().toString();
                    Request request = new Request(null, email, "", session.userkey, order, "waiting");
                    CheckEmail(request);
                    dialog.cancel();
                }
            });

        }


        private void CheckEmail(final Request request) {
            Firebase.setAndroidContext(getApplicationContext());
            Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users");
            final Query queryRef = ref.orderByChild("Email").equalTo(request.getSignerEmail());

            final ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            AddRequest(request);
                            break;
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "email not found", Toast.LENGTH_LONG);
                        toast.show();

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            };
            queryRef.addListenerForSingleValueEvent(listener);
        }

        private void AddRequest(Request request) {
            Firebase reqRef = new Firebase("https://torrid-heat-4458.firebaseio.com/requests");
            Map<String, String> newRequest = new HashMap<String, String>();
            newRequest.put("SignerEmail", request.getSignerEmail());
            newRequest.put("rDocumentId", "");
            newRequest.put("requesterID", request.getRequesterID());
            newRequest.put("signingSeq", request.getOrder());
            newRequest.put("status", "waiting");
            reqRef.push().setValue(newRequest);
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();


        }
}