import { fetchCategories, fetchProducts } from "./product";
import { fetchEmployees } from "./employee";
import { fetchLegalEntityCustomers } from "./legalEntityCustomer";
import { fetchNaturalPersonCustomers } from "./naturalPersonCustomer";
import { fetchSuppliers } from "./supplier";
import { fetchPurchases } from "./purchase";
import { Category, Customer, Employee, Product, ProductSupply, Purchase, Supplier } from "../../@types";
import { fetchSupplies } from "./supply";

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
        productSupplies: []
    };

    data["products"] = (await fetchProducts());
    data["categories"] = (await fetchCategories());
    data["employees"] = (await fetchEmployees());
    data["naturalPersonCustomers"] = (await fetchNaturalPersonCustomers());
    data["legalEntityCustomers"] = (await fetchLegalEntityCustomers());
    data["suppliers"] = (await fetchSuppliers());
    data["productSupplies"] = (await fetchSupplies()).map((supply: any) => {
        return {
            ...supply,
            product: data["products"].find((product) => product.id == supply.product),
            supplier: data["suppliers"].find((supplier) => supplier.id == supply.supplier)
        };
    });
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