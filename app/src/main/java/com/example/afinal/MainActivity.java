package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnFind;
    EditText etName, etAuthor;
    DBHelper dbHelper;

    MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v)
    {
        String name = etName.getText().toString();
        String author = etAuthor.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId())
        {

            case R.id.addSong:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_AUTHOR, author);

                database.insert(DBHelper.TABLE_MUSIC, null, contentValues);
                break;

            case R.id.compareSong:
                Cursor c = database.rawQuery("SELECT * FROM music WHERE TRIM(name) = '"+name.trim()+"'", null);
                c.moveToFirst();

                musicPlayer = MediaPlayer.create(this, R.raw.rise);

                if (c.toString() == musicPlayer.getTrackInfo().toString())
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Песня найдена!:)", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Песня не найдена!:(", Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
        }
        dbHelper.close();
    }
}