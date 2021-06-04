package sg.edu.rp.c346.s19024292.emailapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private ArrayList<Contact> contacts;
    private Context context;
    private TextView textView;

    public ContactAdapter(Context context, int resource, ArrayList<Contact> contacts) {
        super(context, resource, contacts);
        // Store the food that is passed to this adapter
        this.contacts= contacts;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    // getView() is the method ListView will call to get the
    //  View object every time ListView needs a row
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // Get the TextView object
        textView = rowView.findViewById(R.id.textView);
        // Get the ImageView object

        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        Contact currentContact = contacts.get(position);
        // Set the TextView to show the food

        textView.setText(currentContact.getEmail());
        // Return the nicely done up View to the ListView
        return rowView;
    }
}