import ReactNativeIntentLauncher from './NativeReactNativeIntentLauncher';

export interface IntentParams {
  action?: string;
  data?: string;
  type?: string;
  category?: string;
  extra?: Record<string, any>;
  packageName?: string;
  className?: string;
  flags?: number;
  foreground?: boolean;
}

export interface ActivityResult {
  resultCode: number;
  data?: string;
  extra?: Record<string, any>;
}

export function startActivity(params: IntentParams): Promise<ActivityResult> {
  return ReactNativeIntentLauncher.startActivity(
    params
  ) as Promise<ActivityResult>;
}

export function startService(params: IntentParams): Promise<void> {
  return ReactNativeIntentLauncher.startService(params);
}
