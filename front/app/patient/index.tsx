import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

import { useAuth } from '@/contexts/AuthContext';
import ButtonCard from '@/components/Buttons/CardButton';
import Colors from '@/constants/Colors';

export default function PatientHomeScreen() {
    const { user } = useAuth();

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Bem Vindo, {user?.name}</Text>

            <ButtonCard
                title="Agendar Consulta"
                icon="plus"
                route="/patient/registerAppointment"
            />

            <ButtonCard
                title="Minhas Consultas"
                icon="list"
                route="/patient/appointments"
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
        color: Colors.primaryText,
        width: '70%',   
        marginVertical: 20,  
    },
});
