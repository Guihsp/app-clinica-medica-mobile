import { StyleSheet } from "react-native";
import Colors from "@/constants/Colors";

export const styles = StyleSheet.create({
    card: {
        paddingHorizontal: 20,
        paddingVertical: 15,
        borderRadius: 15,
        backgroundColor: Colors.background,
        gap: 10,
        shadowColor: Colors.shadow,
        shadowOffset: {
            width: 4,
            height: 4,
        },
        shadowOpacity: 0.30,
        shadowRadius: 1.41,
        elevation: 4,
        width: 340,
    },
    status: {
        fontFamily: "MontserratSemiBold",
        fontSize: 20,
    },
    scheduled: {
        color: Colors.success,
    },
    canceled: {
        color: Colors.error,
    },
    completed: {
        color: Colors.success,
    },
    rescheduled: {
        color: Colors.primary,
    },
    dateTimeContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
    },
    text: {
        fontFamily: "MontserratRegular",
        fontSize: 20,
        color: Colors.secondaryText,
    },
});