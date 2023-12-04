package com.eacarey.badreads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.eacarey.badreads.Models.UserViewModel;
import com.eacarey.badreads.databinding.ActivityMainBinding;
import com.eacarey.badreads.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

  public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;

  ActivityMainBinding binding;

//  LoggedInUserView mUserViewModel;

  private UserViewModel mUserViewModel1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());


//    UserRepository loginRepository = UserRepository.getInstance(new LoginDataSource());
    mUserViewModel1 = new ViewModelProvider(this).get(UserViewModel.class);

    final Button logoutButton = this.binding.logOutButton;
    logoutButton.setOnClickListener(v -> {
      this.mUserViewModel1.logout();
      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (!mUserViewModel1.isLoggedIn()) {

//      startActivity(LoginActivity.getIntent(getApplicationContext()));

      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
    } else {

//  this.mUserViewModel = new ViewModelProvider(this).get(LoggedInUserView.class);
      User user = mUserViewModel1.getUser();

      binding.welcomeLabel.setText(
          String.format(getString(R.string.welcome_message), user.getUsername()));
    }
  }

//  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    // return from LoginActivity
//    if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
////      this.mUserViewModel = (LoggedInUserView) data.getParcelableExtra(LoginActivity.LOGIN_EXTRA_REPLY);
////      binding.welcomeLabel.setText(
////          String.format(getString(R.string.welcome_message), this.mUserViewModel.getDisplayName()));
//    }
//  }

  public static Intent getIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }
}
