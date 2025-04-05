import { Routes, Route, BrowserRouter, Navigate } from "react-router-dom";

import { useAuth } from "./hooks/useAuth";

import { DefaultLayout } from "./layouts/DefaultLayout";

import { Home } from "./pages/Home";
import { Login } from "./pages/Login";
import { Register } from "./pages/Register";
import MainDashboard from "./pages/Dashboards/Main";

export function Router() {
    const { isAuthenticated } = useAuth();

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                <Route element={<DefaultLayout />}>
                    <Route path="/dashboard" element={ isAuthenticated ? <MainDashboard /> : <Navigate to="/login"/> } />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}
