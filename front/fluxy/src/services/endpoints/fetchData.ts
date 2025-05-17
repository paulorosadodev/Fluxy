import { fetchCategories, fetchProducts } from "./product";
import { fetchEmployees } from "./employee";
import { fetchLegalEntityCustomers } from "./legalEntityCustomer";
import { fetchNaturalPersonCustomers } from "./naturalPersonCustomer";
import { fetchSuppliers } from "./supplier";
import { fetchPurchases } from "./purchase";
import { Category, Customer, Employee, Product, ProductSupply, Purchase, Supplier } from "../../@types";

type Data = {
    products: Product[];
    employees: Employee[];
    naturalPersonCustomers: Customer[];
    legalEntityCustomers: Customer[];
    suppliers: Supplier[];
    purchases: Purchase[];
    categories: Category[];
    productSupplies: ProductSupply[];
};


export const fetchData = async () => {
    const data: Data = {
        products: [],
        employees: [],
        naturalPersonCustomers: [],
        legalEntityCustomers: [],
        suppliers: [],
        purchases: [],        
        categories: [],        
        productSupplies: [
            {   
                id: 1,
                supplier: {
                    cnpj: "67015726000179",
                    name: "Estudos LTDA",
                    address: {
                        street: "Rua Faustino Porto",
                        number: "200",
                        neighborhood: "Boa Viagem",
                        city: "Recife",
                        cep: "51020270"
                    },
                    phone: ["81999972730"]
                },
                product: {
                    id: 1,
                    codEa: "7891234567001",
                    name: "Maçã Fuji 1kg",
                    category: { code: "Hortifruti", name: "Hortifruti" },
                    price: 5,
                    stockQuantity: 304
                },
                productAmount: 580,
                price: 250,
                date: "2025-03-15"
            },
            {
                id: 2,
                supplier: {
                    cnpj: "00457891000123",
                    name: "Alimentos Brasil SA",
                    address: {
                        street: "Av. das Mangueiras",
                        number: "1020",
                        neighborhood: "Centro",
                        city: "São Paulo",
                        cep: "01001000"
                    },
                    phone: ["11223344556"]
                },
                product: {
                    id: 4,
                    codEa: "7891234567004",
                    name: "Arroz Integral 1kg",
                    category: { code: "Grãos", name: "Grãos" },
                    price: 22.5,
                    stockQuantity: 120
                },
                productAmount: 300,
                price: 120,
                date: "2025-04-10"
            },
            {
                id: 3,
                supplier: {
                    cnpj: "12894567000199",
                    name: "Frutas Tropicais ME",
                    address: {
                        street: "Rua do Pomar",
                        number: "77",
                        neighborhood: "Jardins",
                        city: "Fortaleza",
                        cep: "60150110"
                    },
                    phone: ["85988776655"]
                },
                product: {
                    id: 5,
                    codEa: "7891234567005",
                    name: "Leite Integral 1L",
                    category: { code: "Laticínios", name: "Laticínios" },
                    price: 8.99,
                    stockQuantity: 75
                },
                productAmount: 400,
                price: 160,
                date: "2025-04-20"
            },
            {
                id: 4,
                supplier: {
                    cnpj: "23654789000111",
                    name: "Verde Vale Agro",
                    address: {
                        street: "Estrada da Colheita",
                        number: "350",
                        neighborhood: "Interior",
                        city: "Campinas",
                        cep: "13060000"
                    },
                    phone: ["1933445566"]
                },
                product: {
                    id: 6,
                    codEa: "7891234567006",
                    name: "Pão Francês 1kg",
                    category: { code: "Padaria", name: "Padaria" },
                    price: 4.79,
                    stockQuantity: 260
                },
                productAmount: 350,
                price: 87.5,
                date: "2025-04-20"
            }
        ]
    };

    data["products"] = (await fetchProducts());
    data["categories"] = (await fetchCategories());
    data["employees"] = (await fetchEmployees());
    data["naturalPersonCustomers"] = (await fetchNaturalPersonCustomers());
    data["legalEntityCustomers"] = (await fetchLegalEntityCustomers());
    data["suppliers"] = (await fetchSuppliers());
    data["purchases"] = (await fetchPurchases()).map((purchase: any) => {
        return {
            ...purchase,
            product: data["products"].find((product) => product.id === purchase.productId),
            employee: data["employees"].find((employee) => employee.id === purchase.employee),
            customer: data["naturalPersonCustomers"].find((customer) => customer.id === purchase.clientId) ?? data["legalEntityCustomers"].find((customer) => customer.id === purchase.clientId),
            paymentMethod: {installments: purchase.installments, type:purchase.type},
            date: `${purchase.date}T${purchase.time}`
        };
    });

    return data;
};