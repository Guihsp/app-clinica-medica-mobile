import { router } from 'expo-router';
import { View, Text, TouchableOpacity, Alert, Platform } from 'react-native';
import { useState, useEffect } from 'react';
import RNPickerSelect from 'react-native-picker-select';
import DateTimePicker from '@react-native-community/datetimepicker';

import { getAllMedics } from '@/services/api/medics';
import { getAvailableSlots, createAppointment } from '@/services/api/appointment';
import GenericButton from '@/components/Buttons/GenericButton';

import { styles } from './styles';

interface Medic {
    id: string;
    name: string;
}

interface Slot {
    time: string;
}

export default function AppointmentForm() {
    const [medics, setMedics] = useState<Medic[]>([]);
    const [selectedMedic, setSelectedMedic] = useState<string | null>(null);
    const [date, setDate] = useState<Date | null>(null);
    const [showDatePicker, setShowDatePicker] = useState(false);
    const [availableSlots, setAvailableSlots] = useState<Slot[]>([]);
    const [selectedSlot, setSelectedSlot] = useState<string | null>(null);

    useEffect(() => {
        const fetchMedics = async () => {
            try {
                const data = await getAllMedics();
                
                setMedics(data);
            } catch (error) {
                console.error('Error fetching medics:', error);
                Alert.alert('Erro', 'Não foi possível carregar os médicos.');
            }
        };

        fetchMedics();
    }, []);

    useEffect(() => {
        if (selectedMedic && date) {
            const fetchAvailableSlots = async () => {
                try {
                    const formattedDate = date.toISOString().split('T')[0];
                    const slots = await getAvailableSlots(formattedDate, selectedMedic);
                
                    setAvailableSlots(slots);
                } catch (error) {
                    Alert.alert('Erro', 'Não foi possível carregar os horários disponíveis.');
                }
            };

            fetchAvailableSlots();
        }
    }, [selectedMedic, date]);

    const handleAppointment = async () => {
        if (!selectedMedic || !date || !selectedSlot) {
            Alert.alert('Erro', 'Preencha todos os campos.');
            return;
        }

        try {
            const dateTime = `${date.toISOString().split('T')[0]}T${selectedSlot}:00`;
            await createAppointment(selectedMedic, dateTime, null);
            Alert.alert('Sucesso', 'Consulta agendada com sucesso!');

        
            router.back();
        } catch (error) {
            Alert.alert('Erro', 'Não foi possível agendar a consulta.');
        }
    };

    const handleDateChange = (event: any, selectedDate?: Date) => {
        setShowDatePicker(false);
        if (selectedDate) {
            setDate(selectedDate);
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Agendar consulta</Text>

            <Text style={styles.label}>Médico:</Text>
            <RNPickerSelect
                useNativeAndroidPickerStyle={false}
                style={{
                    inputIOS: styles.input,
                    inputAndroid: styles.input,
                    placeholder: styles.placeholder
                }}
                onValueChange={(value) => setSelectedMedic(value)}
                items={medics.map((medic) => ({
                    label: medic.name,
                    value: medic.id,
                }))}
                placeholder={{ label: 'Selecione um médico', value: null }}
            />

            <Text style={styles.label}>Data:</Text>
            <TouchableOpacity onPress={() => setShowDatePicker(true)} style={styles.input}>
                <Text style={[
                    styles.inpuText,
                    date ? styles.dateTextSelected : styles.dateTextPlaceholder
                ]}>{date ? date.toLocaleDateString() : 'Selecione uma data'}</Text>
            </TouchableOpacity>
            {showDatePicker && (
                <DateTimePicker
                    value={date || new Date()}
                    mode="date"
                    display={Platform.OS === 'ios' ? 'inline' : 'default'}
                    onChange={handleDateChange}
                />
            )}

            <Text style={styles.label}>Horário:</Text>
            <RNPickerSelect
                useNativeAndroidPickerStyle={false}
                disabled={!availableSlots.length}
                style={{
                    inputIOS: styles.input,
                    inputAndroid: styles.input,
                    placeholder: styles.placeholder
                }}
                onValueChange={(value) => setSelectedSlot(value)}
                items={availableSlots.map((slot) => ({
                    label: slot.time,
                    value: slot.time,
                }))}
                placeholder={{ label: 'Selecione um horário', value: null }}
            />

            <GenericButton
                title="Agendar"
                onPress={handleAppointment}
                backgroundColor='blue'
            />
        </View>
    );
}