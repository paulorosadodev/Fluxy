import { api } from "../../../api";

export const fetchTotalClients = async () => {
    try {
        const response = await api.get("/clientes/total-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de clientes");
    }
};

export const fetchTotalClientsByCity = async () => {
    try {
        const response = await api.get("/clientes/total-clients-by-city");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de clientes por cidade");
    }
};

export const fetchTopTierClients = async () => {
    try {
        const response = await api.get("/clientes/top-tier-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar clientes de nível superior por compras");
    }
};

export const fetchLowTierClients = async () => {
    try {
        const response = await api.get("/clientes/low-tier-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar clientes de nível inferior por compras");
    }
};

export const fetchTopTierSpendibleClients = async () => {
    try {
        const response = await api.get("/clientes/top-tier-spendible-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar clientes de nível superior por gastos");
    }
};

export const fetchLowTierSpendibleClients = async () => {
    try {
        const response = await api.get("/clientes/low-tier-spendible-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar clientes de nível inferior por gastos");
    }
};

export const fetchTotalPhysicalClients = async () => {
    try {
        const response = await api.get("/clientes/total-physical-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de clientes físicos");
    }
};

export const fetchTotalJuridicalClients = async () => {
    try {
        const response = await api.get("/clientes/total-juridical-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de clientes jurídicos");
    }
};

export const fetchActiveClients = async () => {
    try {
        const response = await api.get("/clientes/active-clients");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar clientes mais ativos");
    }
};