import { useCookies } from "react-cookie";
import { createContext, ReactNode, useEffect, useState } from "react";

import { Store } from "../@types";

import { api } from "../services/api";
import * as auth from "../services/endpoints/auth";
import { Navigate } from "react-router-dom";

type Login = {
    name: string,
    password: string,
}

interface AuthContextData {
    login: (data: Login) => Promise<string | void>;
    store: Store | null;
    signOut: () => void;
    isAuthenticated: boolean
}

export const AuthContext = createContext({} as AuthContextData);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {

    const [cookies, setCookie, removeCookie] = useCookies();
    const [store, setStore] = useState<Store | null>(null);

    useEffect(() => {
        const savedStore = cookies["@Auth:user"];
        if (savedStore) {
            setStore(savedStore);
        }
    }, []);

    const login = async ({name, password}: Login) => {

        try {
            const response = await auth.login({name, password});
            console.log(response);
            const {token, store} = response.data;
            console.log(token);
            api.defaults.headers.common.authorization = `Bearer ${token}`;

            setStore(store);

            setCookie("@Auth:token", token, {
                path: "/",
                maxAge: 60 * 60 * 2, 
            });
            setCookie("@Auth:user", JSON.stringify(store), {
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
        setStore(null);
        <Navigate to="/login" />;
    };

    return (
        <AuthContext.Provider value={{store, login, signOut, isAuthenticated: !!store}}>
            {children}
        </AuthContext.Provider>
    );

};
