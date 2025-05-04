import { useEffect, useState } from "react";
import { DirectionButton, InputWrapper, SearchInput, SortInput, SortSelect } from "./styles";
import { ArrowDown, ArrowUp } from "phosphor-react";
import { Column } from "../DataTable";

import { formatDate, formatPaymentMethod } from "../../utils";
import { Customer, Employee, PaymentMethod, Product, Supplier } from "../../@types";

type SearchFilterInput<T extends Record<string, any>> = {
    data: T[];
    filteredData: T[];
    columns: Column<T>[];
    setFilteredData: React.Dispatch<React.SetStateAction<T[]>>;
    entityName: string;
};

export function SearchFilterInput<T extends Record<string, any>>({data, columns, setFilteredData, entityName}: SearchFilterInput<T>) {
    
    const excludedHeaders = ["CPF", "CNPJ", "Telefone", "Endereço", "Inscrição Estadual"];

    const adjustedColumns = columns.filter(column => !excludedHeaders.includes(column.header));

    const [searchTerm, setSearchTerm] = useState("");
    const [sortField, setSortField] = useState(adjustedColumns[0].accessor);
    const [sortDirection, setSortDirection] = useState<"asc" | "desc">("asc");
    
    useEffect(() => {
        const filteredDataTemp = data
            .filter((item) => {
                return columns.some((column) => {
                    const value = item[column.accessor];

                    if (typeof value === "object" && value !== null) {
                        if ((value as Date) instanceof Date) {
                            return formatDate(value).includes(searchTerm.toLowerCase());
                        }
                        return Object.values(value).some(val => {
                            return typeof val === "string" && val.toLowerCase().includes(searchTerm.toLowerCase());
                        }
                        );
                    }

                    const stringValue = String(item[column.accessor]);

                    return typeof stringValue === "string" && stringValue.toLowerCase().includes(searchTerm.toLowerCase());
                });
            })
            .sort((a, b) => {
                const sortColumn = columns.find(col => col.accessor === sortField);

                if (!sortColumn) return 0;
    
                const aValue = a[sortColumn.accessor];
                const bValue = b[sortColumn.accessor];

                return sortFunction(aValue, bValue, sortDirection);
    
            });
    
        setFilteredData(filteredDataTemp);
    }, [data, sortField, sortDirection, searchTerm]);
    
        
    return (
        <InputWrapper>
            <SearchInput type="text" placeholder={`Pesquisar ${entityName}...`} value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)}/>

            <SortInput>
                <SortSelect value={String(sortField)} onChange={(e) => setSortField(e.target.value)}>
                    {adjustedColumns.map((column, index) => (
                        <option key={index} value={String(column.accessor)}>{column.header}</option>
                    ))}
                </SortSelect>

                <DirectionButton onClick={() =>
                    setSortDirection(prev => prev === "asc" ? "desc" : "asc")
                }>
                    {sortDirection === "asc" ? <ArrowUp size={20} /> : <ArrowDown size={20} />}
                </DirectionButton>
            </SortInput>
        </InputWrapper>
    );

}

function sortFunction(aValue: any, bValue: any, sortDirection: string) {
    if (typeof aValue === "number" && typeof bValue === "number") {
        return sortDirection === "asc" ? aValue - bValue : bValue - aValue;
    }

    if (typeof aValue === "object" && typeof bValue === "object" && aValue != null && bValue != null) {

        if ((aValue as Date) instanceof Date && (bValue as Date) instanceof Date) {
            return sortDirection === "asc"
                ? aValue.getTime() - bValue.getTime()
                : bValue.getTime() - aValue.getTime();
        }
        
        if ("name" in aValue && "name" in bValue && "cnpj" in aValue && "cnpj" in bValue) {
            const supplierA = aValue as Supplier;
            const supplierB = bValue as Supplier;
            
            return sortDirection === "asc"
                ? supplierA.name.localeCompare(supplierB.name)
                : supplierB.name.localeCompare(supplierA.name);
        }
        
        if ("codEa" in aValue && "codEa" in bValue) {
            const productA = aValue as Product;
            const productB = bValue as Product;
        
            return sortDirection === "asc"
                ? productA.name.localeCompare(productB.name)
                : productB.name.localeCompare(productA.name);
        }
        
        if ("installments" in aValue && "installments" in bValue) {
            const paymentMethodA = aValue as PaymentMethod;
            const paymentMethodB = bValue as PaymentMethod;
        
            return sortDirection === "asc"
                ? (formatPaymentMethod(paymentMethodA)).localeCompare(formatPaymentMethod(paymentMethodB))
                : (formatPaymentMethod(paymentMethodB)).localeCompare(formatPaymentMethod(paymentMethodA));
        }

        if (!("salary" in aValue) && !("salary" in bValue)) {
            const customerA = aValue as Customer;
            const customerB = bValue as Customer;
            
            return sortDirection === "asc"
                ? (customerA.name || customerA.legalName || "").localeCompare(customerB.name || customerB.legalName || "")
                : (customerB.name || customerB.legalName || "").localeCompare(customerA.name || customerA.legalName || "");
        }
        
        if ("salary" in aValue && "salary" in bValue) {
            const employeeA = aValue as Employee;
            const employeeB = bValue as Employee;
            
            return sortDirection === "asc"
                ? (employeeA.name).localeCompare(employeeB.name)
                : (employeeB.name).localeCompare(employeeA.name);
        }

    }

    if (typeof aValue === "string" && typeof bValue === "string") {
        return sortDirection === "asc"
            ? aValue.localeCompare(bValue)
            : bValue.localeCompare(aValue);
    }

    return 0;
}
