package com.app.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.aggro.R;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchBox.MenuListener;
import com.quinny898.library.persistentsearch.SearchBox.SearchListener;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;

public class RevealActivity extends ActionBarActivity {

	private SearchBox search;
	private Toolbar toolbar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reveal);
		getString(R.string.app_name);
		search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		RevealActivity.this.setSupportActionBar(toolbar);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				openSearch();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	public void openSearch() {
		toolbar.setTitle("");
		search.setMenuVisibility(View.VISIBLE);
		search.revealFromMenuItem(R.id.action_search, this);
		for (int x = 0; x < 10; x++) {
			SearchResult option = new SearchResult("Result "
					+ Integer.toString(x), getResources().getDrawable(
					R.mipmap.ic_history));
			search.addSearchable(option);
		}
		search.setMenuListener(new MenuListener() {

			@Override
			public void onMenuClick() {
				// Hamburger has been clicked
				Toast.makeText(RevealActivity.this, "Menu click",
						Toast.LENGTH_LONG).show();
			}

		});
		search.setSearchListener(new SearchListener() {
			@Override
			public void onSearchOpened() {

			}

			@Override
			public void onSearchCleared() {

			}

			@Override
			public void onSearchClosed() {

			}

			@Override
			public void onSearchTermChanged() {

			}

			@Override
			public void onSearch(String result) {
				Toast.makeText(RevealActivity.this, " Searched",
						Toast.LENGTH_LONG).show();
				toolbar.setTitle(result);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1234 && resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			search.populateEditText(matches);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void closeSearch() {
		search.hideCircularly(this);
		if(search.getSearchText().isEmpty())toolbar.setTitle("");
	}

}
