import { api } from "../../api";

export const fetchProductsTotalStock = async () => {

    try {
        const response = await api.get("/products/total-stock");
        return response.data;
    } catch (error: any) {
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao recuperar total de estoque");
    }
};

