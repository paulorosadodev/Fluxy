import { useCookies } from "react-cookie";
import { createContext, ReactNode, useEffect, useState } from "react";

import { User } from "../@types";

import { api } from "../services/api";
import * as auth from "../services/endpoints/auth";
import { Navigate } from "react-router-dom";

type Login = {
    name: string,
    password: string,
}

interface AuthContextData {
    login: (data: Login) => Promise<string | void>;
    user: User | null;
    signOut: () => void;
    isAuthenticated: boolean
}

export const AuthContext = createContext({} as AuthContextData);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {

    const [cookies, setCookie, removeCookie] = useCookies();
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        const savedUser = cookies["@Auth:user"];
        if (savedUser) {
            setUser(savedUser);
        }
    }, []);

    const login = async ({name, password}: Login) => {

        try {
            const response = await auth.login({name, password});
            const {token, user} = response.data;

            api.defaults.headers.common.authorization = `Bearer ${token}`;

            setUser(user);

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
        setUser(null);
        <Navigate to="/login" />;
    };

    return (
        <AuthContext.Provider value={{user, login, signOut, isAuthenticated: true}}>
            {children}
        </AuthContext.Provider>
    );

};
