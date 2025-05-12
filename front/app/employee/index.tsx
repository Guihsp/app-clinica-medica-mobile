import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';

import { useAuth } from '@/contexts/AuthContext';
import ButtonCard from '@/components/Buttons/CardButton';
import Colors from '@/constants/Colors';

export default function EmployeeHomeScreen() {
    const { user } = useAuth();
    

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Bem Vindo, {user?.name}</Text>

            <ButtonCard
                title="Cadastrar paciente ou médico"
                icon="user"
                route=""
            />
            <ButtonCard
                title="Agendar, reagendar ou cancelar consulta"
                icon="reportMedical"
                route=""
            />
            <ButtonCard
                title="Lista de consultas agendadas"
                icon="calendar"
                route=""
            />
                
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: Colors.background,
    },
    title: {
        textAlign: 'left',
        fontFamily: 'MontserratSemiBold',
        fontSize: 24,
        color: Colors.textPrimary,
        width: '70%',   
        marginVertical: 20,  
    },
});
