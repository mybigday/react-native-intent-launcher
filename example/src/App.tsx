import { Text, View, StyleSheet, Button } from 'react-native';
import { startActivity } from '@fugood/react-native-intent-launcher';

export default function App() {
  const handleStartActivity = async () => {
    try {
      const result = await startActivity({
        action: 'android.settings.SETTINGS',
      });
      console.log('Result:', result);
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <View style={styles.container}>
      <Button title="Open Settings" onPress={handleStartActivity} />
      <Text>Check console for results</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
