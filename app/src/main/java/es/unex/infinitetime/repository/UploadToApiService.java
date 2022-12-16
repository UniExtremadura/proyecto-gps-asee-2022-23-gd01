package es.unex.infinitetime.repository;

import static java.lang.Thread.sleep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import es.unex.infinitetime.api.RemoteDAOs;
import es.unex.infinitetime.model.InfiniteDatabase;

public class UploadToApiService extends Service {

    private Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        InfiniteDatabase db = InfiniteDatabase.getDatabase(getApplicationContext());
        RemoteDAOs remoteDAOs = new RemoteDAOs();
        UploadToAPI uploadToAPI = new UploadToAPI(db, remoteDAOs);

        thread = new Thread(uploadToAPI);

        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("UploadToApiService", "onTaskRemoved");
        thread.start();

        // Evitar que el servicio termine antes de que termine el hilo
        // que sube los datos a la API
        try {
            sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
