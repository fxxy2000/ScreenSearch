package com.assistant.screensearch;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RunWith(AndroidJUnit4.class)
public class GoogleVisionServiceTest {

    @Test
    public void ensureSimpleOcr() throws InterruptedException {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        final Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.testbitmap);

        final CountDownLatch signal = new CountDownLatch(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                GoogleVisionService gsv = new GoogleVisionService(context, uri, new GoogleVisionService.GoogleVisionServiceCallback() {
                    @Override
                    public void onReceivingTextsAndBounds(GoogleVisionService.GsvResult gsvResult) {
                        Assert.assertTrue(gsvResult.map.size() > 0);
                        signal.countDown();
                    }
                });
            }
        };
        new Handler(Looper.getMainLooper()).post(runnable);

        Assert.assertTrue(signal.await(10, TimeUnit.SECONDS));

    }

}
