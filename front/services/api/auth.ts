import api from './api';

interface LoginResponse {
    id: string;
    username: string;
    email: string;
    token: string;
    userType: 'PATIENT' | 'MEDIC' | 'EMPLOYEE';
}

export const login = async (email: string, password: string): Promise<LoginResponse> => {
    const response = await api.post('/login', { email, password });
    console.log('Login response:', response.data);
    return response.data;
};
