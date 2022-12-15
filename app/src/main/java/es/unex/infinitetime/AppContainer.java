package es.unex.infinitetime;

import android.content.Context;

import es.unex.infinitetime.api.RemoteDAOs;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.repository.Repository;
import es.unex.infinitetime.viewmodel.ViewModelFactory;

public class AppContainer {

    private final InfiniteDatabase database;
    private final RemoteDAOs remoteDAOs;

    public final Repository repository;
    public final ViewModelFactory factory;

    public AppContainer(Context context) {
        database = InfiniteDatabase.getDatabase(context);
        remoteDAOs = new RemoteDAOs();
        repository = Repository.getInstance(database, remoteDAOs);
        factory = new ViewModelFactory(repository);
    }

}
