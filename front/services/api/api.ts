import axios from 'axios';
import * as SecureStore from 'expo-secure-store';

const api = axios.create({
    baseURL: 'http://192.168.15.11:8080', 
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(async (config) => {
    const token = await getToken(); 
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const getToken = async () => {
    const token = await SecureStore.getItemAsync('token');
    return token;
};

export default api;
