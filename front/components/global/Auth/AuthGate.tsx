import { useEffect } from 'react';
import { router } from 'expo-router';
import * as SecureStore from 'expo-secure-store';
import { View, ActivityIndicator } from 'react-native';

export default function AuthGate() {
    useEffect(() => {
        const checkAuth = async () => {
            const token = await SecureStore.getItemAsync('token');
            const userType = await SecureStore.getItemAsync('userType');

            if (!token || !userType) {
                router.replace('/auth/login');
                return;
            }

            switch (userType) {
                case 'PATIENT':
                    router.replace('/patient');
                    break;
                case 'MEDIC':
                    router.replace('/medic');
                    break;
                case 'EMPLOYEE':
                    router.replace('/employee');
                    break;
                default:
                    router.replace('/auth/login');
            }
        };

        checkAuth();
    }, []);

    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <ActivityIndicator size="large" />
        </View>
    );
}
