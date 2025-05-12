import React, { useState } from 'react';
import { View, Text, TextInput, Alert, Image } from 'react-native';

import { useAuth } from '@/contexts/AuthContext';
import GenericButton from '@/components/Buttons/GenericButton';
import styles from './styles';

export default function LoginForm() {
    const { signIn } = useAuth();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(false);

    const handleLogin = async () => {
        if (!email || !password) {
            Alert.alert('Erro', 'Preencha todos os campos');
            return;
        }

        try {
            await signIn(email, password);
        } catch (error) {
            setError(true);
        }
    };

    return (
        <View style={styles.container}>
            <Image style={styles.logo}
                source={require('@/assets/images/logo.png')}
                resizeMode="contain"
            />
            <Text style={styles.errorText}>
                {error && 'Email ou senha inválidos'}
            </Text>
            <Text style={styles.label}>Email:</Text>
            <TextInput
                placeholder="Digite seu email..."
                value={email}
                onChangeText={setEmail}
                autoCapitalize="none"
                keyboardType="email-address"
                style={styles.input}
            />

            <Text style={styles.label}>Senha:</Text>
            <TextInput
                placeholder="Digite sua senha..."
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                style={styles.input}
            />

            <GenericButton
                title="Entrar"
                onPress={handleLogin}
                backgroundColor="blue"
            />
        </View>
    );
}