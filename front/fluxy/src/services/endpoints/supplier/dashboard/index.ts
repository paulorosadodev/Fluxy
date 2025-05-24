import { api } from "../../../api";

export const fetchSuppliersCount = async () => {
    try {
        const response = await api.get("/suppliers/total");
        return response.data.total;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de fornecedores");
    }
};

export const fetchSuppliersMostDeliveries = async () => {
    try {
        const response = await api.get("/suppliers/most-deliveries");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar fornecedores com mais entregas");
    }
};

export const fetchSuppliersLeastDeliveries = async () => {
    try {
        const response = await api.get("/suppliers/least-deliveries");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar fornecedores com menos entregas");
    }
};