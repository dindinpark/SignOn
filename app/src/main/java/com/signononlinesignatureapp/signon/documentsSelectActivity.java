package com.signononlinesignatureapp.signon;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Naseebah on 26/02/16.
 */
public class documentsSelectActivity extends ListActivity{
    private documentsArrayAdapter mAdapter;
    private documentsArrayAdapter signedAdapter;
    private documentsArrayAdapter waitAdapter;

    private List<documents> waitdocuments;
    private Button documentsSelectAddButton;
    ListView signed;
    ListView wait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_document);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(new MyMultiClickListener());
        waitdocuments = new ArrayList<documents>();
        mAdapter = new documentsArrayAdapter(this);
        setListAdapter(mAdapter);
        signed =(ListView) findViewById(R.id.selectdocumentsignedListView);
        wait=(ListView) findViewById(R.id.selectdocumentwaitListView);
        signedAdapter= new documentsArrayAdapter(this);
        waitAdapter=new documentsArrayAdapter(this);
        signed.setAdapter(signedAdapter);
        wait.setAdapter(waitAdapter);



       // documentsSelectAddButton=(Button) findViewById(R.id.documentsSelectAddButton);
    }

    private class MyMultiClickListener implements AbsListView.MultiChoiceModeListener {

        private ArrayList<documents> mdocumentsToDelete = new ArrayList<documents>();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context, menu);
            mode.setTitle(R.string.context_delete_title);
            return true; // gives tactile feedback
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.context_delete:
                    deleteSelectedItems();
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            documents item = (documents) getListAdapter().getItem(position);
            if (checked) {
                mdocumentsToDelete.add(item);
            } else {
                mdocumentsToDelete.remove(item);
            }
            mode.setTitle("Selected " + mdocumentsToDelete.size() + " documentss");
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // purposefully empty
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mdocumentsToDelete = new ArrayList<documents>();
            return true;
        }

        private void deleteSelectedItems() {
            for (documents documents : mdocumentsToDelete) {
                mAdapter.removeItem(documents);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        final documents currentdocuments = (documents) getListAdapter().getItem(position);
        session.base64=currentdocuments.getKey();
        super.onListItemClick(l, v, position, id);
    }
    //////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                // add
                addItem();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addItem() {
/*
        DialogFragment df = new DialogFragment() {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.content_main, container);
                getDialog().setTitle("Add a movie and quote");
                final Button confirmButton = (Button) view.findViewById(R.id.add);
                final Button cancelButton = (Button) view.findViewById(R.id.birth_txt);
                final EditText movieTitleEditText = (EditText) view.findViewById(R.id.selectdocumentsName);
                final EditText movieQuoteEditText = (EditText) view.findViewById(R.id.selectdocumentsID);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String movieTitleText = movieTitleEditText.getText().toString();
                        String movieQuoteText = movieQuoteEditText.getText().toString();
                        Toast.makeText(documentsSelectActivity.this,
                                "Got the title " + movieTitleText + " and quote " + movieQuoteText, Toast.LENGTH_LONG)
                                .show();
                        documents currentQuote = new documents(null,"6666", movieTitleText, movieQuoteText);
                        mAdapter.addItem(currentQuote);
                        dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                return view;
            }
        };
        df.show(getFragmentManager(), "");*/
    }
    /*public void documentsSelectAddButtonClick(View v){
        startActivity(new Intent(documentsSelectActivity.this,CapturedocumentsActivity.class));

    }*/


}

