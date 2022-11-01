import { registerPlugin } from '@capacitor/core';

import type { TelephonyManagerPlugin } from './definitions';

const TelephonyManager = registerPlugin<TelephonyManagerPlugin>(
  'TelephonyManager',
  {
    web: () => import('./web').then(m => new m.TelephonyManagerWeb()),
  },
);

export * from './definitions';
export { TelephonyManager };
