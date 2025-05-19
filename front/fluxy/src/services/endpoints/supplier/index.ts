import { api } from "../../api";

interface SupplierPayload {
    id: number;
    name: string;
    cnpj: string;
    cep: number;
    city: number;
    neighborhood: string;
    street: string;
    number: string;
}

export const fetchSuppliers = async () => {
    const response = (await api.get("/suppliers")).data;

    const formattedSuppliers = response.map((supplier: any) => {
        return {
            ...supplier,
            address: {street: supplier.street, number: supplier.number, neighborhood: supplier.neighborhood, city: supplier.city, cep: supplier.cep}
        };
    });

    return formattedSuppliers;
};

export const addSupplier = async (data: SupplierPayload) => {

    try {
        const response = await api.post("/suppliers", data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar fornecedor");
    }
};

export const editSupplier = async (data: SupplierPayload) => {
    try {
        const response = await api.put(`/suppliers/${data.id}`, data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar fornecedor");
    }
};

export const deleteSupplier = async (data: SupplierPayload) => {
    try {
        const response = await api.delete(`/suppliers/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir fornecedor");
    }
};