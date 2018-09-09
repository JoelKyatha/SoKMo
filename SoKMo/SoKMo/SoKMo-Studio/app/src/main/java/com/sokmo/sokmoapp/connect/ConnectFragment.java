package com.sokmo.sokmoapp.connect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Socket;

import com.sokmo.sokmoapp.MainActivity;
import com.sokmo.sokmoapp.R;
import com.sokmo.sokmoapp.server.Server;


public class ConnectFragment extends Fragment {

    public static String user;

    private Button connectButton, login;
    private EditText ipAddressEditText, portNumberEditText, username, password;
    private SharedPreferences sharedPreferences;

    public ConnectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_login, container, false);
        ipAddressEditText = (EditText) rootView.findViewById(R.id.ipAddress);
        portNumberEditText = (EditText) rootView.findViewById(R.id.portNumber);
        //username = (EditText) rootView.findViewById(R.id.username);
        //password = (EditText) rootView.findViewById(R.id.password);
        //login= (Button) rootView.findViewById(R.id.sign_in_button);

        connectButton = (Button) rootView.findViewById(R.id.connectButton);
        sharedPreferences = getActivity().getSharedPreferences("lastConnectionDetails", Context.MODE_PRIVATE);
        String lastConnectionDetails[] = getLastConnectionDetails();
        ipAddressEditText.setText(lastConnectionDetails[0]);
        portNumberEditText.setText(lastConnectionDetails[1]);
        if (MainActivity.clientSocket != null) {
            connectButton.setText("connected");
            connectButton.setEnabled(false);
        }
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeConnection();
            }
        });

//        login.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String loginCredentials = "login#" + username.getText().toString() + "#" + password.getText();
//                MainActivity.sendMessageToServer(loginCredentials);
//
//                   //Toast.makeText(getActivity(), "" + MainActivity.objectInputStream.readObject(), Toast.LENGTH_LONG).show();
//                    //String serverResponse = String.valueOf(MainActivity.objectInputStream.readObject());
//                    /*if ( serverResponse.equals("#userExist#")) {
//                        Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_LONG).show();
//                    } else if ( serverResponse.equals("#userNotExist#") ) {
//                        Toast.makeText(getActivity(), "User not Registered", Toast.LENGTH_LONG).show();
//                    }*/
//
//            }
//        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.connect));
    }

    public void makeConnection() {
        String ipAddress = ipAddressEditText.getText().toString();
        String port = portNumberEditText.getText().toString();
        if (ValidateIP.validateIP(ipAddress) && ValidateIP.validatePort(port)) {
            setLastConnectionDetails(new String[] {ipAddress, port});
            connectButton.setText("Connecting...");
            connectButton.setEnabled(false);
            new MakeConnection(ipAddress, port, getActivity()) {
                @Override
                public void receiveData(Object result) {
                    MainActivity.clientSocket = (Socket) result;
                    if (MainActivity.clientSocket == null) {
                        Toast.makeText(getActivity(), "Server is not listening", Toast.LENGTH_SHORT).show();
                        connectButton.setText("connect");
                        connectButton.setEnabled(true);
                    } else {
                        connectButton.setText("connected");
                        new Thread(new Runnable() {
                            public void run() {
                                new Server(getActivity()).startServer(Integer.parseInt(port));
                            }
                        }).start();
                    }
                }
            }.execute();
        } else {
            Toast.makeText(getActivity(), "Invalid IP Address or port", Toast.LENGTH_SHORT).show();
        }
    }
    private String[] getLastConnectionDetails() {
        String arr[] = new String[2];
        arr[0] = sharedPreferences.getString("lastConnectedIP", "");
        arr[1] = sharedPreferences.getString("lastConnectedPort", "3000");
        return arr;
    }
    private void setLastConnectionDetails(String arr[]) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastConnectedIP", arr[0]);
        editor.putString("lastConnectedPort", arr[1]);
        editor.apply();
    }

}
