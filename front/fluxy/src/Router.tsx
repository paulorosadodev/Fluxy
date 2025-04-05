import { Routes, Route, BrowserRouter, Navigate } from "react-router-dom";

import { useAuth } from "./hooks/useAuth";

import { Home } from "./pages/Home";
import { Login } from "./pages/Login";
import { Register } from "./pages/Register";

export function Router() {
    const { isAuthenticated } = useAuth();

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </BrowserRouter>
    );
}
