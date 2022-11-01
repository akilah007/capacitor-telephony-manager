package com.verve.telephony.killerh;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "TelephonyManager")
public class TelephonyManagerPlugin extends Plugin {

    private TelephonyManager implementation = new TelephonyManager();

    private static final String PERMISSION_DENIED_ERROR = "Unable to do file operation, user denied permission request";

    public NotificationManager notificationManager;
    String channelId = "0";
    public NotificationCompat.Builder builder;
    final int NOTIFICATION_ID = 0;

    @Override
    public void load() {
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

    }
    @Override
    protected void handleOnNewIntent(Intent data) {
        super.handleOnNewIntent(data);
        Log.d("message==========", data.getAction());
        JSObject actionJson = new JSObject();
        actionJson.put("actionId", data.getAction());
        notifyListeners("notificationActionPerformed", actionJson, true);
    }

    @PluginMethod
    public void outGoingCallNotification(PluginCall call) {
        JSObject result = new JSObject();
        String displayName = call.getString("value");
        Intent fullScreenIntent = new Intent(getContext(), getActivity().getClass());
        fullScreenIntent.setAction("openCallView");
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getContext(),
                NOTIFICATION_ID, fullScreenIntent, 0);

        // Hang Up Intent
        Intent intentHangUp = new Intent(getContext(), getActivity().getClass());
        intentHangUp.setAction("hangUpCallButton");
        PendingIntent pendingIntentHangUp = PendingIntent.getActivity(getContext(),
                NOTIFICATION_ID, intentHangUp, 0);
        // Speaker Intent
        Intent intentSpeaker = new Intent(getContext(), getActivity().getClass());
        intentSpeaker.setAction("speakerOnButton");
        PendingIntent pendingIntentSpeaker = PendingIntent.getActivity(getContext(),
                NOTIFICATION_ID, intentSpeaker, 0);


        builder = new NotificationCompat.Builder(getContext(), channelId)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(displayName)
                .setContentText("Dialing")
                .setContentIntent(fullScreenPendingIntent)
                .setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setAutoCancel(false)
                .setUsesChronometer(false)
                .setShowWhen(false)
                .setOngoing(true)
                .setNotificationSilent()
                .addAction(new NotificationCompat.Action.Builder(R.drawable.icon,
                        HtmlCompat.fromHtml("<font color=" + ContextCompat.getColor(getContext(), R.color.colorDanger) + ">" + "Hang up" + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        , pendingIntentHangUp).build())
                .addAction(new NotificationCompat.Action.Builder(R.drawable.icon,
                        HtmlCompat.fromHtml("<font color=" + ContextCompat.getColor(getContext(), R.color.colorSuccess) + ">" + "Turn Speaker on" + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        , pendingIntentSpeaker).build());
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Outgoing Call",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.d("message", "Notification Received");
    }

    @ActivityCallback
    private void hangUpCallButton(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        JSObject result2 = new JSObject();
        result2.put("hh", "hahahahah");
        Log.d("message", "Notification Hahahaha...");
        call.resolve(result2);

        // Do something with the result data
    }



    @PluginMethod
    public void updateOutgoingNotification(PluginCall call) {
        JSObject result = new JSObject();
        builder
                .setContentText("Outgoing call")
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    @SuppressLint("RestrictedApi")
    @PluginMethod
    public void updateOutgoingNotificationAction(PluginCall call) {
        JSObject result = new JSObject();
        Boolean mode = call.getBoolean("isSpeaker");
        Log.d("message**********", String.valueOf(mode));
        // Speaker Intent
        Intent intentSpeaker = new Intent(getContext(), getActivity().getClass());
        builder.mActions.remove(1);
        assert mode != null;
        intentSpeaker.setAction("speakerOnButton");
        PendingIntent pendingIntentSpeaker = PendingIntent.getActivity(getContext(),
                NOTIFICATION_ID, intentSpeaker, 0);
        if (mode) {
            builder
                    .addAction(new NotificationCompat.Action.Builder(R.drawable.icon,
                            HtmlCompat.fromHtml("<font color=" + ContextCompat.getColor(getContext(), R.color.colorSuccess) + ">" + "Turn Speaker off" + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                            , pendingIntentSpeaker).build());
            ;
        } else {
            builder
                    .addAction(new NotificationCompat.Action.Builder(R.drawable.icon,
                            HtmlCompat.fromHtml("<font color=" + ContextCompat.getColor(getContext(), R.color.colorSuccess) + ">" + "Turn Speaker on" + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                            , pendingIntentSpeaker).build());
            ;

        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    @PluginMethod
    public void removeOutgoingNotification(PluginCall call) {
        JSObject result = new JSObject();
        notificationManager.cancelAll();
        Log.d("message", "Notification Removed");
        call.resolve(result);
    }
}
