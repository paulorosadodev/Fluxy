import { createContext, Dispatch, ReactNode, SetStateAction, useEffect, useState } from "react";

import { Category, Customer, Employee, Product, ProductSupply, Purchase, Supplier } from "../@types";

import { fetchData } from "../services/endpoints/fetchData";

interface DataContextData {
    data: FetchDataResponse;
    products: Product[];
    suppliers: Supplier[];
    employees: Employee[];
    customers: Customer[];
    purchases: Purchase[];
    categories: Category[];
    productSupplies: ProductSupply[];
    setMadeRequest: Dispatch<SetStateAction<boolean>>;
    isLoading: boolean
}

export const DataContext = createContext({} as DataContextData);

interface DataProviderProps {
    children: ReactNode;
}

export interface FetchDataResponse {
    products: Product[],
    suppliers: Supplier[],
    employees: Employee[],
    customers: Customer[],
    purchases: Purchase[],
    categories: Category[],
    productSupplies: ProductSupply[],
}

export const DataProvider = ({ children }: DataProviderProps) => {

    const [data, setData] = useState<FetchDataResponse>({
        products: [],
        suppliers: [],
        employees: [],
        customers: [],
        purchases: [],
        categories: [],
        productSupplies: []
    });

    const [products, setProducts] = useState<Product[]>([]);
    const [suppliers, setSuppliers] = useState<Supplier[]>([]);
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [customers, setCustomers] = useState<Customer[]>([]);
    const [purchases, setPurchases] = useState<Purchase[]>([]);
    const [categories, setCategories] = useState<Category[]>([]);
    const [productSupplies, setProductSupplies] = useState<ProductSupply[]>([]);
    const [madeRequest, setMadeRequest] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        loadData();
    }, [madeRequest]);

    const loadData = async () => {
        setIsLoading(true);

        const result = await fetchData();
        setData(result);
        setProducts(result.products);
        setSuppliers(result.suppliers);
        setEmployees(result.employees);
        setCustomers(result.customers);
        setPurchases(result.purchases);
        setCategories(result.categories);
        setProductSupplies(result.productSupplies);

        setIsLoading(false);
    };

    return (
        <DataContext.Provider value={{data, products, suppliers, employees, customers, purchases, categories, productSupplies, setMadeRequest, isLoading}}>
            {children}
        </DataContext.Provider>
    );

};
