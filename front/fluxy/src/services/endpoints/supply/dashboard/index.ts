import { api } from "../../../api";

export const fetchTotalDeliveries = async () => {
    try {
        const response = await api.get("/supply/total");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de entregas");
    }
};

export const fetchTotalCost = async () => {
    try {
        const response = await api.get("/supply/total-cost");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar valor total de entregas");
    }
};
export const fetchTotalAverageCost = async () => {
    try {
        const response = await api.get("/supply/total-average-cost");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar valor médio de entrega");
    }
};

export const fetchDeliveriesByMonth = async (month: number, year: number) => {
    try {
        const response = await api.post("/supply/monthly-total", {
            month: month,
            year: year
        });
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar entregas por mês");
    }
};

export const fetchDeliveryMonthAverageCost = async (month: number, year: number) => {
    try {
        const response = await api.post("/supply/monthly-average-cost", {
            month: month,
            year: year
        });
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar média de valor de entrega por mês");
    }
};

export const fetchTotalDeliveryCostByMonth = async (month: number, year: number) => {
    try {
        const response = await api.post("/supply/monthly-total-cost", {
            month: month,
            year: year
        });
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar custo total das entregas por mês");
    }
};

export const fetchMostExpensiveDeliveries = async () => {
    try {
        const response = await api.get("/supply/mais-caras");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar entregas mais caras");
    }
};

export const fetchLeastExpensiveDeliveries = async () => {
    try {
        const response = await api.get("/supply/mais-barata");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar entregas mais baratas");
    }
};