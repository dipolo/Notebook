package gr.dipolegames.sakis.notebook;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private ArrayList<Note> notes;
    private  NoteAdapter noteAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

/*        notes = new ArrayList<Note>();

        notes.add(new Note("this is a new note title sakis sakis sakis sakis sakis sakis sakis sakis sakis sakis sakis sakis ",
                "this the body of our note this the body of our note this the body of our note this the body of our note this the body of our note this the body of our note this the body of our note this the body of our note ", Note.Category.PERSONAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.FINANCE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.QUOTE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.TECHINAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.PERSONAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.QUOTE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.FINANCE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.PERSONAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.TECHINAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.QUOTE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.PERSONAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.FINANCE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.QUOTE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.TECHINAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.PERSONAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.QUOTE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.FINANCE));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.PERSONAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.TECHINAL));
        notes.add(new Note("this is a new note title", "this the body of our note", Note.Category.QUOTE));
*/

        NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes = dbAdapter.getAllNotes();
        dbAdapter.close();

        noteAdapter = new NoteAdapter(getActivity(), notes);
        setListAdapter(noteAdapter);

        //getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.black));
        //getListView().setDividerHeight(1);

        registerForContextMenu(getListView());

    }

    @Override public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        LaunchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;
        switch (item.getItemId()){
            case R.id.edit:
                //Do something here
                LaunchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("menu Clicks", "We pressed edit!");
                return  true;
        }
        return super.onContextItemSelected(item);
    }

    private void LaunchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position){
        Note note = (Note) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getId());

        switch (ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;
        }
        startActivity((intent));
    }
}
