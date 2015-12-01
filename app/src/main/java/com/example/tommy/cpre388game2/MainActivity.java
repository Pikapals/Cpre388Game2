package com.example.tommy.cpre388game2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends Activity {
    // TAG is used to debug in Android logcat console


    private static final Pair[] bitmap = {new Pair(0, 0), new Pair(1, 0), new Pair(2, 0), new Pair(3, 0), new Pair(4, 0), new Pair(0, 1), new Pair(0, 2), new Pair(0, 3), new Pair(0, 4), new Pair(1, 4), new Pair(2, 4), new Pair(3, 4), new Pair(4, 4), new Pair(4, 3), new Pair(4, 2), new Pair(3, 2), new Pair(2, 2),
        new Pair(6, 0), new Pair(6, 1), new Pair(6, 2), new Pair(6, 3), new Pair(6, 4), new Pair(7, 0), new Pair(8, 0), new Pair(9, 0), new Pair(10, 0), new Pair(10, 1), new Pair(10, 2), new Pair(10, 3), new Pair(10, 4), new Pair(7, 2), new Pair(8, 2), new Pair(9, 2),
        new Pair(12, 0), new Pair(12, 1), new Pair(12, 2), new Pair(12, 3), new Pair(12, 4), new Pair(13,1), new Pair(14,2), new Pair(14,1), new Pair(15, 0), new Pair(15, 1), new Pair(15, 2), new Pair(15, 3), new Pair(15, 4),
        new Pair(17, 0), new Pair(18, 0), new Pair(19, 0), new Pair(20, 0), new Pair(21, 0), new Pair(17, 1), new Pair(17, 2), new Pair(18, 2), new Pair(19, 2), new Pair(20, 2), new Pair(21, 2), new Pair(17, 3), new Pair(17, 4), new Pair(18, 4), new Pair(19, 4), new Pair(20, 4), new Pair(21, 4),
        new Pair(0, 6), new Pair(0, 7), new Pair(0, 8), new Pair(0, 9), new Pair(0, 10), new Pair(1, 6), new Pair(2, 6), new Pair(3, 6), new Pair(4, 6), new Pair(4, 7), new Pair(4, 8), new Pair(4, 9), new Pair(4, 10), new Pair(1, 10), new Pair(2, 10), new Pair(3, 10),
        new Pair(6, 6), new Pair(6, 7), new Pair(7, 8), new Pair(7, 9), new Pair(8, 10), new Pair(9, 9), new Pair(9, 8), new Pair(10, 7), new Pair(10, 6),
        new Pair(12, 6), new Pair(12, 7), new Pair(12, 8), new Pair(12, 9), new Pair(12, 10), new Pair(13, 6), new Pair(14, 6), new Pair(15, 6), new Pair(16, 6), new Pair(13, 8), new Pair(14, 8), new Pair(15, 8), new Pair(16, 8), new Pair(13, 10), new Pair(14, 10), new Pair(15, 10), new Pair(16, 10),
        new Pair(18, 6), new Pair(18, 7), new Pair(18, 8), new Pair(18, 9), new Pair(18, 10), new Pair(19, 6), new Pair(20, 6), new Pair(21, 6), new Pair(22, 6), new Pair(22, 7), new Pair(22, 8), new Pair(21, 8), new Pair(20, 8), new Pair(19, 8), new Pair(19, 9), new Pair(20, 10)};
    //new Pair(12, 0), new Pair(12, 1), new Pair(12, 2), new Pair(12, 3), new Pair(12, 4), new Pair(13, 0), new Pair(14, 0), new Pair(15, 0), new Pair(16, 0), new Pair(16, 1), new Pair(16, 2), new Pair(16, 3), new Pair(16, 4), new Pair(13,4), new Pair(14, 4), new Pair(15, 4)

    private static final int REFRESH_RATE = 500;


    public SnakeCharacter main = null;
    public AppleObject apple = null;
    private Handler mHandler = new Handler();

    public static final int PAINT = 0;
    public static final int ERASE = 1;
    public static final int LOAD = 2;

    private Random r;
    private static final String TAG = "MainGameScreen";

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
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        registerReceiver(mUsbReceiver, filter);
        setContentView(R.layout.activity_main);
        r = new Random();
        main = new SnakeCharacter();
        apple = new AppleObject(r.nextInt(31), r.nextInt(31));
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
        drawToBoard(main);
        drawToBoard(apple);
    }

    @Override
    public void onPause() {
        super.onPause();
        eraseBoard();
        closeAccessory();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    public void moveDown(View v) {
        mHandler.removeCallbacks(frameUpdate);
        mHandler.post(frameUpdate);
        main.setDirection(SnakeCharacter.Direction.DOWN);
    }

    public void moveUp(View v) {
        mHandler.removeCallbacks(frameUpdate);
        mHandler.post(frameUpdate);
        main.setDirection(SnakeCharacter.Direction.UP);
    }

    public void moveLeft(View v) {
        mHandler.removeCallbacks(frameUpdate);
        mHandler.post(frameUpdate);
        main.setDirection(SnakeCharacter.Direction.LEFT);
    }

    public void moveRight(View v) {
        mHandler.removeCallbacks(frameUpdate);
        mHandler.post(frameUpdate);
        main.setDirection(SnakeCharacter.Direction.RIGHT);
    }

    public void moveMain() {
        eraseBoard();
        if(main.move()) {
            Toast.makeText(this, "Game over", Toast.LENGTH_SHORT).show();
            gameOver();
        } else {
            if(main.checkApple(apple)) {
               /* TextView scoreText = (TextView) findViewById(R.id.scoreText);
                scoreText.setText("Score: " + main.getScore());*/
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
    }
}
