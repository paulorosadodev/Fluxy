export const fetchData = async () => {
    const data = {
        products: [
            { codEa: "001", name: "Maçã", category: "HortiFruti", price: 5, stockQuantity: 304 },
            { codEa: "002", name: "Arroz Tipo 1", category: "Grãos", price: 22.5, stockQuantity: 120 },
            { codEa: "003", name: "Feijão Carioca", category: "Grãos", price: 8.99, stockQuantity: 75 },
            { codEa: "004", name: "Leite Integral", category: "Laticínios", price: 4.79, stockQuantity: 260 },
            { codEa: "005", name: "Queijo Mussarela", category: "Laticínios", price: 34.9, stockQuantity: 58 },
            { codEa: "006", name: "Pão de Forma", category: "Padaria", price: 7.5, stockQuantity: 130 },
            { codEa: "007", name: "Detergente Neutro", category: "Limpeza", price: 2.99, stockQuantity: 180 },
            { codEa: "008", name: "Sabonete", category: "Higiene", price: 3.25, stockQuantity: 200 },
            { codEa: "009", name: "Refrigerante Cola 2L", category: "Bebidas", price: 6.99, stockQuantity: 95 },
            { codEa: "010", name: "Cerveja Pilsen Lata", category: "Bebidas", price: 3.79, stockQuantity: 210 }
        ],
        suppliers: [
            {
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
            {
                cnpj: "00457891000123",
                name: "Alimentos Brasil SA",
                address: {
                    street: "Av. das Mangueiras",
                    number: "1020",
                    neighborhood: "Centro",
                    city: "São Paulo",
                    cep: "01001000"
                },
                phone: ["11923344556"]
            },
            {
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
            {
                cnpj: "23654789000111",
                name: "Verde Vale Agro",
                address: {
                    street: "Estrada da Colheita",
                    number: "350",
                    neighborhood: "Interior",
                    city: "Campinas",
                    cep: "13060000"
                },
                phone: ["19933445566"]
            }
        ],        
        employees: [
            {
                employeeNumber: "001",
                name: "Paulo Rosado",
                cpf: "14383252400",
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
            {
                employeeNumber: "001",
                name: "Paulo Rosado",
                cpf: "14383252400",
                workShift: "08:00 - 18:00",
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
            {
                employeeNumber: "001",
                name: "Paulo Rosado",
                cpf: "14383252400",
                sectorOfActivity: "Financeiro",
                salary: 3040.50,
                address: {
                    street: "Rua Faustino Porto",
                    number: "200",
                    neighborhood: "Boa Viagem",
                    city: "Recife",
                    cep: "51020270"
                },
                phone: ["81999972730"]
            }
        ],
        customers: [
            {
                cpf: "23456789012",
                name: "Carlos Almeida",
                address: {
                    street: "Avenida Central",
                    number: "350",
                    neighborhood: "Centro",
                    city: "São Paulo",
                    cep: "01015001"
                },
                phone: ["11987654321"]
            },
            {
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
                phone: ["11932456789"]
            },
            {
                cpf: "32165498765",
                name: "Maria Santos",
                address: {
                    street: "Rua das Flores",
                    number: "12",
                    neighborhood: "Jardim Paulista",
                    city: "Campinas",
                    cep: "13034060"
                },
                phone: ["19987654321"]
            },
            {
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
                phone: ["21976543210"]
            },
            {
                cpf: "56789012345",
                name: "Joaquim Silva",
                address: {
                    street: "Avenida Brasil",
                    number: "1000",
                    neighborhood: "Campo Grande",
                    city: "Mato Grosso do Sul",
                    cep: "79002001"
                },
                phone: ["67998765432"]
            }
        ],
        purchases: [
            {
                number: 2,
                customer: {
                    cpf: "23456789012",
                    name: "Carlos Almeida",
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
                    codEa: "002",
                    name: "Banana",
                    category: "HortiFruti",
                    price: 3,
                    stockQuantity: 120
                },
                employee: [{
                    employeeNumber: "002",
                    name: "Fernanda Lima",
                    cpf: "12345678900",
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
                }],
                productAmount: 8,
                date: new Date(),
                paymentMethod: {
                    type: "Cash",
                    installments: 1
                }
            },
            {
                number: 3,
                customer: {
                    cpf: "32165498765",
                    name: "Maria Santos",
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
                    category: "Mercearia",
                    price: 25,
                    stockQuantity: 200
                },
                employee: [{
                    employeeNumber: "003",
                    name: "Lucas Pereira",
                    cpf: "98765432100",
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
                }],
                productAmount: 2,
                date: new Date(),
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
                    category: "Eletrônicos",
                    price: 3500,
                    stockQuantity: 15
                },
                employee: [{
                    employeeNumber: "004",
                    name: "Juliana Rocha",
                    cpf: "65432198700",
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
                }],
                productAmount: 1,
                date: new Date(),
                paymentMethod: {
                    type: "Credit Card",
                    installments: 6
                }
            },
            {
                number: 5,
                customer: {
                    cpf: "56789012345",
                    name: "Joaquim Silva",
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
                    category: "Limpeza",
                    price: 2.5,
                    stockQuantity: 500
                },
                employee: [{
                    employeeNumber: "005",
                    name: "Carla Mendes",
                    cpf: "32178965400",
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
                }],
                productAmount: 20,
                date: new Date(),
                paymentMethod: {
                    type: "Pix",
                    installments: 1
                }
            },
            {
                number: 6,
                customer: {
                    cpf: "14383252400",
                    name: "Paulo Rosado",
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
                    category: "Higiene",
                    price: 1.8,
                    stockQuantity: 300
                },
                employee: [{
                    employeeNumber: "001",
                    name: "Paulo Rosado",
                    cpf: "14383252400",
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
                }],
                productAmount: 15,
                date: new Date(),
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
                    category: "Alimentos",
                    price: 8,
                    stockQuantity: 100
                },
                employee: [{
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
                }],
                productAmount: 10,
                date: new Date(),
                paymentMethod: {
                    type: "Credit Card",
                    installments: 2
                }
            },
            {
                number: 8,
                customer: {
                    cpf: "11223344556",
                    name: "Ana Beatriz",
                    address: {
                        street: "Rua Nova Esperança",
                        number: "40",
                        neighborhood: "Santa Cruz",
                        city: "Salvador",
                        cep: "40100123"
                    },
                    phone: ["71998877665"]
                },
                product: {
                    codEa: "008",
                    name: "Papel Higiênico",
                    category: "Higiene",
                    price: 12,
                    stockQuantity: 150
                },
                employee: [{
                    employeeNumber: "007",
                    name: "Mariana Freitas",
                    cpf: "33445566778",
                    role: "Supervisora",
                    salary: 4500,
                    address: {
                        street: "Rua dos Girassóis",
                        number: "77",
                        neighborhood: "Pituba",
                        city: "Salvador",
                        cep: "40100234"
                    },
                    phone: ["71999887744"]
                }],
                productAmount: 5,
                date: new Date(),
                paymentMethod: {
                    type: "Pix",
                    installments: 1
                }
            },
            {
                number: 9,
                customer: {
                    cpf: "99887766554",
                    name: "Felipe Carvalho",
                    address: {
                        street: "Rua do Sol",
                        number: "101",
                        neighborhood: "Centro",
                        city: "Fortaleza",
                        cep: "60010001"
                    },
                    phone: ["85991234567"]
                },
                product: {
                    codEa: "009",
                    name: "Leite Integral",
                    category: "Alimentos",
                    price: 6.5,
                    stockQuantity: 180
                },
                employee: [{
                    employeeNumber: "008",
                    name: "Eduardo Silva",
                    cpf: "77665544332",
                    role: "Repositor",
                    salary: 2300,
                    address: {
                        street: "Rua das Marés",
                        number: "21",
                        neighborhood: "Meireles",
                        city: "Fortaleza",
                        cep: "60060123"
                    },
                    phone: ["85998877666"]
                }],
                productAmount: 6,
                date: new Date(),
                paymentMethod: {
                    type: "Debit Card",
                    installments: 1
                }
            },
            {
                number: 10,
                customer: {
                    cpf: "55667788990",
                    name: "Clara Mendes",
                    address: {
                        street: "Rua Azul",
                        number: "89",
                        neighborhood: "Boa Vista",
                        city: "Belo Horizonte",
                        cep: "30140071"
                    },
                    phone: ["31992345678"]
                },
                product: {
                    codEa: "010",
                    name: "Biscoito Recheado",
                    category: "Alimentos",
                    price: 4.2,
                    stockQuantity: 220
                },
                employee: [{
                    employeeNumber: "009",
                    name: "Henrique Dias",
                    cpf: "22334455667",
                    role: "Caixa",
                    salary: 2700,
                    address: {
                        street: "Rua Estrela",
                        number: "70",
                        neighborhood: "Savassi",
                        city: "Belo Horizonte",
                        cep: "30140072"
                    },
                    phone: ["31991234567"]
                }],
                productAmount: 12,
                date: new Date(),
                paymentMethod: {
                    type: "Credit Card",
                    installments: 3
                }
            }
        ],        
        categories: [
            { 
                code: "01",
                name: "HortiFruti",
                description: "Frutas e verduras" 
            }
        ],
        productSupplies: [
            {
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
                    codEa: "001",
                    name: "Maçã",
                    category: "HortiFruti",
                    price: 5,
                    stockQuantity: 304
                },
                productAmount: 580,
                price: 250,
                date: new Date()
            },
            {
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
                    codEa: "002",
                    name: "Banana",
                    category: "HortiFruti",
                    price: 3,
                    stockQuantity: 450
                },
                productAmount: 300,
                price: 120,
                date: new Date()
            },
            {
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
                    codEa: "003",
                    name: "Manga",
                    category: "HortiFruti",
                    price: 4,
                    stockQuantity: 500
                },
                productAmount: 400,
                price: 160,
                date: new Date()
            },
            {
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
                    codEa: "004",
                    name: "Cenoura",
                    category: "HortiFruti",
                    price: 2.5,
                    stockQuantity: 600
                },
                productAmount: 350,
                price: 87.5,
                date: new Date()
            }
        ]        
    };

    return data;
};
