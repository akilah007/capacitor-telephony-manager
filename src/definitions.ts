export interface TelephonyManagerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
