package com.hackathon.pemilu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {
	
	ListView mainmenu;
	MainMenuAdapter menuAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainmenu = (ListView) findViewById(R.id.mainmenu);
        
        String[] menu	= { "Cari caleg mu !" , "Mata Pemilu" };
        int[] images	= { R.drawable.menu_1, R.drawable.menu_2 };
        
        menuAdapter = new MainMenuAdapter(this, R.layout.main_menu_row, menu, images );
        mainmenu.setAdapter(menuAdapter);
        mainmenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				switch(position)
				{
					case 0:
						Intent nik = new Intent( MainActivity.this, SearchNIKActivity_.class );
						startActivity(nik);
						break;
					case 1:
						Intent report = new Intent( MainActivity.this, ReportImages_.class );
						startActivity(report);
						break;
				}
				
			}
		});
        
    }

}
