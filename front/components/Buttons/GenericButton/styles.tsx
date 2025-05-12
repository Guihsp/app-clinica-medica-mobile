import { StyleSheet } from 'react-native';
import Colors from '@/constants/Colors';

export const styles = StyleSheet.create({
    button: {
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 15,
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        color: Colors.tertiaryText,
        fontSize: 20,
        fontFamily: 'MontserratBold',
    },
    blue: {
        backgroundColor: Colors.primary,
    },
    red: {
        backgroundColor: Colors.error,
    },
    green: {
        backgroundColor: Colors.success,
    },
});