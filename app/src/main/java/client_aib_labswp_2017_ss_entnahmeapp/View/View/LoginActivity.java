package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.LoginController;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.LoginModel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText mNameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Spinner spinnerGui;
    private Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mNameView = (EditText) findViewById(R.id.name);
        mPasswordView = (EditText) findViewById(R.id.password);
        spinnerGui = (Spinner) findViewById(R.id.spinnerGui);

        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);

        addListenerOnSpinner();

    }

    private void addListenerOnSpinner() {

        spinnerGui.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intentLabor = new Intent(LoginActivity.this, LaborGui.class);
                        addListenerToButton(intentLabor);
                        break;
                    case 1:
                        Intent intentEntnahme = new Intent(LoginActivity.this, PrimerList.class);
                        addListenerToButton(intentEntnahme);
                        break;
                    case 2:
                        Intent intentRueck = new Intent(LoginActivity.this, LagerRueckgabeGUI.class);
                        addListenerToButton(intentRueck);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void addListenerToButton(final Intent intent) {
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginController loginC = new LoginModel();
                if (loginC.login(mNameView.getText().toString(), mPasswordView.getText().toString()) == true) {
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Falscher Benutzername oder falsches Passwort", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,0);
                    toast.show();
                }
            }
        });
    }
}

