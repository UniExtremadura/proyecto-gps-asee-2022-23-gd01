package es.unex.infinitetime;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import es.unex.infinitetime.api.RemoteDAOs;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.repository.PersistenceUser;
import es.unex.infinitetime.repository.Repository;
import es.unex.infinitetime.viewmodel.ViewModelFactory;

public class AppContainer {

    private final InfiniteDatabase database;
    private final RemoteDAOs remoteDAOs;

    public final Repository repository;
    public final ViewModelFactory factory;
    public final SharedPreferences sharedPreferences;
    public final PersistenceUser persistenceUser;

    public AppContainer(Context context) {
        database = InfiniteDatabase.getDatabase(context);
        remoteDAOs = new RemoteDAOs();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        persistenceUser = PersistenceUser.getInstance();
        persistenceUser.setPreferences(sharedPreferences);
        persistenceUser.loadUserId();

        repository = Repository.getInstance(database, remoteDAOs, persistenceUser);
        factory = new ViewModelFactory(repository);
    }

}
