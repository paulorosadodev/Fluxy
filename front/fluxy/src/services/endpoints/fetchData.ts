import { fetchCategories, fetchProducts } from "./product";
import { fetchEmployees } from "./employee";
import { fetchLegalEntityCustomers } from "./legalEntityCustomer";
import { fetchNaturalPersonCustomers } from "./naturalPersonCustomer";
import { fetchSuppliers } from "./supplier";

export const fetchData = async () => {
    const data = {
        products: [],
        employees: [],
        naturalPersonCustomers: [],
        legalEntityCustomers: [],
        suppliers: [],
        purchases: [
            {
                number: 2,
                customer: {
                    cpf: "75248582059",
                    name: "Carlos Souza",
                    address: {
                        street: "Avenida Central",
                        number: "350",
                        neighborhood: "Centro",
                        city: "São Paulo",
                        cep: "01015001"
                    },
                    phone: ["11987654321"]
                },
                product: {
                    codEa: "7891234567001",
                    name: "Maçã Fuji 1kg",
                    category: { code: "Hortifruti", name: "Hortifruti" },
                    price: 3.99,
                    stockQuantity: 100
                },
                employee: {
                    employeeNumber: "EMP000002",
                    name: "Maria Silva",
                    cpf: "39825433043",
                    role: "Atendente",
                    salary: 2500,
                    address: {
                        street: "Rua do Comércio",
                        number: "85",
                        neighborhood: "Centro",
                        city: "São Paulo",
                        cep: "01015002"
                    },
                    phone: ["11987654322"]
                },
                productAmount: 8,
                date: "2025-03-15T14:30:00",
                paymentMethod: {
                    type: "Crédito",
                    installments: 2
                }
            },
            {
                number: 3,
                customer: {
                    cpf: "50224698044",
                    name: "Maria Oliveira",
                    address: {
                        street: "Rua das Flores",
                        number: "12",
                        neighborhood: "Jardim Paulista",
                        city: "Campinas",
                        cep: "13034060"
                    },
                    phone: ["19987654321"]
                },
                product: {
                    codEa: "003",
                    name: "Arroz",
                    category: { code: "Mercearia", name: "Mercearia" },
                    price: 25,
                    stockQuantity: 200
                },
                employee: {
                    employeeNumber: "EMP000003",
                    name: "Carlos Souza",
                    cpf: "96152518016",
                    role: "Repositor",
                    salary: 2200,
                    address: {
                        street: "Av. das Palmeiras",
                        number: "60",
                        neighborhood: "Centro",
                        city: "Campinas",
                        cep: "13034061"
                    },
                    phone: ["19988884444"]
                },
                productAmount: 2,
                date: "2025-03-15T14:30:00",
                paymentMethod: {
                    type: "Debit Card",
                    installments: 1
                }
            },
            {
                number: 4,
                customer: {
                    cnpj: "12345678000145",
                    legalName: "Tech Solutions Ltda",
                    stateRegistration: "451234567",
                    address: {
                        street: "Rua dos Trabalhadores",
                        number: "245",
                        neighborhood: "Zona Norte",
                        city: "São Paulo",
                        cep: "02152020"
                    },
                    phone: ["1132456789"]
                },
                product: {
                    codEa: "004",
                    name: "Notebook",
                    category: { code: "Eletrônicos", name: "Eletrônicos" },
                    price: 3500,
                    stockQuantity: 15
                },
                employee: {
                    employeeNumber: "EMP000004",
                    name: "Ana Paula",
                    cpf: "71215577036",
                    role: "Vendedora",
                    salary: 3200,
                    address: {
                        street: "Rua das Acácias",
                        number: "18",
                        neighborhood: "Pinheiros",
                        city: "São Paulo",
                        cep: "05422020"
                    },
                    phone: ["11998761234"]
                },
                productAmount: 1,
                date: "2025-03-15T14:30:00",
                paymentMethod: {
                    type: "Credit Card",
                    installments: 6
                }
            },
            {
                number: 5,
                customer: {
                    cpf: "38687054068",
                    name: "João da Silva",
                    address: {
                        street: "Avenida Brasil",
                        number: "1000",
                        neighborhood: "Campo Grande",
                        city: "Mato Grosso do Sul",
                        cep: "79002001"
                    },
                    phone: ["6798765432"]
                },
                product: {
                    codEa: "005",
                    name: "Detergente",
                    category: { code: "Limpeza", name: "Limpeza" },
                    price: 2.5,
                    stockQuantity: 500
                },
                employee: {
                    employeeNumber: "EMP000005",
                    name: "Lucas Mendes",
                    cpf: "14780616000",
                    role: "Caixa",
                    salary: 2400,
                    address: {
                        street: "Rua Azul",
                        number: "50",
                        neighborhood: "Vila Nova",
                        city: "Campo Grande",
                        cep: "79002002"
                    },
                    phone: ["67999887766"]
                },
                productAmount: 20,
                date: "2025-03-15T14:30:00",
                paymentMethod: {
                    type: "Pix",
                    installments: 1
                }
            },
            {
                number: 6,
                customer: {
                    cpf: "75248582059",
                    name: "Carlos Souza",
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
                    codEa: "006",
                    name: "Sabonete",
                    category: { code: "Higiene", name: "Higiene" },
                    price: 1.8,
                    stockQuantity: 300
                },
                employee: {
                    employeeNumber: "EMP000005",
                    name: "Lucas Mendes",
                    cpf: "14780616000",
                    role: "Caixa",
                    salary: 3040.50,
                    address: {
                        street: "Rua Faustino Porto",
                        number: "200",
                        neighborhood: "Boa Viagem",
                        city: "Recife",
                        cep: "51020270"
                    },
                    phone: ["81999972730"]
                },
                productAmount: 15,
                date: "2025-03-15T14:30:00",
                paymentMethod: {
                    type: "Cash",
                    installments: 1
                }
            },
            {
                number: 7,
                customer: {
                    cnpj: "98765432000134",
                    legalName: "Comércio & Cia Ltda",
                    stateRegistration: "067823910",
                    address: {
                        street: "Rua Pedro II",
                        number: "128",
                        neighborhood: "Vila Progresso",
                        city: "Rio de Janeiro",
                        cep: "21010010"
                    },
                    phone: ["2176543210"]
                },
                product: {
                    codEa: "007",
                    name: "Café em pó",
                    category: { code: "Alimentos", name: "Alimentos" },
                    price: 8,
                    stockQuantity: 100
                },
                employee: {
                    employeeNumber: "006",
                    name: "Roberto Souza",
                    cpf: "99887766554",
                    role: "Atendente",
                    salary: 2600,
                    address: {
                        street: "Av. Atlântica",
                        number: "500",
                        neighborhood: "Copacabana",
                        city: "Rio de Janeiro",
                        cep: "22021001"
                    },
                    phone: ["21999988776"]
                },
                productAmount: 10,
                date: "2025-03-15T14:30:00",
                paymentMethod: {
                    type: "Credit Card",
                    installments: 2
                }
            }
        ],        
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

    return data;
};
