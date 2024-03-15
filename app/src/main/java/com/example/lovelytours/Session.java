package com.example.lovelytours;

import com.example.lovelytours.models.Guide;
import com.example.lovelytours.models.User;

public class Session {

    private  static Session session;
    private User currentUser;

    private Session(){}

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isGuide() {
        return currentUser instanceof Guide;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
        }
        return session;

    }
}
