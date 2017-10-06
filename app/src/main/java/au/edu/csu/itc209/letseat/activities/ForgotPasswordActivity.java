package au.edu.csu.itc209.letseat.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.fragments.Status_Bar_Fragment;
import au.edu.csu.itc209.letseat.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity implements Status_Bar_Fragment.OnFragmentInteractionListener {

    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtEmailWrapper) TextInputLayout edtEmailWrapper;

    final String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);

        Bundle extras = new Bundle();
        extras.putString(Constants.TITLE_KEY, "Forgot password");

        Status_Bar_Fragment statusBarFragment = new Status_Bar_Fragment();
        statusBarFragment.setArguments(extras);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_status_bar_container, statusBarFragment).commit();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnResetPassword)
    public void resetPassword() {
        final String email = edtEmail.getText().toString().trim();

        if(!Util.isValidEmail(edtEmail, edtEmailWrapper)) return;

        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please check your email for further instruction!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, "onFragmentInteraction:Uri" + uri.toString());
    }
}
