package client_aib_labswp_2017_ss_entnahmeapp.View.view.guis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.LoginControllerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;

/**
 * {@link LoginActivity} displays the login screen for the application.
 */
public class LoginActivity extends AppCompatActivity implements CustomObserver {


    private EditText mNameView;
    private EditText mPasswordView;
    private TextView textWarnung;
    private Spinner spinnerGui;
    private Button mSignInButton;
    private LoginControllerImpl loginImpl;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
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
        View mLoginFormView = findViewById(R.id.login_form);

        textWarnung = (TextView) findViewById(R.id.auswahlWarnung);

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginImpl.requestLogin(mNameView.getText().toString(), mPasswordView.getText().toString());
                mSignInButton.setEnabled(false);
            }
        });
    }

    /**
     * Starts an activity depending on the spinner item that the {@link User} has chosen.
     */
    private void startSelectedActivity() {
        User user = new User(mNameView.getText().toString(), mPasswordView.getText().toString());


        switch (spinnerGui.getSelectedItemPosition()) {
            case 0:
                textWarnung.setVisibility(View.VISIBLE);
                break;
            case 1:
                Intent intentLaboratory = new Intent(LoginActivity.this, LaboratoryGui.class);
                intentLaboratory.putExtra("USER", user);
                Toast.makeText(this, R.string.sucesslogin, Toast.LENGTH_SHORT).show();
                startActivity(intentLaboratory);
                break;
            case 2:
                Intent intentLastSanger = new Intent(LoginActivity.this, LastSangerListActivity.class);
                intentLastSanger.putExtra("USER", user);
                Toast.makeText(this, R.string.sucesslogin, Toast.LENGTH_SHORT).show();
                startActivity(intentLastSanger);
                break;
            case 3:
                Intent intentPickList = new Intent(LoginActivity.this, PickListActivity.class);
                intentPickList.putExtra("USER", user);
                Toast.makeText(this, R.string.sucesslogin, Toast.LENGTH_SHORT).show();
                startActivity(intentPickList);
                break;
            case 4:
                Intent intentReturn = new Intent(LoginActivity.this, ReturnGUI.class);
                intentReturn.putExtra("USER", user);
                Toast.makeText(this, R.string.sucesslogin, Toast.LENGTH_SHORT).show();
                startActivity(intentReturn);
                break;

        }
    }

    /**
     * Notifies the {@link User} if a wrong username or password has been entered.
     */
    private void wrongAuthentification() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.failurelogin, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
        mSignInButton.setEnabled(true);
    }

    /**
     * Calls one of two methods that either authorizes the {@link User} to utilize the app or prohibits the access.
     *
     * @param o    the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LOGIN:
                if ((Boolean) o) {
                    startSelectedActivity();
                } else {
                    wrongAuthentification();
                }
                break;
        }
    }

    /**
     * Notifies the {@link User} when something went wrong with the request.
     */
    @Override
    public void onResponseError(Object o, ResponseCode code) {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
        mSignInButton.setEnabled(true);
    }

    /**
     * Notifies the {@link User} when something went wrong with the request.
     */
    @Override
    public void onResponseFailure(ResponseCode code) {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        mSignInButton.setEnabled(true);
    }

    /**
     * Resumes the activity if the {@link User} returns from the paused state.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSignInButton.setEnabled(true);
    }
}

