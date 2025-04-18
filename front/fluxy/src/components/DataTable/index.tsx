import { useEffect, useState } from "react";
import { DataTableWrapper, InputWrapper, ActionsWrapper } from "./styles";
import { SearchFilterInput } from "../SearchFilterInput";

import { Square, CheckSquare, Trash, Pencil } from "phosphor-react";

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
    const [selectedsRow, setSelectedsRow] = useState<string[]>([]);

    useEffect(() => {
        if (selectedsRow) {
            const filteredRowStrings = filteredData.map(row => Object.values(row).toString());
    
            const updatedSelecteds = selectedsRow.filter(rowStr =>
                filteredRowStrings.includes(rowStr)
            );
    
            if (updatedSelecteds.length !== selectedsRow.length) {
                setSelectedsRow(updatedSelecteds.length > 0 ? updatedSelecteds : []);
            }
        }
    }, [selectedsRow, filteredData]);

    function handleSelect(row: string | undefined) {
        if (!row) return;
    
        setSelectedsRow(prev => {
            const current = prev ?? [];
    
            if (current.includes(row)) {
                return current.filter(item => item !== row);
            } else {
                return [...current, row];
            }
        });
    }    

    return (
        <>
            <InputWrapper>
                <ActionsWrapper>
                    <button>Adicionar</button>
                    <Pencil id="edit" className={selectedsRow.length === 1 ? "active" : ""} size={25} />
                    <Trash id="delete" className={selectedsRow.length >= 1 ? "active" : ""} size={25} />
                </ActionsWrapper>
                <SearchFilterInput data={data} columns={columns} filteredData={filteredData} setFilteredData={setFilteredData} entityName={entityName}/>
            </InputWrapper>
            <DataTableWrapper>
                <table className="responsive-table">
                    <thead>
                        <tr>
                            <th></th>
                            {columns.map((column, index) => (
                                <th scope="col" key={index}>{column.header}</th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {filteredData.map((row) => (
                            <tr key={Object.values(row).toString()} onClick={() => handleSelect(Object.values(row).toString())}>
                                <td className={`selector ${selectedsRow?.includes(Object.values(row).toString()) ? "selected" : ""}`}>
                                    <ActionsWrapper className="mobile inactive">
                                        <Pencil id="edit" className={selectedsRow ? "active" : ""} size={25} />
                                        <Trash id="delete" className={selectedsRow ? "active" : ""} size={25} />
                                    </ActionsWrapper>
                                    {selectedsRow?.includes(Object.values(row).toString()) ?
                                        <CheckSquare className="square" weight="bold" width={16} height={18}/>
                                        :
                                        <Square className="square" weight="bold" width={16} height={18}/>
                                    }
                                </td>
                                {columns.map((column, colIndex) => {
                                    const cellValue = row[column.accessor];
                                    const formattedValue = column.formatter
                                        ? column.formatter(cellValue) ?? "?"
                                        : cellValue ?? "?";
                                    return <td className={selectedsRow?.includes(Object.values(row).toString()) ? "selected" : ""}  data-label={columns[colIndex].header + ":"} key={colIndex}>{formattedValue}</td>;
                                })}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </DataTableWrapper>
        </>
    );
}
