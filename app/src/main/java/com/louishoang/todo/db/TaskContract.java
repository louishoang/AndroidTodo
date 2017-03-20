package com.louishoang.todo.db;

import android.provider.BaseColumns;

/**
 * Created by louishoang on 3/19/17.
 */

public class TaskContract {
  public static final String DB_NAME = "com.louishoang.todo.db";
  public static final int DB_VERSION = 1;

  public class TaskEntry implements BaseColumns{
    public static final String TABLE = "tasks";

    public static final String COL_TASK_TITLE = "title";
  }
}
