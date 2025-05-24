import { api } from "../../../api";

interface LegalEntityCustomerPayload {
    id: number; 
    legalName: string;
    cnpj: string;
    stateRegistration: string;
    street: string;
    number: string;
    neighborhood: string;
    city: string;
    cep: string;
    phone: string[];
}

export const fetchLegalEntityCustomers = async () => {
    const response = (await api.get("/legal-clients")).data;
    const LegalEntityCustomers = response.map((legalEntityCustomer: any) => {
        return {
            ...legalEntityCustomer,
            address: {street: legalEntityCustomer.street, number: legalEntityCustomer.number, neighborhood: legalEntityCustomer.neighborhood, city: legalEntityCustomer.city, cep: legalEntityCustomer.cep}
        };
    });
    return LegalEntityCustomers;
};

export const addLegalEntityCustomer = async (data: LegalEntityCustomerPayload) => {

    try {
        const response = await api.post("/legal-clients", data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar cliente");
    }
};

export const editLegalEntityCustomer = async (data: LegalEntityCustomerPayload) => {
    try {
        const response = await api.put(`/legal-clients/${data.id}`, data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar cliente");
    }
};

export const deleteLegalEntityCustomer = async (data: LegalEntityCustomerPayload) => {
    try {
        const response = await api.delete(`/legal-clients/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir cliente");
    }
};
