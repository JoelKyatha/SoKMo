package com.sokmo.sokmoapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sokmo.sokmoapp.connect.ConnectFragment;
import com.sokmo.sokmoapp.help.HelpFragment;
import com.sokmo.sokmoapp.keyboard.KeyboardFragment;
import com.sokmo.sokmoapp.poweroff.PowerOffFragment;
import com.sokmo.sokmoapp.presentation.PresentationFragment;
import com.sokmo.sokmoapp.server.Server;
import com.sokmo.sokmoapp.touchpad.TouchpadFragment;

public class MainActivity extends AppCompatActivity implements FragmentHome.FragmentHomeListener{

    public static Socket clientSocket = null;
    public static ObjectInputStream objectInputStream = null;
    public static ObjectOutputStream objectOutputStream = null;
    private static AppCompatActivity thisActivity;
    private boolean doubleBackToExitPressedOnce = false;
    private Fragment current_fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

        // selecting connect fragment by default
        //replaceFragment(R.id.nav_connect);
        Fragment fragment = new ConnectFragment();
        if (fragment != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment).commit();
        }
        thisActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkForPermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkForPermission() {
        if (thisActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (thisActivity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(thisActivity, "Read Permission is necessary to transfer", Toast.LENGTH_LONG).show();
            } else {
                thisActivity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                //2 is integer constant for WRITE_EXTERNAL_STORAGE permission, uses in onRequestPermissionResult
            }
        }
    }


    @Override
    public void onBackPressed() {
        if(this.current_fragment!=null){
            if(current_fragment instanceof TouchpadFragment || current_fragment instanceof KeyboardFragment || current_fragment instanceof PresentationFragment || current_fragment instanceof PowerOffFragment ){
                current_fragment=null;
                super.onBackPressed();
                Fragment fragment = new FragmentHome();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack("tag").commit();
                current_fragment=fragment;
            }else if(current_fragment instanceof FragmentHome){
                this.finish();
                System.exit(0);
            }
        }else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void fragmentClicked(Fragment fragment) {

        this.current_fragment=fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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
            return true;
        }
        if (id == R.id.about)
        {

            final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("This is SoKMo Application which allows you to\nUse your Smartphone as a Remote Presentation App via wifi or mobile hotspot.\nAuthor:\nJoel Kyatha");
            dlgAlert.setTitle("About");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dlgAlert.c
                        }
                    });
        }
        else  if(id==R.id.action_help){

//            Fragment fragment = new HelpFragment();
//            if (fragment != null) {
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_frame, fragment).commit();
//            }
          //Intent  intent= new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/JoelKyatha/SoKMo"));

            Intent intent =new Intent(this,HelpActivity.class);


            startActivity(intent);



        }
        
        return super.onOptionsItemSelected(item);
    }



//    private void  replaceFragment(int id) {
//        Fragment fragment = null;
//
//        if (id == R.id.nav_connect) {
//            fragment = new ConnectFragment();
//        } else if (id == R.id.nav_touchpad) {
//            fragment = new TouchpadFragment();
//        } else if (id == R.id.nav_keyboard) {
//            fragment = new KeyboardFragment();
//        } /*else if (id == R.id.nav_file_transfer) {
//            fragment = new FileTransferFragment();
//        } else if (id == R.id.nav_file_download) {
//            fragment = new FileDownloadFragment();
//        } else if (id == R.id.nav_image_viewer) {
//            fragment = new ImageViewerFragment();
//        } else if (id == R.id.nav_media_player) {
//            fragment = new MediaPlayerFragment();
//        } */else if (id == R.id.nav_presentation) {
//            fragment = new PresentationFragment();
//        } else if (id == R.id.nav_power_off) {
//            fragment = new PowerOffFragment();
//        } else if (id == R.id.action_help) {
//            fragment = new HelpFragment();
//        } else if (id == R.id.action_live_screen) {
//            //fragment = new LiveScreenFragment();
//        }
//        if (fragment != null) {
//            FragmentManager manager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = manager.beginTransaction();
//            fragmentTransaction.replace(R.id.content_frame, fragment).commit();
//        }
//    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            if (MainActivity.clientSocket != null) {
                MainActivity.clientSocket.close();
            }
            if (MainActivity.objectOutputStream != null) {
                MainActivity.objectOutputStream.close();
            }
            if (MainActivity.objectInputStream != null) {
                MainActivity.objectInputStream.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        Server.closeServer();
    }

    //this method is called from fragments to send message to server (Desktop)
    public static void sendMessageToServer(String message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "STRING");

        }
    }

    public static void sendMessageToServer(int message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "INT");

        }
    }

    public static void socketException() {
        //Toast.makeText(thisActivity, "Connection Closed", Toast.LENGTH_LONG).show();
        if (MainActivity.clientSocket != null) {
            try {
                MainActivity.clientSocket.close();
                MainActivity.objectOutputStream.close();
                MainActivity.clientSocket = null;
            } catch(Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void sendMessageToServer(float message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "FLOAT");

        }
    }

    public static void sendMessageToServer(long message) {
        if (MainActivity.clientSocket != null) {
            new SendMessageToServer().execute(String.valueOf(message), "LONG");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Click again to download", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Failed to download", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Click again to download", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "File Transfer will not work", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String action = new String();
        if ( keyCode == KeyEvent.KEYCODE_VOLUME_UP ) {
                action = "UP_ARROW_KEY";
        } else if ( keyCode == KeyEvent.KEYCODE_VOLUME_DOWN ) {
            action = "DOWN_ARROW_KEY";
        }else  if(keyCode==KeyEvent.KEYCODE_BACK){
            onBackPressed();
        }
        MainActivity.sendMessageToServer(action);
        return true;
    }

}
