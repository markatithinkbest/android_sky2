package com.giant.MrAction;

import net.micode.fileexplorer.FileExplorerTabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProfileFragment extends Fragment {
View parentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	parentView=inflater.inflate(R.layout.profile, container, false);
    	parentView.findViewById(R.id.ppb01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                
                Intent intent = new Intent(getActivity(),Profile01activity.class);
                startActivity(intent);
            }
        });	
    	parentView.findViewById(R.id.ppb02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                
                Intent intent = new Intent(getActivity(),Profile02activity.class);
                startActivity(intent);
            }
        });	
    	parentView.findViewById(R.id.ppb03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                
                Intent intent = new Intent(getActivity(),Profile03activity.class);
                startActivity(intent);
            }
        });	
    	
    	return parentView;
    }

}
