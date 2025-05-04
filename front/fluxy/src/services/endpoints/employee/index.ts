import { api } from "../../api";

interface EmployeePayload {
    id: number;
    name: string;
    cpf: string;
    salary: number;
    sectorOfActivity: string;
    workShift: string;
    role: string;
    street: string;
    number: string;
    neighborhood: string;
    city: string;
    cep: string;
    phone: string[];
}

export const fetchEmployees = async () => {
    const response = (await api.get("/employees")).data;
    
    const formattedEmployees = response.map((employee: any) => {
        return {
            ...employee,
            address: {street: employee.street, number: employee.number, neighborhood: employee.neighborhood, city: employee.city, cep: employee.cep}
        };
    });
    return formattedEmployees;
};

export const addEmployee = async (data: EmployeePayload) => {

    try {
        const response = await api.post("/employees", data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao adicionar funcionário");
    }
};

export const editEmployee = async (data: EmployeePayload) => {
    try {
        const response = await api.put(`/employees/${data.id}`, data);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao editar funcionário");
    }
};

export const deleteEmployee = async (data: EmployeePayload) => {
    try {
        const response = await api.delete(`/employees/${data}`);
        return response;
    } catch (error: any) {
        
        const errorMessage = error.response?.data;

        if (errorMessage) {
            throw new Error(errorMessage);
        }
        throw new Error("Erro inesperado ao excluir funcionário");
    }
};