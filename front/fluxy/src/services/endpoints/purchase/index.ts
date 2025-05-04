import { api } from "../../api";

interface ProductPayload {
    id: number;
    name: string;
    codEa: string;
    price: number;
    stockQuantity: number;
    categoryCode: string;
}

export const fetchPurchase = async () => {
    const response = await api.get("/products", );
    return response.data;
};

export const addPurchase = async (data: ProductPayload) => {

    try {
        const response = await api.post("/products", data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar produto");
    }
};

export const editPurchase = async (data: ProductPayload) => {
    try {
        const response = await api.put(`/products/${data.id}`, data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar produto");
    }
};

export const deletePurchase = async (data: ProductPayload) => {
    try {
        const response = await api.delete(`/products/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir produto");
    }
};