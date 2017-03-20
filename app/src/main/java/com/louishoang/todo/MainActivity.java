package com.louishoang.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.louishoang.todo.db.TaskContract;
import com.louishoang.todo.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActitivy";
  private TaskDbHelper mHelper;
  private ListView mTaskListView;
  private ArrayAdapter<String> mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mHelper = new TaskDbHelper(this);
    mTaskListView = (ListView)findViewById(R.id.list_todo);
    updateUI();
  }

  private void updateUI(){
    ArrayList<String> taskList = new ArrayList<>();
    SQLiteDatabase db = mHelper.getReadableDatabase();
    Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
      new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
      null, null, null, null, null);
    while(cursor.moveToNext()) {
      int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
      taskList.add(cursor.getString(idx));
    }

    if(mAdapter == null){
      mAdapter = new ArrayAdapter<>(this,
                                     R.layout.item_todo,
                                     R.id.task_title,
                                     taskList);
      mTaskListView.setAdapter(mAdapter);
    } else {
      mAdapter.clear();
      mAdapter.addAll(taskList);
      mAdapter.notifyDataSetChanged();
    }

    cursor.close();
    db.close();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case R.id.action_add_task:

        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
          .setTitle("Add a new task")
          .setMessage("What do you do want to do next?")
          .setView(taskEditText)
          .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
              String task = String.valueOf(taskEditText.getText());
              SQLiteDatabase db = mHelper.getWritableDatabase();
              ContentValues values = new ContentValues();
              values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
              db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
              db.close();
              updateUI();
            }
          })
          .setNegativeButton("Cancel", null)
          .create();

        dialog.show();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
