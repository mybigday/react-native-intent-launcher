# @fugood/react-native-intent-launcher

Android Intent launcher

## Installation


```sh
npm install @fugood/react-native-intent-launcher
```


## Usage


```js
import { startActivity, startService } from '@fugood/react-native-intent-launcher';

// Start an activity
startActivity({
  action: 'android.settings.SETTINGS',
}).then((result) => {
  console.log('Activity result:', result);
});

// Start an activity with data, extras, and specific component
startActivity({
  action: 'android.intent.action.VIEW',
  data: 'https://www.google.com',
  extra: {
    'key': 'value',
  },
  // Optional: target specific package/class
  // packageName: 'com.android.chrome',
  // className: 'com.google.android.apps.chrome.Main',
}).then((result) => {
  console.log('Activity finished with result:', result);
});

// Start a service
startService({
  action: 'com.example.MY_SERVICE',
}).then(() => {
  console.log('Service started');
}).catch((e) => {
  console.error('Failed to start service:', e);
});
```

## API

### `startActivity(params: IntentParams): Promise<ActivityResult>`

Starts an Android activity with the given parameters. Returns a Promise that resolves with the activity result.

### `startService(params: IntentParams): Promise<void>`

Starts an Android service with the given parameters.

### `IntentParams`

| Property | Type | Description |
|Data | | |
| `action` | `string` | The intent action, e.g., `android.intent.action.VIEW`. |
| `data` | `string` | The intent data URI. |
| `type` | `string` | The intent MIME type. |
| `category` | `string` | The intent category. |
| `extra` | `Object` | Key-value pairs for intent extras. |
| `packageName` | `string` | The package name of the component. |
| `className` | `string` | The class name of the component. |
| `flags` | `number` | Intent flags (integer). |
| `foreground` | `boolean` | If true, use `startForegroundService` on Android 8.0+. |

### `ActivityResult`

| Property | Type | Description |
| `resultCode` | `number` | The result code returned by the activity. |
| `data` | `string` | The result data URI, if any. |
| `extra` | `Object` | The result extras, if any. |


## Contributing

- [Development workflow](CONTRIBUTING.md#development-workflow)
- [Sending a pull request](CONTRIBUTING.md#sending-a-pull-request)
- [Code of conduct](CODE_OF_CONDUCT.md)

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
