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
        const response = await api.post("/register", data);
        return response;
    } catch (error) {
        console.error("Erro ao registrar:", error);
        throw error; 
    }
};

export const login = async (data: LoginPayload) => {
    try {
        const response = await api.post("/login", data);
        return response;
    } catch (error) {
        console.error("Erro ao logar:", error);
        throw error;
    }
};
