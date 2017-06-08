package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.LoginAPI;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.LoginControllerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;

import static client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode.*;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements CustomObserver {


    // UI references.
    private EditText mNameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Spinner spinnerGui;
    private Button mSignInButton;
    private LoginControllerImpl loginImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginImpl = new LoginControllerImpl();
        loginImpl.setCObserver(this);

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mNameView = (EditText) findViewById(R.id.name);
        mPasswordView = (EditText) findViewById(R.id.password);
        spinnerGui = (Spinner) findViewById(R.id.spinnerGui);

        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);

        addListenerToButton();
    }

    private void addListenerToButton() {
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginImpl.requestLogin(mNameView.getText().toString(), mPasswordView.getText().toString());
            }
        });
    }

    private void startSelectedActivity() {
        User user = new User( mNameView.getText().toString(), mPasswordView.getText().toString());


        switch (spinnerGui.getSelectedItemPosition()) {
            case 0:
                Intent intentLabor = new Intent(LoginActivity.this, LaborGui.class);
                intentLabor.putExtra("USER",user);
                startActivity(intentLabor);
                break;
            case 1:
                Intent intentEntnahme = new Intent(LoginActivity.this, PrimerList.class);
                intentEntnahme.putExtra("USER",user);
                startActivity(intentEntnahme);
                break;
            case 2:
                Intent intentRueck = new Intent(LoginActivity.this, LagerRueckgabeGUI.class);
                intentRueck.putExtra("USER",user);
                startActivity(intentRueck);
                break;
        }
    }

    private void wrongAuthentification() {
        Toast toast = Toast.makeText(getApplicationContext(), "Falscher Benutzername oder falsches Passwort", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LOGIN:
                if ((Boolean) o == true) {
                    startSelectedActivity();
                } else {
                    wrongAuthentification();
                }
                break;
        }
    }


    @Override
    public void onResponseError() {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}

