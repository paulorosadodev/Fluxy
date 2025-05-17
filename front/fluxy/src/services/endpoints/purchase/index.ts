import { cleanCNPJ, cleanCPF, isValidCPF } from "../../../utils";
import { api } from "../../api";

interface PurchasePayload {
    id: number;
    customerId: string;
    employeeId: string;
    installments: number;
    paymentType: string;
    productAmount: number;
    productId: string
}

export const fetchPurchases = async () => {
    const response = await api.get("/purchases", );
    return response.data;
};

export const addPurchase = async (data: PurchasePayload) => {

    const formattedData = {
        ...data,
        productId: data.productId.split("|")[0]?.trim(),
        customerId: isValidCPF(data.customerId.split("|")[1]?.trim()) ? cleanCPF(data.customerId.split("|")[1]?.trim()) : cleanCNPJ(data.customerId.split("|")[1]?.trim()),
        employeeId: data.employeeId.split("|")[1]?.trim(),
    };

    try {
        const response = await api.post("/purchases", formattedData);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar compra");
    }
};

export const editPurchase = async (data: PurchasePayload) => {

    const match = data.productId.match(/\d+/);
    const cleanedProductId = match ? Number(match[0]) : null;

    if (!cleanedProductId) {
        throw new Error("ID do produto inválido");
    }

    const formattedData = {
        ...data,
        productId: cleanedProductId,
    };

    console.log(data);

    try {
        const response = await api.put(`/purchases/${data.id}`, formattedData);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar compra");
    }
};

export const deletePurchase = async (data: PurchasePayload) => {
    try {
        const response = await api.delete(`/purchases/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir compra");
    }
};
