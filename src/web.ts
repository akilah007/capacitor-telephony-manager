import { WebPlugin } from '@capacitor/core';

import type { TelephonyManagerPlugin } from './definitions';

export class TelephonyManagerWeb
  extends WebPlugin
  implements TelephonyManagerPlugin {
  async outGoingCallNotification(options: { value: string }): Promise<{ value: string }> {
    console.log('outGoingCallNotification', options);
    return options;
  }
  async updateOutgoingNotification(options: { value: string }): Promise<{ value: string }> {
    console.log('updateOutgoingNotification', options);
    return options;
  }
  async updateOutgoingNotificationAction(options: { isSpeaker: boolean }): Promise<{ isSpeaker: boolean }> {
    console.log('updateOutgoingNotificationAction', options);
    return options;
  }
  async removeOutgoingNotification(): Promise<boolean> {
    console.log('removeOutgoingNotification');
    return true;
  }
}
