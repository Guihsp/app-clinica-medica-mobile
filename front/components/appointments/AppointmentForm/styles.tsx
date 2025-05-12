import { StyleSheet } from "react-native";
import Colors from "@/constants/Colors";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: Colors.background,
    },
    title: {
        fontSize: 24,
        fontFamily: "MontserratSemiBold",
        marginTop: 100,
        marginBottom: 48,
        textAlign: "center",
        color: Colors.primaryText,
    },
    label: {
        fontSize: 20,
        fontFamily: "MontserratMedium",
    },
    input: {
        fontSize: 20,
        backgroundColor: Colors.inputBackground,
        borderColor: Colors.border,
        borderWidth: 1,
        borderRadius: 15,
        color: Colors.primaryText,
        paddingHorizontal: 20,
        paddingVertical: 10,
        marginBottom: 20,
        fontFamily: "MontserratMedium",
        lineHeight: 25,
    },
    placeholder: {
        color: Colors.secondaryText,
        fontSize: 20,
    },
    inpuText: {
        fontSize: 20,
    },
    dateTextSelected: {
        color: Colors.secondaryText,
    },
    dateTextPlaceholder: {
        color: Colors.secondaryText,
    },
    button: {
        backgroundColor: "#007BFF",
        paddingVertical: 12,
        paddingHorizontal: 16,
        borderRadius: 4,
    },
    buttonText: {
        color: "#fff",
        fontSize: 16,
        textAlign: "center",
    },
});