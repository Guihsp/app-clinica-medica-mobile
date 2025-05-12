import { StyleSheet } from "react-native";

import Colors from "@/constants/Colors";

export const styles = StyleSheet.create({
    card: {
        backgroundColor: Colors.background,
        borderRadius: 10,
        shadowColor: Colors.shadow,
        shadowOffset: {
            width: 0,
            height: 5,
        },
        shadowOpacity: 0.50,
        shadowRadius: 4,
        elevation: 10,
        paddingHorizontal: 15,
        flexDirection: "row",
        alignItems: "center",
        gap: 15,
        marginBottom: 25,
        height: 105
    },
    icon: {
        alignItems: "center",
        justifyContent: "center",
    },
    title: {
        color: Colors.primaryText,
        fontSize: 20,
        fontFamily: "MontserratMedium",
        width: "70%",
    },
});