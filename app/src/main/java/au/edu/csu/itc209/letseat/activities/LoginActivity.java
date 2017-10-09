package au.edu.csu.itc209.letseat.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.models.User;
import au.edu.csu.itc209.letseat.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtPassword) EditText edtPass;
    @BindView(R.id.btnForgotPassword) Button btnForgotPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnRegister) Button btnRegister;
    @BindView(R.id.btnLoginFacebook) Button btnLoginFacebook;
    @BindView(R.id.btnLoginGoogle) Button btnLoginGoogle;
    @BindView(R.id.login_button) LoginButton login_button;
    @BindView(R.id.btnLoginAnonymous) Button loginAnonymous;
    @BindView(R.id.edtEmailWrapper) TextInputLayout edtEmailWrapper;
    @BindView(R.id.edtPasswordWrapper) TextInputLayout edtPasswordWrapper;

    private static final String TAG = "LogIn Activity";


    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    private GoogleSignInOptions gso;
    private GoogleApiClient gac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();
        login_button.setReadPermissions("email", "public_profile");
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "loginByFacebook success:" + loginResult.getAccessToken().getUserId());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancel Login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gac = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick(R.id.btnForgotPassword)
    public void goToForgotPasswordScreen() {
        goToOtherScreen(ForgotPasswordActivity.class, false);
    }

    @OnClick(R.id.btnLoginAnonymous)
    public void loginAnonymous() {
        mAuth.signInAnonymously().addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "signInAnonymously:success");
                    goToOtherScreen(MainActivity.class, true);
                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        final String email = edtEmail.getText().toString().trim();
        final String password = edtPass.getText().toString().trim();

        if(!Util.isValidEmail(edtEmail, edtEmailWrapper) || !isValidPassword(password)) return;
        Util.showLoading(LoginActivity.this);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Util.hideLoading(LoginActivity.this);
                            Toast.makeText(LoginActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                            goToOtherScreen(MainActivity.class, true);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Util.hideLoading(LoginActivity.this);
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean isValidPassword(final String password) {
        boolean isValidPass = (password != null && !password.equals(""));

        if (!isValidPass) {
            edtPasswordWrapper.setError("Please enter your password!");
            edtPasswordWrapper.setErrorEnabled(true);
            return false;
        }
        edtPasswordWrapper.setErrorEnabled(false);
        return isValidPass;
    }

    @OnClick(R.id.btnRegister)
    public void goToRegisterScreen() {
        goToOtherScreen(RegisterActivity.class, false);
    }

    @OnClick(R.id.btnLoginGoogle)
    public void loginByGoogle() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(gac), Constants.RC_SIGN_IN);
    }

    @OnClick(R.id.btnLoginFacebook)
    public void loginByFacebook() {
        Log.d(TAG, "login by facebook");
        login_button.performClick();
    }

    public void goToOtherScreen(Class activityClass, boolean isFinishCurrentActivity) {
        startActivity(new Intent(LoginActivity.this, activityClass));
        if(isFinishCurrentActivity) finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                firebaseAuthWithGoogle(result.getSignInAccount());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acc) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acc.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Log In With Google", Toast.LENGTH_SHORT).show();
                    goToOtherScreen(MainActivity.class, true);
                } else {
                    Log.d(TAG, "firebaseAuthWithGoogle:" + task.getException());
                }
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Log In With Facebook", Toast.LENGTH_SHORT).show();
                            goToOtherScreen(MainActivity.class, true);
                        } else {
                            Log.d(TAG, "firebaseAuthWithGoogle:" + task.getException());
                        }
                    }
                });
    }

}
