import { Text, View } from "react-native";

import GenericButton from "@/components/Buttons/GenericButton";
import { styles } from "./styles";

interface AppointmentCardProps {
    appointment: {
        dateTime: string;
        medicName: string;
        status: "SCHEDULED" | "CANCELED" | "COMPLETED" | "RESCHEDULED";
        patientName: string;
        id: number
    };
}
export default function AppointmentCard({ appointment }: AppointmentCardProps) {
    const { dateTime, medicName, status, patientName, id } = appointment;

    const date = new Date(dateTime);
    const formattedDate = date.toLocaleDateString('pt-BR');
    const formattedTime = date.toLocaleTimeString('pt-BR', {
        hour: '2-digit',
        minute: '2-digit',
    });

    const formatStatus = (status: string): string => {
        const statusMap: { [key: string]: string } = {
            SCHEDULED: "Agendado",
            CANCELED: "Cancelado",
            COMPLETED: "Concluído",
            RESCHEDULED: "Reagendado",
        };

        return statusMap[status] || status;
    };

    const CancelAppointment = () => {
        // Implement cancel appointment logic here
        console.log(`Canceling appointment with ID: ${id}`);
    };

    const RescheduleAppointment = () => {
        // Implement reschedule appointment logic here
        console.log(`Rescheduling appointment with ID: ${id}`);
    };

    return (
        <View style={styles.card}>
            <Text style={[
                styles.status,
                status === "SCHEDULED" && styles.scheduled,
                status === "CANCELED" && styles.canceled,
                status === "COMPLETED" && styles.completed,
                status === "RESCHEDULED" && styles.rescheduled,
            ]}>{formatStatus(status)}</Text>
            <View style={styles.dateTimeContainer}>
                <Text style={styles.text}>Data: {formattedDate}</Text>
                <Text style={styles.text}>Hora: {formattedTime}</Text>
            </View>
            <Text style={styles.text}>Médico: {medicName}</Text>
            <Text style={styles.text}>Paciente: {patientName}</Text>
            {status !== "CANCELED" && status !== "COMPLETED" && (
                <>
                    <GenericButton
                        title="Cancelar"
                        onPress={CancelAppointment}
                        backgroundColor="red"
                    />
                    <GenericButton
                        title="Reagendar"
                        onPress={RescheduleAppointment}
                        backgroundColor="blue"
                    />
                </>
            )}
        </View>
    );
}