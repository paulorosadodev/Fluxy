import { api } from "../../../api";

export const fetchTotalPurchases = async () => {
    try {
        const response = await api.get("/purchases/total");
        return response.data.totalPurchases;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de compras");
    }
};

export const fetchTotalPurchaseCostByMonth = async (month: number, year: number) => {
    try {
        const response = await api.post("/purchases/monthly-purchase-costs", {
            month: month,
            year: year
        });

        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar custo total das compras por mês");
    }
};

export const fetchTotalPurchasesByMonth = async (month: number, year: number) => {
    try {
        const response = await api.post("/purchases/by-month-year", {
            month: month,
            year: year
        });

        return response.data.count;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de compras por mês");
    }
};

export const fetchPaymentTypeCounts = async () => {
    try {
        const response = await api.get("/purchases/payment-type-count");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar contagem por tipo de pagamento");
    }
};

export const fetchMostExpensivePurchases = async () => {
    try {
        const response = await api.get("/purchases/most-expensive-purchases");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar compras mais caras");
    }
};

export const fetchLeastExpensivePurchases = async () => {
    try {
        const response = await api.get("/purchases/least-expensive-purchases");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar compras mais baratas");
    }
};

export const fetchAveragePurchaseCostByMonth = async (month: number, year: number) => {
    try {
        const response = await api.post("/purchases/monthly-average-costs", {
            month: month,
            year: year
        });

        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar média de custos por mês");
    }
};

export const fetchTotalPurchaseCosts = async () => {
    try {
        const response = await api.get("/purchases/total-costs");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar custo total das compras");
    }
};

export const fetchAveragePurchaseCost = async () => {
    try {
        const response = await api.get("/purchases/average-costs");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar custo médio das compras");
    }
};