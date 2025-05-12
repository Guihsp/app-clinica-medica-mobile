import { StyleSheet } from "react-native";
import Colors from "@/constants/Colors";

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: Colors.background,
    },
    title: {
        fontFamily: "MontserratSemiBold",
        fontSize: 24,
        color: Colors.primaryText,
        marginTop: 20,
        marginBottom: 10,
        textAlign: "center",
    },
    sectionTitle: {
        fontFamily: "MontserratMedium",
        fontSize: 20,
        color: Colors.primaryText,
        marginHorizontal: 20,
    },
    ContentList: {
        paddingVertical: 20,
        paddingHorizontal: 20,
        gap: 20,
    },
    noAppointmentsText: {
        fontFamily: "MontserratRegular",
        fontSize: 16,
        color: Colors.secondaryText,
    },
});