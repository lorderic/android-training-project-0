package com.codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;


public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        //items = new ArrayList<String>();
        readItems();
        
        itemsAdapter = new ArrayAdapter<String>(getBaseContext(), 
        		android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();
    }
    
    public void addTodoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
    
    private void setupListViewListener(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
    		@Override
    		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId){
    			items.remove(position);
    			itemsAdapter.notifyDataSetChanged();
    			saveItems();
    			return true;
    		}
    	});
    }
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e){
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }   
    
    private void saveItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }
 
}
