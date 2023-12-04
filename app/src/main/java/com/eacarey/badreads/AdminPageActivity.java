package com.eacarey.badreads;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AdminPageActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_page);
  }

  public static Intent getIntent(Context context) {
    return new Intent(context, AdminPageActivity.class);
  }
}