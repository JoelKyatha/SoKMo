package com.sokmo.sokmoapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sokmo.sokmoapp.connect.ConnectFragment;
import com.sokmo.sokmoapp.help.HelpFragment;
import com.sokmo.sokmoapp.keyboard.KeyboardFragment;
import com.sokmo.sokmoapp.poweroff.PowerOffFragment;
import com.sokmo.sokmoapp.presentation.PresentationFragment;
import com.sokmo.sokmoapp.touchpad.TouchpadFragment;

import java.util.ArrayList;
public class FragmentHome  extends Fragment implements HomeAdapter.ItemListener{

    private RecyclerView recyclerView;
    private ArrayList<Item> arrayList;
    FragmentHomeListener fragmentHomeListener;
    public FragmentHome() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        arrayList = new ArrayList<Item>();

        Context context = inflater.getContext();
        arrayList.add(new Item("Controls a Mouse Cursor", R.drawable.nav_touchpad, "Mouse"));
        arrayList.add(new Item("Types or Uses Keystroke Shortcuts", R.drawable.ic_keyboard, "Keyboard"));
        arrayList.add(new Item("Assists with Slide Show", R.drawable.presentation, "Presentation"));
        arrayList.add(new Item("Power Off/Restart your Device", R.drawable.ic_power_off, "Power"));
        //arrayList.add(new Item("Item 5", R.drawable.three_d, "#F94336"));
        //arrayList.add(new Item("Item 6", R.drawable.terraria, "#0A9B88"));

        HomeAdapter adapter = new HomeAdapter(context, arrayList, this);
        recyclerView.setAdapter(adapter);



        GridLayoutManager manager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.help));
        fragmentHomeListener=(FragmentHomeListener)getActivity();
        fragmentHomeListener.fragmentClicked(new FragmentHome());
    }

    @Override
    public void onItemClick(Item item) {
        Fragment fragment = null;
        switch (item.text){
            case "Controls a Mouse Cursor":
                fragment = new TouchpadFragment();
                break;

            case "Types or Uses Keystroke Shortcuts":
                fragment = new KeyboardFragment();
                break;

            case "Assists with Slide Show":
                fragment = new PresentationFragment();
                break;

            case "Power Off/Restart your Device":
                fragment = new PowerOffFragment();
                break;
        }


        if (fragment != null) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            fragmentHomeListener=(FragmentHomeListener)getActivity();
            fragmentHomeListener.fragmentClicked(fragment);
        }


    }
    public interface FragmentHomeListener{
        void fragmentClicked(Fragment fragment);
    }

}
