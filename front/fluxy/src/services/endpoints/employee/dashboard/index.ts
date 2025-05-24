import { api } from "../../../api";

export const fetchEmployeesCount = async () => {
    try {
        const response = await api.get("/employees/total");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de funcionários");
    }
};

export const fetchEmployeesMostPurchases = async () => {
    try {
        const response = await api.get("/employees/most-purchases");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar funcionários com mais compras");
    }
};

export const fetchEmployeesLeastPurchases = async () => {
    try {
        const response = await api.get("/employees/least-purchases");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar funcionários com menos compras");
    }
};

export const fetchTotalSalaries = async () => {
    try {
        const response = await api.get("/employees/total-salaries");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de salários");
    }
};

export const fetchEmployeeCountByShift = async () => {
    try {
        const response = await api.get("/employees/employee-per-shift");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar funcionários por turno");
    }
};

export const fetchEmployeeCountByRole = async () => {
    try {
        const response = await api.get("/employees/employee-per-role");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar funcionários por cargo");
    }
};