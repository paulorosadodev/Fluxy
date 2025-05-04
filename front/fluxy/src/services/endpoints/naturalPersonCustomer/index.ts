import { api } from "../../api";

interface NaturalPersonCustomerPayload {
    id: number;
    name: string,
    cpf: string,
    street: string,
    number: string,
    neighborhood: string,
    city: string,
    cep: string
    phone: string[]
}

export const fetchNaturalPersonCustomers = async () => {
    const response = (await api.get("/physical-clients", )).data;
    const NaturalPersonCustomers = response.map((naturalPersonCustomer: any) => {
        return {
            ...naturalPersonCustomer,
            address: {street: naturalPersonCustomer.street, number: naturalPersonCustomer.number, neighborhood: naturalPersonCustomer.neighborhood, city: naturalPersonCustomer.city, cep: naturalPersonCustomer.cep}
        };
    });
    return NaturalPersonCustomers;
};

export const addNaturalPersonCustomer = async (data: NaturalPersonCustomerPayload) => {

    try {
        const response = await api.post("/physical-clients", data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar cliente");
    }
};

export const editNaturalPersonCustomer = async (data: NaturalPersonCustomerPayload) => {
    try {
        const response = await api.put(`/physical-clients/${data.id}`, data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar cliente");
    }
};

export const deleteNaturalPersonCustomer = async (data: NaturalPersonCustomerPayload) => {
    try {
        const response = await api.delete(`/physical-clients/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir cliente");
    }
};