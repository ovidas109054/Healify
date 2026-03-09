package com.project.healify.di;

import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import com.project.healify.data.local.HealifyDatabase;
import com.project.healify.data.local.UserDao;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import java.io.File;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public HealifyDatabase provideDatabase(@ApplicationContext Context context, SecurityManager securityManager) {
        // 1. Force load native libraries
        SQLiteDatabase.loadLibs(context);
        
        String dbName = "healify_db";
        String passphrase = securityManager.getDatabasePassphrase();
        
        // 2. Comprehensive transition check: Delete if encrypted with old key
        File dbFile = context.getDatabasePath(dbName);
        if (dbFile.exists()) {
            try {
                // Try opening with current key to verify compatibility
                SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), passphrase, null, SQLiteDatabase.OPEN_READONLY).close();
            } catch (Exception e) {
                Log.e("Healify", "Incompatible database detected. Cleaning up for security update.");
                // Delete main DB file and common sidecar files
                context.deleteDatabase(dbName);
                new File(dbFile.getAbsolutePath() + "-wal").delete();
                new File(dbFile.getAbsolutePath() + "-shm").delete();
            }
        }

        // 3. Initialize Room with SQLCipher SupportFactory
        byte[] passphraseBytes = SQLiteDatabase.getBytes(passphrase.toCharArray());
        SupportFactory factory = new SupportFactory(passphraseBytes);

        return Room.databaseBuilder(context, HealifyDatabase.class, dbName)
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public UserDao provideUserDao(HealifyDatabase database) {
        return database.userDao();
    }
}
