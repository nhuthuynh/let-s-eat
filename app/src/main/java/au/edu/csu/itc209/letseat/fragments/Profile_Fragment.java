package au.edu.csu.itc209.letseat.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ByteArrayLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.csu.itc209.letseat.R;
import au.edu.csu.itc209.letseat.asynctasks.UploadFileToStorage;
import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Profile_Fragment extends Fragment {

    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtEmailWrapper) TextInputLayout edtEmailWrapper;
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtNameWrapper) TextInputLayout edtNameWrapper;
    @BindView(R.id.btnChangePassword) Button btnChangePassword;
    @BindView(R.id.btnUpdate) Button btnUpdate;
    @BindView(R.id.btnProfileImage) ImageButton btnProfileImage;
    @BindView(R.id.btnClear) Button Clear;

    final String TAG = "ProfileFragment";
    private BottomSheetDialog bottomSheetDialog;
    private View bottomSheetView;
    private Bitmap bitmap;

    MaterialDialog.Builder builder;
    MaterialDialog dialog;

    private OnFragmentInteractionListener mListener;

    public Profile_Fragment() {
        // Required empty public constructor
    }


    public static Profile_Fragment newInstance() {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void loadUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            edtEmail.setText(user.getEmail());
            edtName.setText(user.getDisplayName());
            loadUserProfileImage(user.getPhotoUrl());
        }
    }

    public void loadUserProfileImage(final Uri url) {
        if(url != null) {
            Glide.with(getContext()).load(url).into(btnProfileImage);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @OnClick(R.id.btnChangePassword)
    public void onOpenPopupChangePassword() {
        new MaterialDialog.Builder(getContext())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        EditText currentPassword = (EditText) dialog.getCustomView().findViewById(R.id.currentPassword);
                        TextInputLayout currentPasswordWrapper = (TextInputLayout) dialog.getCustomView().findViewById(R.id.currentPasswordWrapper);
                        EditText newPassword = (EditText) dialog.getCustomView().findViewById(R.id.newPassword);
                        TextInputLayout newPasswordWrapper = (TextInputLayout) dialog.getCustomView().findViewById(R.id.newPasswordWrapper);
                        EditText confirmNewPassword = (EditText) dialog.getCustomView().findViewById(R.id.confirmNewPassword);
                        TextInputLayout confirmNewPasswordWrapper = (TextInputLayout) dialog.getCustomView().findViewById(R.id.confirmNewPasswordWrapper);

                        if (Util.isEmptyOrNull(currentPassword, currentPasswordWrapper)
                                || Util.isEmptyOrNull(newPassword, newPasswordWrapper)
                                || Util.isEmptyOrNull(confirmNewPassword, confirmNewPasswordWrapper)) {
                            return;
                        }


                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                    }
                })
                .title(R.string.change_password)
                .customView(R.layout.dialog_change_password, false)
                .positiveText(R.string.change_password)
                .negativeText(R.string.clear)
                .show();

    }

    @OnClick(R.id.btnClear)
    public void onClear() {
        loadUserInfo();
    }

    @OnClick(R.id.btnUpdate)
    public void updateProfile() {
        String name = edtName.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(!Util.isEmptyOrNull(edtName, edtNameWrapper)) return;

        final UserProfileChangeRequest.Builder profileBuilder = new UserProfileChangeRequest.Builder();
        Util.showLoading(getContext());
        if (!user.getDisplayName().equals(name)) {
            profileBuilder.setDisplayName(name);
        }

        if (bitmap != null) {
            new UploadFileToStorage(new UploadFileToStorage.AsyncResponse() {
                @Override
                public void onPostExecuteCallback(Uri uri) {
                    if(uri != null) {
                        profileBuilder.setPhotoUri(uri);
                        updateProfile(profileBuilder);
                    } else {
                        Log.d(TAG, "uploadfileAsyncTask failed:" + uri);
                    }
                }

                @Override
                public void onPostExecuteCallbackFailed(Exception ex) {
                    Log.d(TAG, "uploadfileAsyncTask failed:" +ex.getMessage());
                }
            }).execute(bitmap);
        } else {
            updateProfile(profileBuilder);
        }

    }

    public void updateProfile(UserProfileChangeRequest.Builder builder) {
        if(builder != null) {
            FirebaseAuth.getInstance().getCurrentUser().updateProfile(builder.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "update profile successfully");
                        Util.hideLoading(getContext());
                        Toast.makeText(getContext(), "Update profile successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Util.hideLoading(getContext());
                        Log.d(TAG, "update profile failed:" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    @OnClick(R.id.btnProfileImage)
    public void openBottomSheet() {
        if(bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        createBottomSheet();
        loadUserInfo();
        return v;
    }

    public void startPickImageIntent() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, getActivity().getString(R.string.select_image));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, Constants.REQUEST_PICK_IMAGE);
    }

    public void startTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + resultCode + ":data:" + data.toString()+":result:"+getActivity().RESULT_OK);

        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE && data !=null) {
            Bundle extras = data.getExtras();
            if(extras!=null) {
                bitmap = (Bitmap) extras.get("data");
                btnProfileImage.setImageBitmap(bitmap);
            }
        }

        if (requestCode == Constants.REQUEST_PICK_IMAGE && data != null) {
            Uri uri = data.getData();
            if(uri!=null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    btnProfileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Log.d(TAG, "IOException Pick Image:" + e.getMessage());
                }
            }

        }
    }


    public void createBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_profile_image, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss");
            }
        });
        LinearLayout btnTakePicture = (LinearLayout) bottomSheetView.findViewById(R.id.btnTakePicture);
        if (isCameraAvailable()) {
            btnTakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick take picture");
                    startTakePictureIntent();
                }
            });
        } else {
            Toast.makeText(getContext(), "Your device do not have camera, please select other option!", Toast.LENGTH_SHORT).show();
        }
        LinearLayout btnSelectFromStorage = (LinearLayout) bottomSheetView.findViewById(R.id.btnSelectFromStorage);
        btnSelectFromStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick select from storage");
                startPickImageIntent();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public boolean isCameraAvailable() {
        return getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
