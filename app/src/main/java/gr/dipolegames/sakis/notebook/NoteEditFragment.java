package gr.dipolegames.sakis.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private ImageButton noteCatButton;
    private Note.Category savedButtonCategory;
    private  AlertDialog categoryDialogObject;

    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        EditText title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        EditText message = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButton);

        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA));

        Note.Category category = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
        noteCatButton.setImageResource(Note.categoryToDrawable(category));

        BuildCategoryDialog();

        noteCatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialogObject.show();
            }
        });

        // Inflate the layout for this fragment
        return fragmentLayout;
    }


    private void BuildCategoryDialog(){
        final String[] categories = new String[]{"Personal", "Technical", "Quote", "Finance"};
        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose note type");
        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {

                categoryDialogObject.cancel();

                switch (item){
                    case 0:
                        savedButtonCategory = Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.p);
                        break;
                    case 1:
                        savedButtonCategory = Note.Category.TECHINAL;
                        noteCatButton.setImageResource(R.drawable.t);
                        break;
                    case 2:
                        savedButtonCategory = Note.Category.QUOTE;
                        noteCatButton.setImageResource(R.drawable.q);
                        break;
                    case 3:
                        savedButtonCategory = Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.f);
                        break;
                }
            }
        });

        categoryDialogObject = categoryBuilder.create();
    }
}
