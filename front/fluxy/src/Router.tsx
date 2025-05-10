import { Routes, Route, BrowserRouter, Navigate } from "react-router-dom";

import { useAuth } from "./hooks/useAuth";

import { DefaultLayout } from "./layouts/DefaultLayout";

import { Home } from "./pages/Home";
import { Login } from "./pages/Login";

import MainDashboard from "./pages/Dashboards/Main";
import ProductsDashboard from "./pages/Dashboards/Products";
import SuppliersDashboard from "./pages/Dashboards/Suppliers";
import EmployeesDashboard from "./pages/Dashboards/Employees";
import CustomersDashboard from "./pages/Dashboards/Customers";
import PurchasesDashboard from "./pages/Dashboards/Purchases";
import Loading from "./components/Loading";

export function Router() {
    const { isAuthenticated, isLoading } = useAuth();

    if (isLoading) {
        return <Loading />; 
    }

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />

                <Route element={<DefaultLayout />}>
                    <Route path="/dashboard/inicio" 
                        element={ isAuthenticated ? <MainDashboard /> : <Navigate to="/login"/> } />
                    <Route path="/dashboard/produtos" 
                        element={ isAuthenticated ? <ProductsDashboard /> : <Navigate to="/login"/> } />
                    <Route path="/dashboard/fornecedores" 
                        element={ isAuthenticated ? <SuppliersDashboard /> : <Navigate to="/login"/> } />
                    <Route path="/dashboard/funcionarios" 
                        element={ isAuthenticated ? <EmployeesDashboard /> : <Navigate to="/login"/> } />
                    <Route path="/dashboard/clientes" 
                        element={ isAuthenticated ? <CustomersDashboard /> : <Navigate to="/login"/> } />
                    <Route path="/dashboard/compras" 
                        element={ isAuthenticated ? <PurchasesDashboard /> : <Navigate to="/login"/> } />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}
