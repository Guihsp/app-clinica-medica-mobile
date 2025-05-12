import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';
import { router } from 'expo-router';
import * as SecureStore from 'expo-secure-store';

import { login } from '@/services/api/auth'; 

type UserType = 'PATIENT' | 'MEDIC' | 'EMPLOYEE';

interface User {
    id: string;
    name: string;
    type: UserType;
    token: string;
}

interface AuthContextData {
    user: User | null;
    signIn: (email: string, password: string) => Promise<void>;
    signOut: () => void;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);    

    useEffect(() => {
        const loadUserFromStorage = async () => {
            const storedUser = await SecureStore.getItemAsync('user');
            if (storedUser) {
                setUser(JSON.parse(storedUser));
            }
        };

        loadUserFromStorage();
    }, []);

    const signIn = async (email: string, password: string) => {
        try {
            const data = await login(email, password);

            const userData = {
                id: data.id,
                name: data.username,
                email: data.email,
                type: data.userType,
                token: data.token,
            };

            await SecureStore.setItemAsync('token', data.token);
            await SecureStore.setItemAsync('userType', data.userType);

            setUser(userData);


            switch (userData.type) {
                case 'PATIENT':
                    router.replace('/patient');
                    break;
                case 'MEDIC':
                    router.replace('/medic');
                    break;
                case 'EMPLOYEE':
                    router.replace('/employee');
                    break;
            }
        } catch (error) {
            throw new Error('Erro ao fazer login');
        }
    };

    const signOut = () => {
        setUser(null);
        SecureStore.deleteItemAsync('token');
        SecureStore.deleteItemAsync('userType');
        router.replace('/auth/login');
    };

    return (
        <AuthContext.Provider value={{ user, signIn, signOut }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
