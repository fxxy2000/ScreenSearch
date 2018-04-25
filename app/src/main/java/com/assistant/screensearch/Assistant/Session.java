package com.assistant.screensearch.Assistant;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.View;

import com.assistant.screensearch.R;

public class Session extends VoiceInteractionSession {
    static final String TAG = "Session";

    private  int mCurrentTask = -1;

    Session(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityManager am = getContext().getSystemService(ActivityManager.class);
        am.setWatchHeapLimit(40 * 1024 * 1024);
    }

    @Override
    public void onShow(Bundle args, int showFlags) {
        super.onShow(args, showFlags);
    }

    @Override
    public void onHide() {
        super.onHide();
    }

    @Override
    public View onCreateContentView() {
        final View view = getLayoutInflater().inflate(R.layout.voice_interaction_session, null);
        view.findViewById(R.id.top_content).setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {

    }

    @Override
    public void onHandleScreenshot(Bitmap screenshot) {
        
    }


    @Override
    public void onComputeInsets(Insets outInsets) {
        super.onComputeInsets(outInsets);
    }

    @Override
    public void onTaskStarted(Intent intent, int taskId) {
        super.onTaskStarted(intent, taskId);
        mCurrentTask = taskId;
    }

    @Override
    public void onTaskFinished(Intent intent, int taskId) {
        super.onTaskFinished(intent, taskId);
        if (mCurrentTask == taskId) {
            mCurrentTask = -1;
        }
    }

    @Override
    public void onLockscreenShown() {
        if (mCurrentTask < 0) {
            hide();
        }
    }
}