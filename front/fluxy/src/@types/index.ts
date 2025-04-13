export interface Entity {
    id?: number;
}
export interface User extends Entity {
    name: string
};

export interface Address {
    street: string;
    number: string;
    neighborhood: string;
    city: string;
    cep: string;
}

export interface Person extends Entity {
    address: Address;
    phone: string[]
}
export interface Supplier extends Person {
    cnpj: string;
    name: string
}

export interface Employee extends Person {
    employeeNumber: string;
    cpf: string;
    name: string;
    salary: number
}

export interface ManagerEmployee  extends Employee {
    sectorOfActivity: string
}

export interface AssistantManagerEmployee  extends Employee {
    workShift: string
}

export interface OperationsEmployee extends Employee {
    role: string
}

export interface Customer extends Person {}

export interface LegalEntityCustomer extends Customer {
    cnpj: string;
    legalName: string;
    stateRegistration: string
}

export interface NaturalPersonCustomer extends Customer {
    name: string;
    cpf: string
}

export interface Category extends Entity {
    code: string;
    description: string;
    name: string
}

export interface Product extends Entity {
    name: string;
    description?: string;
    price: number;
    codEa: string;
    stockQuantity: number;
    category: string;
}

export interface ProductPriceHistory extends Entity {
    date: Date;
    price: number
}

export interface PaymentMethod extends Entity {
    type: string;
    installments: number
}

export interface Purchase extends Entity {
    number: number;
    customer: Customer;
    product: Product;
    employee: OperationsEmployee[]
    productAmount: number;
    date: Date;
    paymentMethod: PaymentMethod
}

export interface ProductSupply extends Entity {
    supplier: Supplier;
    product: Product;
    productAmount: number;
    price: number;
    date: Date
}
