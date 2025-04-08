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
    } catch (e) {
        console.log(e);
        throw {
            response: {
                data: {
                    error: "Internal Server Error"
                }
            }
        }; 
    }
};

export const login = async (data: LoginPayload) => {
    try {
        const response = await api.post("/login", data);
        console.log(response);
        return response;
    } catch (e) {
        console.log(e);
        throw {
            response: {
                data: {
                    error: "Internal Server Error"
                }
            }
        }; 
    }
};
