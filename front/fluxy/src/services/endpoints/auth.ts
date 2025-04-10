// src/services/endpoints/auth.ts
import { api } from "../api";

interface RegisterPayload {
    name: string;
    password: string;
    confirmPassword: string;
}

interface LoginPayload {
    name: string;
    password: string;
}

export const register = async (data: RegisterPayload) => {
    try {
        const response = await api.post("/auth/register", data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao se cadastrar");
    }
};

export const login = async (data: LoginPayload) => {
    try {
        const response = await api.post("/auth/login", data);
        return response;
    } catch (error: any) {

        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao fazer login");
    }
};
