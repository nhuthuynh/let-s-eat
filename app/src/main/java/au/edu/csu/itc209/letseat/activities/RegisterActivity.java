package au.edu.csu.itc209.letseat.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.fragments.Status_Bar_Fragment;
import au.edu.csu.itc209.letseat.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements Status_Bar_Fragment.OnFragmentInteractionListener {

    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.edtConfirmPassword) EditText edtConfirmPassword;
    @BindView(R.id.edtEmailWrapper) TextInputLayout edtEmailWrapper;
    @BindView(R.id.edtNameWrapper) TextInputLayout edtNameWrapper;
    @BindView(R.id.edtPasswordWrapper) TextInputLayout edtPasswordWrapper;

    private FirebaseAuth mAuth;

    private final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        Status_Bar_Fragment statusBarFragment = new Status_Bar_Fragment().newInstance(getString(R.string.register_title_screen));
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_status_bar_container, statusBarFragment).commit();
    }



    @OnClick(R.id.btnRegister)
    public void registerAccount(){
        String email = edtEmail.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if(!isValidName(name) || !Util.isValidEmail(edtEmail, edtEmailWrapper) || !isValidPassword(password, confirmPassword)) return;
        Util.showLoading(this);
        mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                createFirebaseUserProfile(task.getResult().getUser());
                            } else {
                                Log.d(TAG, task.getException().getMessage());
                                Util.hideLoading(getBaseContext());
                                Toast.makeText(RegisterActivity.this, "Register failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    public boolean isValidName(String name) {
        boolean isNameValid = (name != null && !name.equals(""));

        if (!isNameValid) {
            edtNameWrapper.setError("Please enter your name!");
            edtNameWrapper.setErrorEnabled(true);
            return false;
        }

        return isNameValid;
    }

    public boolean isValidPassword(String password, String confirmPassword) {
        if(password.length() < 6) {
            edtPasswordWrapper.setError("Your password must contain at least 6 characters!");
            edtPasswordWrapper.setErrorEnabled(true);
            return false;
        } else if (!password.equals(confirmPassword)) {
            edtPasswordWrapper.setError("Your passwords do not match!");
            edtPasswordWrapper.setErrorEnabled(true);
            return false;
        }

        return true;
    }

    public void createFirebaseUserProfile(final FirebaseUser user) {
        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(edtName.getText().toString().trim()).build();

        user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "update user name successfully!");
                } else {
                    Log.d(TAG, "update user name failed! " + task.getException().getMessage());
                }
                Util.hideLoading(getBaseContext());
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
