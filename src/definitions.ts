import type {PluginListenerHandle} from "@capacitor/core";

export interface TelephonyManagerPlugin {
  addListener(eventName: 'notificationActionPerformed', listenerFunc: (notificationAction: any) => void): Promise<PluginListenerHandle> & PluginListenerHandle;
  outGoingCallNotification(options: { value: string }): Promise<{ value: string }>;
  updateOutgoingNotification(options: { value: string }): Promise<{ value: string }>;
  updateOutgoingNotificationAction(options: { isSpeaker: boolean }): Promise<{ isSpeaker: boolean }>;
  removeOutgoingNotification(): Promise<boolean>;
}
