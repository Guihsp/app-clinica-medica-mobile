import { Slot } from 'expo-router';
import { useFonts } from 'expo-font';
import { View, ActivityIndicator, StyleSheet } from 'react-native';
import { AuthProvider } from '../contexts/AuthContext';

export default function RootLayout() {
  const [fontsLoaded] = useFonts({
    Montserrat: require('@/assets/fonts/Montserrat-Regular.ttf'),
    MontserratMedium: require('@/assets/fonts/Montserrat-Medium.ttf'),
    MontserratSemiBold : require('@/assets/fonts/Montserrat-SemiBold.ttf'),
    MontserratBold: require('@/assets/fonts/Montserrat-Bold.ttf'),
  });


  if (!fontsLoaded) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#0000ff" />
      </View>
    );
  }

  return (
    <AuthProvider>
      <Slot />
    </AuthProvider>
  );
}

const styles = StyleSheet.create({
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});