/*
 * Copyright (c) 2015 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.alltherages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends AppCompatActivity implements RageComicListFragment.OnRageComicSelected{
  private Toolbar toolbar;
  public static final String PREFS_NAME = "AOP_PREFS";
  public static final String PREFS_KEY = "name";
  SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if(id==R.id.action_settings){
      return true;
          }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main,menu);
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = (Toolbar)findViewById(R.id.tool_bar);
    setSupportActionBar(toolbar);
    if(savedInstanceState == null ){
      getSupportFragmentManager().beginTransaction().add(R.id.root_layout,RageComicListFragment.newInstance(),"rageComicList").commit();
    }
  }

  @Override
  public void onRageComicSelected(int ImageResid, String name, String description, String url) {
    Toast.makeText(this,"You Selected "+name,Toast.LENGTH_SHORT).show();
  }
  public void save(Context context, String text) {
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
    editor = settings.edit(); //2

    editor.putString(PREFS_KEY, text); //3
    editor.commit(); //4
  }
}
