import NativeAPI from './NativeReactNativeIntentLauncher';

const NO_API_ERROR = 'Intent API is not available';

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
  if (!NativeAPI) {
    throw new Error(NO_API_ERROR);
  }
  return NativeAPI.startActivity(params) as Promise<ActivityResult>;
}

export function startService(params: IntentParams): Promise<void> {
  if (!NativeAPI) {
    throw new Error(NO_API_ERROR);
  }
  return NativeAPI.startService(params);
}
