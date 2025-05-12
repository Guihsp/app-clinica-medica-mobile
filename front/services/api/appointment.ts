import api from "./api";

export const createAppointment = async (medicId: string, dateTime: string, PacientId: string | null) => {
    const response = await api.post("/appointments/register", {
        medicId,
        dateTime,
        PacientId,
    }); 
    return response.data;
}

export const getAvailableSlots = async (date: string, medicId: string) => {
    const response = await api.get(`/appointments/available-slots`, {
        params: {
            medicId,
            date,
        },
    });
    return response.data;   
}

export const getAppointments = async () => {
    const response = await api.get("/appointments/list");
    return response.data;
}