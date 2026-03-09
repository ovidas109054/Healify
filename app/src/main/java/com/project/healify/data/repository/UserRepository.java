package com.project.healify.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.project.healify.data.local.UserDao;
import com.project.healify.data.local.UserEntity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class UserRepository {
    private static final String PREF_NAME = "healify_prefs";
    private static final String KEY_USER_EMAIL = "current_user_email";
    
    private final UserDao userDao;
    private final ExecutorService executorService;
    private final SharedPreferences sharedPreferences;
    private String currentUserEmail;

    @Inject
    public UserRepository(UserDao userDao, @ApplicationContext Context context) {
        this.userDao = userDao;
        this.executorService = Executors.newSingleThreadExecutor();
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // Restore session from SharedPreferences
        this.currentUserEmail = sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public void saveUser(UserEntity user) {
        this.currentUserEmail = user.email;
        // Persist session
        sharedPreferences.edit().putString(KEY_USER_EMAIL, user.email).apply();
        executorService.execute(() -> userDao.insertUser(user));
    }

    public void getUser(String email, OnUserLoadedCallback callback) {
        executorService.execute(() -> {
            UserEntity user = userDao.getUser(email);
            callback.onUserLoaded(user);
        });
    }

    public void getCurrentUser(OnUserLoadedCallback callback) {
        if (currentUserEmail != null) {
            getUser(currentUserEmail, callback);
        } else {
            callback.onUserLoaded(null);
        }
    }

    public void setCurrentUserEmail(String email) {
        this.currentUserEmail = email;
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply();
    }
    
    public void logout() {
        this.currentUserEmail = null;
        sharedPreferences.edit().remove(KEY_USER_EMAIL).apply();
    }

    public interface OnUserLoadedCallback {
        void onUserLoaded(UserEntity user);
    }
}
