package bilalmajeed.com.bluetoothcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.UUID;
import java.io.IOException;
import java.util.Set;



public class MainActivity extends Activity {

    Button upBtn, downBtn, leftBtn, rightBtn;
    boolean error = false;
    Menu optionsMenu;

    //declare a few constant error messages
    private final String deviceName = "HC-06";
    private final String CONNECTION_ERROR = "Not connected to device";

    //declare the objects needed for the bluetooth
    private BluetoothAdapter btAdapter;
    private BluetoothSocket btSocket;
    private BluetoothDevice btRemoteDevice;
    private OutputStream output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upBtn = (Button) findViewById(R.id.upBtn);
        downBtn = (Button) findViewById(R.id.downBtn);
        leftBtn = (Button) findViewById(R.id.leftBtn);
        rightBtn = (Button) findViewById(R.id.rightBtn);

        //initialize the btAdapter
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter btDisconnected = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        IntentFilter btConnected = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        this.registerReceiver(bt_broadcastReceiver, btDisconnected);
        this.registerReceiver(bt_broadcastReceiver, btConnected);

        //initialize the btAdapter
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        //if there not btAdapter, meaning no Bluetooth support on the phone. Then notify user
        if(btAdapter == null){
            messageBox("DEVICE NOT SUPPORTED", "Your device does not have bluetooth capabilities");
        }

        //if the bluetooth is not enabled then ask for the user to enable it
        if(!btAdapter.isEnabled()){
            Intent turnBTOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTOn, 1);
        }

        buttonTouchListeners();
    }

    final private BroadcastReceiver bt_broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            MenuItem connectMenuItem = optionsMenu.findItem(R.id.action_connect);

            //if bluetooth is not connected then enable connect button
            if(BluetoothDevice.ACTION_ACL_DISCONNECTED == action){
                connectMenuItem.setTitle(getResources().getString(R.string.action_connect));
            }else if(BluetoothDevice.ACTION_ACL_CONNECTED == action){
                connectMenuItem.setTitle(getResources().getString(R.string.action_disconnect));
            }
        }
    };

    private void buttonTouchListeners() {
        upBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    output.write("1\n".getBytes());
                }catch(Exception e){
                    return false;
                }
                return true;
            }
        });

        downBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    output.write("2\n".getBytes());
                }catch(Exception e){
                    return false;
                }
                return true;
            }
        });

        rightBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    output.write("3\n".getBytes());
                }catch(Exception e){
                    return false;
                }
                return true;
            }
        });

        leftBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try{
                    output.write("4\n".getBytes());
                }catch(Exception e){
                    return false;
                }
                return true;
            }
        });
    }

    public void connect(){

        findRemoteDevice();

        try {
            connectRemoteDevice();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void disconnect(){
        try{
            btSocket.close();
            output.flush();
            output.close();
        }catch(IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void findRemoteDevice(){
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        //if there any paired devices
        if(pairedDevices.size() > 0) {
            //loop through paired devices
            for (BluetoothDevice deviceFound : pairedDevices) {
                if (deviceFound.getName().equals(deviceName))
                    btRemoteDevice = deviceFound;
            }
        }
    }

    public void connectRemoteDevice() throws IOException {
        UUID btUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        btSocket = btRemoteDevice.createRfcommSocketToServiceRecord(btUUID);
        btSocket.connect();
        output = btSocket.getOutputStream();
        Toast.makeText(this, "CONNECTED TO CAR", Toast.LENGTH_LONG).show();
    }

    //displays a message box to the user with the inputed message with one button
    private void messageBox(String method, String message) {
        Log.d("EXCEPTION: " + method, message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "This action is not supported at this time", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_connect) {
            if(item.getTitle() == getResources().getString(R.string.action_connect))
                connect();
            else if(item.getTitle() == getResources().getString(R.string.action_disconnect))
                disconnect();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            btSocket.close();
        }catch(IOException e){
            Toast.makeText(this, "ERROR - Could not close socket", Toast.LENGTH_LONG).show();
        }
    }
}
