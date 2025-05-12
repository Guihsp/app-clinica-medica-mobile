import api from "./api";


export const getAllMedics = async () => {
    const response = await api.get("/medic/list");
    return response.data;
}