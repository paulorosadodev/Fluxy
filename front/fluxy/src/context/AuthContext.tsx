import { useCookies } from "react-cookie";
import { createContext, ReactNode, useEffect, useState } from "react";

import { User } from "../@types";

import { api } from "../services/api";
import * as auth from "../services/endpoints/auth";
import { Navigate } from "react-router-dom";
import { decryptRole, encryptRole } from "../utils";

type Login = {
    name: string,
    password: string,
}

interface AuthContextData {
    login: (data: Login) => Promise<string | void>;
    role: string;
    user: User | null;
    signOut: () => void;
    isAuthenticated: boolean;
    isLoading: boolean
}

export const AuthContext = createContext({} as AuthContextData);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {

    const [cookies, setCookie, removeCookie] = useCookies();
    const [user, setUser] = useState<User | null>(null);
    const [role, setRole] = useState("");
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const savedUser = cookies["@Auth:user"];
        const savedRole = cookies["@Auth:role"];
    
        if (savedUser) {
            setUser(savedUser);
        }
    
        if (savedRole) {
            const decryptedRole = decryptRole(savedRole);
            setRole(decryptedRole);
        }
        setIsLoading(false);
    }, []);

    const login = async ({name, password}: Login) => {

        try {
            const response = await auth.login({name, password});
            const {token, user, role} = response.data;

            api.defaults.headers.common.authorization = `Bearer ${token}`;

            setUser(user);

            let updatedRole = role;
            if (role === "admin") {
                updatedRole = "admin-products-suppliers-employees-customers-purchases-productSupplies";
            } 

            setRole(updatedRole);

            const encryptedRole = encryptRole(updatedRole);
            setCookie("@Auth:role", encryptedRole, {
                path: "/",
                maxAge: 60 * 60 * 2,
            });
            setCookie("@Auth:token", token, {
                path: "/",
                maxAge: 60 * 60 * 2, 
            });
            setCookie("@Auth:user", JSON.stringify(user), {
                path: "/",
                maxAge: 60 * 60 * 2, 
            });
        } catch (err: any) {
            return err.message;
        }

    };

    const signOut = () => {
        removeCookie("@Auth:token", { path: "/" });
        removeCookie("@Auth:user", { path: "/" });
        removeCookie("@Auth:role", { path: "/" });
        setUser(null);
        <Navigate to="/login" />;
    };

    return (
        <AuthContext.Provider value={{user, role, login, signOut, isLoading, isAuthenticated: !!user}}>
            {children}
        </AuthContext.Provider>
    );

};
