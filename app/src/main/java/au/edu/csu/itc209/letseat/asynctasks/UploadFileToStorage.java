package au.edu.csu.itc209.letseat.asynctasks;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import au.edu.csu.itc209.letseat.constant.Constants;
import au.edu.csu.itc209.letseat.util.Util;

/**
 * Created by NhutHuynh on 10/6/17.
 */

public class UploadFileToStorage extends AsyncTask<Bitmap, Integer, UploadTask> {
    private StorageReference storageRef;
    private StorageReference folderRef;
    private StorageReference fileRef;
    public AsyncResponse delegate = null;

    public UploadFileToStorage(AsyncResponse response) {
        this.delegate = response;
        storageRef = FirebaseStorage.getInstance().getReference();
        folderRef = storageRef.child(Constants.FIREBASE_STORAGE_FOLDER_NODE);
    }

    public interface AsyncResponse {
        void onPostExecuteCallback(Uri t);
        void onPostExecuteCallbackFailed(Exception ex);
    }

    @Override
    protected void onPostExecute(UploadTask task) {
        super.onPostExecute(task);
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                delegate.onPostExecuteCallbackFailed(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                delegate.onPostExecuteCallback(downloadUrl);
            }
        });

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected UploadTask doInBackground(Bitmap... bitmap) {
        if (bitmap != null && bitmap.length > 0) {
            String fileName = Util.getStringTime();
            fileRef = storageRef.child(String.format("%s/%s.jpg", Constants.FIREBASE_STORAGE_FOLDER_NODE,fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap[0].compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            return fileRef.putBytes(data);
        }
        return null;
    }
}
