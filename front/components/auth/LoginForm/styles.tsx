import { StyleSheet } from 'react-native';
import Colors from '@/constants/Colors';

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 24,
        backgroundColor: Colors.background,
    },
    logo: {
        alignSelf: 'center',
        marginBottom: 20,
    },
    label: {
        fontSize: 20,
        fontWeight: 400,
        color: Colors.primaryText,
    },
    input: {
        fontSize: 20,
        height: 48,
        borderColor: Colors.border,
        borderWidth: 1,
        backgroundColor: Colors.inputBackground,
        marginBottom: 15,
        paddingHorizontal: 12,
        borderRadius: 15,
    },
    button: {
        backgroundColor: Colors.primary,
        paddingVertical: 10,
        borderRadius: 15,
        marginTop: 8,
    },
    buttonText: {
        color: Colors.tertiaryText,
        textAlign: 'center',
        fontWeight: 'bold',
        fontSize: 20,
    },
    errorText: {
        color: Colors.error,
        fontSize: 16,
        marginTop: 10,
        textAlign: 'center',
    },
});

export default styles;