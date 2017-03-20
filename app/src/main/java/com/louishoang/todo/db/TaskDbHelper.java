package com.louishoang.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by louishoang on 3/19/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {
  public TaskDbHelper(Context context){
    super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String createtable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                           TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                           TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
    db.execSQL(createtable);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
    onCreate(db);
  }
}
