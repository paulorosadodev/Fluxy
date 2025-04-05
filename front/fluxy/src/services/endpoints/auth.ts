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
    return api.post("/register", data);
};

export const login = async (data: LoginPayload) => {
    return api.post("/login", data);
};
