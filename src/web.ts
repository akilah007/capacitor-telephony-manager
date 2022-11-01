import { WebPlugin } from '@capacitor/core';

import type { TelephonyManagerPlugin } from './definitions';

export class TelephonyManagerWeb
  extends WebPlugin
  implements TelephonyManagerPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
