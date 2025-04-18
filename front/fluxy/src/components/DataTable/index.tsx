import { useState } from "react";
import { DataTableWrapper, InputWrapper } from "./styles";
import { SearchFilterInput } from "../SearchFilterInput";

type DataTableProps<T extends Record<string, any>> = {
    data: T[];
    columns: Column<T>[];
    entityName: string
};

export type Column<T> = {
    header: string;
    accessor: keyof T;
    formatter?: (value: any) => string | string[] ; 
};

export function DataTable<T extends Record<string, any>>({ data, columns, entityName }: DataTableProps<T>) {

    const [filteredData, setFilteredData] = useState<any[]>([]);

    return (
        <>
            <InputWrapper>
                <button>Adicionar</button>
                <SearchFilterInput data={data} columns={columns} filteredData={filteredData} setFilteredData={setFilteredData} entityName={entityName}/>
            </InputWrapper>
            <DataTableWrapper>
                <table className="responsive-table">
                    <thead>
                        <tr>
                            {columns.map((column, index) => (
                                <th scope="col" key={index}>{column.header}</th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {filteredData.map((row, rowIndex) => (
                            <tr key={rowIndex}>
                                {columns.map((column, colIndex) => {
                                    const cellValue = row[column.accessor];
                                    const formattedValue = column.formatter
                                        ? column.formatter(cellValue) ?? "?"
                                        : cellValue ?? "?";
                                    return <td data-label={columns[colIndex].header + ":"} key={colIndex}>{formattedValue}</td>;
                                })}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </DataTableWrapper>
        </>
    );
}
