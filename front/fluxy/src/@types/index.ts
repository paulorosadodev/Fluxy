export interface Entity {
    id?: number;
}
export interface User extends Entity {
    name: string;
    role: string
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
    role: string
    sectorOfActivity: string
    workShift: string
}

export interface Customer extends Person {
    cnpj?: string;
    legalName?: string;
    stateRegistration?: string
    name?: string;
    cpf?: string
}


export interface Category extends Entity {
    code: string;
    name: string
}

export interface Product extends Entity {
    name: string;
    price: number;
    codEa: string;
    stockQuantity: number;
    category: Category;
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
    employee: Employee;
    productAmount: number;
    total?: number;
    date: Date;
    paymentMethod: PaymentMethod
}

export interface ProductSupply extends Entity {
    supplier: Supplier;
    product: Product;
    productAmount: number;
    price: number;
    date: string
}
