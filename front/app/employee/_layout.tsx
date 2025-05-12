import { Stack } from 'expo-router';
import { Image, Pressable, Text } from 'react-native';
import { useAuth } from '@/contexts/AuthContext';
import Colors from '@/constants/Colors';

export default function EmployeeLayout() {
    const { signOut } = useAuth();
    
      return (
        <Stack
          screenOptions={{
            headerStyle: {
              backgroundColor: Colors.background
            },
            headerShadowVisible: false,
            headerTitle: () => (
              <Image
                source={require('@/assets/images/logo.png')}
                style={{ width: 120, height: 40, resizeMode: 'contain' }}
              />
            ),
            headerRight: () => (
              <Pressable onPress={signOut} style={{ marginRight: 15 }}>
                <Text>
                  Sair
                </Text>
              </Pressable>
            ),
          }}
        />
      );
}