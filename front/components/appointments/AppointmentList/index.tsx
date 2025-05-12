import { Text, View, ScrollView } from 'react-native';
import { useEffect, useState } from "react";

import AppointmentCard from "@/components/appointments/AppointmentCard";
import { getAppointments } from '@/services/api/appointment';
import { styles } from "./styles";

export default function AppointmentList() {
    const [appointments, setAppointments] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchAppointments = async () => {
            try {
                const data = await getAppointments();
                setAppointments(data);
            } catch (err) {
                setError("Failed to load appointments");
            } finally {
                setLoading(false);
            }
        };

        fetchAppointments();
    }, []);

    if (loading) {
        return <Text style={styles.loadingText}>Carregando...</Text>;
    }

    const scheduledAppointments = appointments.filter(
        (appointment) => appointment.status === "SCHEDULED" || appointment.status === "RESCHEDULED"
    );
    const canceledAppointments = appointments.filter(
        (appointment) => appointment.status === "CANCELED"
    );
    const completedAppointments = appointments.filter(
        (appointment) => appointment.status === "COMPLETED"
    );

    return (
        <View style={styles.container}>
            <ScrollView
                showsVerticalScrollIndicator={false}
            >

            <Text style={styles.title}>Lista de Consultas</Text>
            {error && <Text style={styles.errorText}>{error}</Text>}

            <Text style={styles.sectionTitle}>Agendadas e Reagendadas</Text>
            <ScrollView
                horizontal
                showsHorizontalScrollIndicator={false}
                contentContainerStyle={styles.ContentList}
            >
                {scheduledAppointments.length === 0 ? (
                    <Text style={styles.noAppointmentsText}>Nenhuma consulta agendada ou reagendada.</Text>
                ) : (
                    scheduledAppointments.map((appointment) => (
                        <AppointmentCard key={appointment.id} appointment={appointment} />
                    ))
                )}
            </ScrollView>

            <Text style={styles.sectionTitle}>Canceladas</Text>
            <ScrollView
                horizontal
                showsHorizontalScrollIndicator={false}
                contentContainerStyle={styles.ContentList}
                style={styles.appointmentsList}
            >
                {canceledAppointments.length === 0 ? (
                    <Text style={styles.noAppointmentsText}>Nenhuma consulta cancelada.</Text>
                ) : (
                    canceledAppointments.map((appointment) => (
                        <AppointmentCard key={appointment.id} appointment={appointment} />
                    ))
                )}
            </ScrollView>

            <Text style={styles.sectionTitle}>Concluídas</Text>
            <ScrollView
                horizontal
                showsHorizontalScrollIndicator={false}
                contentContainerStyle={styles.ContentList}
            >
                {completedAppointments.length === 0 ? (
                    <Text style={styles.noAppointmentsText}>Nenhuma consulta concluída.</Text>
                ) : (
                    completedAppointments.map((appointment) => (
                        <AppointmentCard key={appointment.id} appointment={appointment} />
                    ))
                )}
            </ScrollView>
            </ScrollView>
        </View >
    );
}