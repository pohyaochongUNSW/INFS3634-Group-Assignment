package com.example.triviafun.asynctask;

import com.example.triviafun.model.User;

public interface AsyncTaskGetUserDelegate {
    void handleTaskGetUserResult(User user);
}
