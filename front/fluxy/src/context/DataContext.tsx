import { createContext, Dispatch, ReactNode, SetStateAction, useEffect, useState } from "react";

import { Category, Customer, Employee, Product, ProductSupply, Purchase, Supplier } from "../@types";

import { fetchData } from "../services/endpoints/fetchData";

interface DataContextData {
    data: FetchDataResponse;
    products: Product[];
    suppliers: Supplier[];
    employees: Employee[];
    naturalPersonCustomers: Customer[],
    legalEntityCustomers: Customer[],
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
    naturalPersonCustomers: Customer[],
    legalEntityCustomers: Customer[],
    purchases: Purchase[],
    categories: Category[],
    productSupplies: ProductSupply[],
}

export const DataProvider = ({ children }: DataProviderProps) => {

    const [data, setData] = useState<FetchDataResponse>({
        products: [],
        suppliers: [],
        employees: [],
        naturalPersonCustomers: [],
        legalEntityCustomers: [],
        purchases: [],
        categories: [],
        productSupplies: []
    });

    const [products, setProducts] = useState<Product[]>([]);
    const [suppliers, setSuppliers] = useState<Supplier[]>([]);
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [naturalPersonCustomers, setNaturalPersonCustomers] = useState<Customer[]>([]);
    const [legalEntityCustomers, setLegalEntityCustomers] = useState<Customer[]>([]);
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
    
        const timeoutPromise = new Promise<null>(resolve => 
            setTimeout(() => resolve(null), 3000)
        );
    
        try {
            const result = await Promise.race([
                fetchData(),
                timeoutPromise
            ]) as FetchDataResponse | null;
    
            if (result) {
                setData(result);
                setProducts(result.products);
                setSuppliers(result.suppliers);
                setEmployees(result.employees);
                setNaturalPersonCustomers(result.naturalPersonCustomers);
                setLegalEntityCustomers(result.legalEntityCustomers);
                setPurchases(result.purchases);
                setCategories(result.categories);
                setProductSupplies(result.productSupplies);
            }
        } catch (error) {
            console.error("Erro ao carregar dados:", error);
        }
    
        setIsLoading(false);
    };

    return (
        <DataContext.Provider value={{data, products, suppliers, employees, naturalPersonCustomers, legalEntityCustomers, purchases, categories, productSupplies, setMadeRequest, isLoading}}>
            {children}
        </DataContext.Provider>
    );

};
