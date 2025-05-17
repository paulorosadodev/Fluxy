import { cleanCNPJ } from "../../../utils";
import { api } from "../../api";

interface SupplyPayload {
    id: number;
    supplier: string;
    product: string;
    productAmount: number;
    price: number;
}

export const fetchSupplies = async () => {
    const response = await api.get("/supply");
    return response.data;
};

export const addSupply = async (data: SupplyPayload) => {

    const formattedData = {
        ...data,
        product: data.product.split("|")[0]?.trim(),
        supplier: cleanCNPJ(data.supplier.split("|")[1]?.trim()),
    };
    
    try {
        const response = await api.post("/supply", formattedData);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar entrega");
    }
};

export const editSupply = async (data: SupplyPayload) => {
    try {
        const response = await api.put(`/supply/${data.id}`, data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar entrega");
    }
};

export const deleteSupply = async (data: SupplyPayload) => {
    try {
        const response = await api.delete(`/supply/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir entrega");
    }
};