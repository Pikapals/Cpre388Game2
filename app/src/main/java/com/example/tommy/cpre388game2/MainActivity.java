package com.example.tommy.cpre388game2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



public class MainActivity extends Activity {
    // TAG is used to debug in Android logcat console

    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_SCORE, };

    private static final Pair[] bitmap = {new Pair(0, 0), new Pair(1, 0), new Pair(2, 0), new Pair(3, 0), new Pair(4, 0), new Pair(0, 1), new Pair(0, 2), new Pair(0, 3), new Pair(0, 4), new Pair(1, 4), new Pair(2, 4), new Pair(3, 4), new Pair(4, 4), new Pair(4, 3), new Pair(4, 2), new Pair(3, 2), new Pair(2, 2),
        new Pair(6, 0), new Pair(6, 1), new Pair(6, 2), new Pair(6, 3), new Pair(6, 4), new Pair(7, 0), new Pair(8, 0), new Pair(9, 0), new Pair(10, 0), new Pair(10, 1), new Pair(10, 2), new Pair(10, 3), new Pair(10, 4), new Pair(7, 2), new Pair(8, 2), new Pair(9, 2),
        new Pair(12, 0), new Pair(12, 1), new Pair(12, 2), new Pair(12, 3), new Pair(12, 4), new Pair(13,1), new Pair(14,2), new Pair(15,1), new Pair(16, 0), new Pair(16, 1), new Pair(16, 2), new Pair(16, 3), new Pair(16, 4),
        new Pair(18, 0), new Pair(19, 0), new Pair(20, 0), new Pair(21, 0), new Pair(22, 0), new Pair(18, 1), new Pair(18, 2), new Pair(19, 2), new Pair(20, 2), new Pair(21, 2), new Pair(22, 2), new Pair(18, 3), new Pair(18, 4), new Pair(19, 4), new Pair(20, 4), new Pair(21, 4), new Pair(22, 4),
        new Pair(0, 6), new Pair(0, 7), new Pair(0, 8), new Pair(0, 9), new Pair(0, 10), new Pair(1, 6), new Pair(2, 6), new Pair(3, 6), new Pair(4, 6), new Pair(4, 7), new Pair(4, 8), new Pair(4, 9), new Pair(4, 10), new Pair(1, 10), new Pair(2, 10), new Pair(3, 10),
        new Pair(6, 6), new Pair(6, 7), new Pair(7, 8), new Pair(7, 9), new Pair(8, 10), new Pair(9, 9), new Pair(9, 8), new Pair(10, 7), new Pair(10, 6),
        new Pair(12, 6), new Pair(12, 7), new Pair(12, 8), new Pair(12, 9), new Pair(12, 10), new Pair(13, 6), new Pair(14, 6), new Pair(15, 6), new Pair(16, 6), new Pair(13, 8), new Pair(14, 8), new Pair(15, 8), new Pair(16, 8), new Pair(13, 10), new Pair(14, 10), new Pair(15, 10), new Pair(16, 10),
        new Pair(18, 6), new Pair(18, 7), new Pair(18, 8), new Pair(18, 9), new Pair(18, 10), new Pair(19, 6), new Pair(20, 6), new Pair(21, 6), new Pair(22, 6), new Pair(22, 7), new Pair(22, 8), new Pair(21, 8), new Pair(20, 8), new Pair(19, 8), new Pair(20, 9), new Pair(21, 10)};

    private static  final Pair[] scoreMap = { new Pair(0, 21), new Pair(1, 21), new Pair(2, 21), new Pair(3, 21), new Pair(4, 21), new Pair(0, 22), new Pair(0, 23), new Pair(1, 23), new Pair(2, 23), new Pair(3, 23), new Pair(4, 23), new Pair(4, 24), new Pair(4, 25), new Pair(3, 25), new Pair(2, 25), new Pair(1, 25), new Pair(0, 25),
            new Pair(6, 21), new Pair(7, 21), new Pair(8, 21), new Pair(9, 21), new Pair(10, 21), new Pair(6, 22), new Pair(6, 23), new Pair(6, 24), new Pair(6, 25), new Pair(7, 25), new Pair(8, 25), new Pair(9, 25), new Pair(10, 25),
            new Pair(12, 21), new Pair(12, 22), new Pair(12, 23), new Pair(12, 24), new Pair(12, 25), new Pair(13, 21), new Pair(14, 21), new Pair(15, 21), new Pair(16, 21), new Pair(16, 22), new Pair(16, 23), new Pair(16, 24), new Pair(16, 25), new Pair(13, 25), new Pair(14, 25), new Pair(15, 25),
            new Pair(18, 21), new Pair(18, 22), new Pair(18, 23), new Pair(18, 24), new Pair(18, 25), new Pair(19, 21), new Pair(20, 21), new Pair(21, 21), new Pair(22, 21), new Pair(22, 22), new Pair(22, 23), new Pair(21, 23), new Pair(20, 23), new Pair(19, 23), new Pair(20, 24), new Pair(21, 25),
            new Pair(24, 21), new Pair(25, 21), new Pair(26, 21), new Pair(27, 21), new Pair(28, 21), new Pair(24, 22), new Pair(24, 23), new Pair(25, 23), new Pair(26, 23), new Pair(27, 23), new Pair(28, 23), new Pair(24, 24), new Pair(24, 25), new Pair(25, 25), new Pair(26, 25), new Pair(27, 25), new Pair(28, 25),
            new Pair(30, 21), new Pair(31, 21), new Pair(30, 22), new Pair(31, 22), new Pair(30, 24), new Pair(31, 24), new Pair(30, 25), new Pair(31, 25)
    };

    private HashMap<Integer, Pair[]> numbersMap = new HashMap<Integer, Pair[]>();

    private static final Pair[] numberZero = {new Pair(1, 27), new Pair(2, 27), new Pair(3, 27), new Pair(0, 28), new Pair(0, 29), new Pair(0, 30), new Pair(1, 31), new Pair(2, 31), new Pair(3, 31), new Pair(4, 28), new Pair(4, 29), new Pair(4, 30)};
    private static final Pair[] numberOne = {new Pair(0, 27), new Pair(1, 27), new Pair(1, 28), new Pair(1, 29), new Pair(1, 30), new Pair(1, 31)};
    private static final Pair[] numberTwo = {new Pair(0, 28), new Pair(1, 27), new Pair(2, 27), new Pair(3, 27), new Pair(4, 28), new Pair(3, 29), new Pair(2, 30), new Pair(0, 31), new Pair(1, 31), new Pair(2, 31), new Pair(3, 31), new Pair(4, 31)};
    private static final Pair[] numberThree = {new Pair(0, 27), new Pair(1, 27), new Pair(2, 27), new Pair(3, 27), new Pair(4, 27), new Pair(4, 28), new Pair(4, 29), new Pair(3, 29), new Pair(2, 29), new Pair(1, 29), new Pair(4, 30), new Pair(4, 31), new Pair(3, 31), new Pair(2, 31), new Pair(1, 31), new Pair(0, 31)};
    private static final Pair[] numberFour = {new Pair(0, 27), new Pair(0, 28), new Pair(0, 29), new Pair(1, 29), new Pair(2, 29), new Pair(3, 29), new Pair(4, 29), new Pair(4, 27), new Pair(4, 28), new Pair(4, 30), new Pair(4, 31)};
    private static final Pair[] numberFive = {new Pair(4, 27), new Pair(3, 27), new Pair(2, 27), new Pair(1, 27), new Pair(0, 28), new Pair(0, 29), new Pair(1, 29), new Pair(2, 29), new Pair(3, 29), new Pair(4, 29), new Pair(4, 30), new Pair(4, 31), new Pair(3, 31), new Pair(2, 31), new Pair(1, 31), new Pair(0, 31)};
    private static final Pair[] numberSix = {new Pair(4, 27), new Pair(3, 27), new Pair(2, 27), new Pair(1, 27), new Pair(0, 27), new Pair(0, 28), new Pair(0, 29), new Pair(1, 29), new Pair(2, 29), new Pair(3, 29), new Pair(4, 29), new Pair(4, 30), new Pair(4, 31), new Pair(3, 31), new Pair(2, 31), new Pair(1, 31), new Pair(0, 31), new Pair(0, 30)};
    private static final Pair[] numberSeven = {new Pair(0, 27), new Pair(1, 27), new Pair(2, 27), new Pair(3, 27), new Pair(4, 27), new Pair(4, 28), new Pair(3, 29), new Pair(2, 30), new Pair(1, 31)};
    private static final Pair[] numberEight = {new Pair(0, 27), new Pair(1, 27), new Pair(2, 27), new Pair(3, 27), new Pair(4, 27), new Pair(4, 28), new Pair(3, 29), new Pair(2, 29), new Pair(1, 29), new Pair(0, 28), new Pair(0, 30), new Pair(0, 31), new Pair(1, 31), new Pair(2, 31), new Pair(3, 31), new Pair(4, 31), new Pair(4, 30)};
    private static final Pair[] numberNine = {new Pair(0, 27), new Pair(1, 27), new Pair(2, 27), new Pair(3, 27), new Pair(4, 27), new Pair(4, 28), new Pair(4, 29), new Pair(3, 29), new Pair(2, 29), new Pair(1, 29), new Pair(0, 29), new Pair(0, 28), new Pair(4, 30), new Pair(4, 31), new Pair(3, 31), new Pair(2, 31), new Pair(1, 31), new Pair(0, 31)};

    private static final int REFRESH_RATE = 500;


    public SnakeCharacter main = null;
    public AppleObject apple = null;
    private Handler mHandler = new Handler();

    public static final int PAINT = 0;
    public static final int ERASE = 1;
    public static final int LOAD = 2;

    private boolean gameStart;

    public int highScore;

    private Random r;
    private static final String TAG = "MainGameScreen";

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    private static final String ACTION_USB_PERMISSION = "com.google.android.DemoKit.action.USB_PERMISSION";

    private UsbManager mUsbManager;
    private PendingIntent mPermissionIntent;
    private boolean mPermissionRequestPending;

    UsbAccessory mAccessory;
    ParcelFileDescriptor mFileDescriptor;
    FileInputStream mInputStream;
    FileOutputStream mOutputStream;

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        openAccessory(accessory);
                    } else {
                        Log.d(TAG, "permission denied for accessory "
                                + accessory);
                    }
                    mPermissionRequestPending = false;
                }
            } else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
                UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                if (accessory != null && accessory.equals(mAccessory)) {
                    closeAccessory();
                }
            }
        }
    };


    private void openAccessory(UsbAccessory accessory) {
        mFileDescriptor = mUsbManager.openAccessory(accessory);
        if (mFileDescriptor != null) {
            mAccessory = accessory;
            FileDescriptor fd = mFileDescriptor.getFileDescriptor();
            mInputStream = new FileInputStream(fd);
            mOutputStream = new FileOutputStream(fd);
            Log.d(TAG, "accessory opened");
        } else {
            Log.d(TAG, "accessory open fail");
        }
    }


    private void closeAccessory() {
        try {
            if (mFileDescriptor != null) {
                mFileDescriptor.close();
            }
        } catch (IOException e) {
        } finally {
            mFileDescriptor = null;
            mAccessory = null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new SQLiteHelper(this);
        numbersMap.put(0, numberZero);
        numbersMap.put(1, numberOne);
        numbersMap.put(2, numberTwo);
        numbersMap.put(3, numberThree);
        numbersMap.put(4, numberFour);
        numbersMap.put(5, numberFive);
        numbersMap.put(6, numberSix);
        numbersMap.put(7, numberSeven);
        numbersMap.put(8, numberEight);
        numbersMap.put(9, numberNine);
        //Toast.makeText(this, numbersMap.toString(), Toast.LENGTH_LONG).show();
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        registerReceiver(mUsbReceiver, filter);
        setContentView(R.layout.activity_main);
        r = new Random();
        if(savedInstanceState != null && gameStart) {
            loadGame(savedInstanceState);
        } else {
            gameStart = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.run, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        database = dbHelper.getWritableDatabase();
        highScore = getHighScore().getHighScore();
        TextView highScoreText = (TextView)findViewById(R.id.highscoreText);
        highScoreText.setText("High score: " + highScore);

        if (mInputStream != null && mOutputStream != null) {
            return;
        }

        UsbAccessory[] accessories = mUsbManager.getAccessoryList();
        UsbAccessory accessory = (accessories == null ? null : accessories[0]);
        if (accessory != null) {
            if (mUsbManager.hasPermission(accessory)) {
                openAccessory(accessory);
            } else {
                synchronized (mUsbReceiver) {
                    if (!mPermissionRequestPending) {
                        mUsbManager.requestPermission(accessory,mPermissionIntent);
                        mPermissionRequestPending = true;
                    }
                }
            }
        } else {
            Log.d(TAG, "mAccessory is null");
        }

        if(gameStart) {
            drawToBoard(main);
            drawToBoard(apple);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        eraseBoard();
        dbHelper.close();
        closeAccessory();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        saveGame(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        loadGame(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    public void moveDown(View v) {
        if(gameStart) {
            mHandler.removeCallbacks(frameUpdate);
            mHandler.post(frameUpdate);
            main.setDirection(SnakeCharacter.Direction.DOWN);
        }
    }

    public void moveUp(View v) {
        if(gameStart) {
            mHandler.removeCallbacks(frameUpdate);
            mHandler.post(frameUpdate);
            main.setDirection(SnakeCharacter.Direction.UP);
        }
    }

    public void moveLeft(View v) {
        if(gameStart) {
            mHandler.removeCallbacks(frameUpdate);
            mHandler.post(frameUpdate);
            main.setDirection(SnakeCharacter.Direction.LEFT);
        }
    }

    public void moveRight(View v) {
        if(gameStart) {
            mHandler.removeCallbacks(frameUpdate);
            mHandler.post(frameUpdate);
            main.setDirection(SnakeCharacter.Direction.RIGHT);
        }
    }

    public void moveMain() {
        eraseBoard();
        if(main.move()) {
            Toast.makeText(this, "Game over", Toast.LENGTH_SHORT).show();
            gameOver();
        } else {
            if(main.checkApple(apple)) {
                TextView scoreText = (TextView) findViewById(R.id.scoreText);
                scoreText.setText("Score: " + main.getScore());
                if(main.getScore() > highScore) {
                    TextView highScoreText = (TextView)findViewById(R.id.highscoreText);
                    highScoreText.setText("High score: " + main.getScore());
                }
                apple = new AppleObject(r.nextInt(31), r.nextInt(31));
            }
            drawToBoard(main);
            drawToBoard(apple);
        }
    }

    /**
     * Create a Runnable startTimer that makes timer runnable.
     */
    private Runnable frameUpdate = new Runnable() {
        public void run() {

            mHandler.postDelayed(frameUpdate, REFRESH_RATE);
            moveMain();

        }
    };

    public void startGame(View v) {
        gameStart = true;
        Button newGameButton = (Button)findViewById(R.id.newGame);
        newGameButton.setVisibility(View.GONE);
        main = new SnakeCharacter();
        apple = new AppleObject(r.nextInt(31), r.nextInt(31));
        eraseBoard();
        drawToBoard(main);
        drawToBoard(apple);
    }

    public void drawToBoard(GameCharacter gc) {
        ArrayList<Pair> pixels = gc.drawToBoard();

        for(Pair pixel : pixels) {
            byte[] msg = new byte[6];
            msg[0] = PAINT;
            msg[1] = Byte.parseByte(((Integer) pixel.x).toString());
            msg[2] = Byte.parseByte(((Integer) pixel.y).toString());
            msg[3] = Byte.parseByte(((Integer) gc.r).toString());
            msg[4] = Byte.parseByte(((Integer) gc.g).toString());
            msg[5] = Byte.parseByte(((Integer) gc.b).toString());

            if (mOutputStream != null) {
                try {
                    mOutputStream.write(msg);
                } catch (IOException e) {
                    Log.e(TAG, "write failed", e);
                }
            }
        }
    }

    public void eraseBoard(){
        byte[] msg = new byte[6];
        msg[0] = ERASE;

        if (mOutputStream != null) {
            try {
                mOutputStream.write(msg);
            } catch (IOException e) {
                Log.e(TAG, "write failed", e);
            }
        }
    }

    public void gameOver() {
        mHandler.removeCallbacks(frameUpdate);
        gameStart = false;
        Button newGameButton = (Button)findViewById(R.id.newGame);
        newGameButton.setVisibility(View.VISIBLE);
        for(Pair pixel : bitmap) {
            byte[] msg = new byte[6];
            msg[0] = PAINT;
            msg[1] = Byte.parseByte(((Integer) pixel.x).toString());
            msg[2] = Byte.parseByte(((Integer) pixel.y).toString());
            msg[3] = Byte.parseByte(((Integer) 1).toString());
            msg[4] = Byte.parseByte(((Integer) 0).toString());
            msg[5] = Byte.parseByte(((Integer) 0).toString());

            if (mOutputStream != null) {
                try {
                    mOutputStream.write(msg);
                } catch (IOException e) {
                    Log.e(TAG, "write failed", e);
                }
            }
        }
        for(Pair pixel : scoreMap) {
            byte[] msg = new byte[6];
            msg[0] = PAINT;
            msg[1] = Byte.parseByte(((Integer) pixel.x).toString());
            msg[2] = Byte.parseByte(((Integer) pixel.y).toString());
            msg[3] = Byte.parseByte(((Integer) 1).toString());
            msg[4] = Byte.parseByte(((Integer) 0).toString());
            msg[5] = Byte.parseByte(((Integer) 0).toString());

            if (mOutputStream != null) {
                try {
                    mOutputStream.write(msg);
                } catch (IOException e) {
                    Log.e(TAG, "write failed", e);
                }
            }
        }

        ArrayList<Pair[]> scores = numberCombiner(main.getScore());
        try {
            for (Pair[] num : scores) {
                for (Pair pixel : num) {
                    byte[] msg = new byte[6];
                    msg[0] = PAINT;
                    msg[1] = Byte.parseByte(((Integer) pixel.x).toString());
                    msg[2] = Byte.parseByte(((Integer) pixel.y).toString());
                    msg[3] = Byte.parseByte(((Integer) 1).toString());
                    msg[4] = Byte.parseByte(((Integer) 0).toString());
                    msg[5] = Byte.parseByte(((Integer) 0).toString());

                    if (mOutputStream != null) {
                        try {
                            mOutputStream.write(msg);
                        } catch (IOException e) {
                            Log.e(TAG, "write failed", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            //Toast.makeText(this, scores.toString(), Toast.LENGTH_SHORT).show();
        }

        if(main.getScore() > highScore) {
            sendScore(main.getScore());
            highScore = main.getScore();
        }
    }

    public void saveGame(Bundle save) {
        if(gameStart) {
            save.putParcelable("Main", main);
            save.putParcelable("Apple", apple);
        }
    }

    public void loadGame(Bundle load) {
        if(gameStart) {
            mHandler.removeCallbacks(frameUpdate);
            main = load.getParcelable("Main");
            apple = load.getParcelable("Apple");
            mHandler.post(frameUpdate);
        }
    }
    public Event sendScore(int score) {

        // Put keys (row columns) and values (parameters) into ContentValues object

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_SCORE, score);

        // Insert ContentValues into row in events table and obtain row ID
        // HINT: database.insert(...) returns the id of the row you insert
        long id = database.insert(SQLiteHelper.TABLE_EVENTS, null, values);

        // Query database for event row just added using the getEvent(...) method
        // NOTE: You need to write a query to get an event by id at the to-do marker
        //		 in the getEvent(...) method
        Event newEvent = getEvent(id);

        return newEvent;
    }

    public void deleteEvent(Event event) {
        long id = event.getId();
        String id_string = "" + id;
        database.delete(SQLiteHelper.TABLE_EVENTS, "?=?", new String[]{SQLiteHelper.COLUMN_ID, id_string});
    }

    /**
     * Queries and returns event based on ID
     * @param id
     * ID of event to return
     * @return
     * Event with ID "id"
     */
    public Event getEvent(long id) {
        Cursor cursor = null;

        // TODO: Create query for single event here

        cursor = database.query(SQLiteHelper.TABLE_EVENTS, allColumns, SQLiteHelper.COLUMN_ID + "=" + id, null, null, null, null, null);

        cursor.moveToFirst();
        Event toReturn = cursorToEvent(cursor);
        cursor.close();
        return toReturn;
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();

        // TODO: Fill event object with data from Cursor
        event.setId(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_ID)));
        event.setHighScore(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_SCORE)));

        return event;
    }

    public Event getHighScore() {

        Cursor cursor = null;
        cursor = database.query(SQLiteHelper.TABLE_EVENTS, allColumns, null, null, null, null, null, null);

        Event high = new Event(-1, 0);
        cursor.moveToFirst();
        while(cursor != null && !cursor.isAfterLast()) {
            Event check = cursorToEvent(cursor);
            if(check.getHighScore() > high.getHighScore()) {
                high = check;
            }
            cursor.moveToNext();
        }

        return high;
    }

    private ArrayList<Pair[]> numberCombiner(int number)
    {
        ArrayList<Pair[]> finalScore = new ArrayList<Pair[]>();
        try {
            String digits = Integer.toString(number);

            for(int i = 0; i < digits.length(); i++)
            {
                Pair[] digit = numbersMap.get(Integer.parseInt(Character.toString(digits.charAt(i))));
                if(i >= 1)
                {
                    for(Pair p : digit)
                    {
                        p.x += i*6;
                    }
                }
                finalScore.add(digit);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        return finalScore;

    }
}
