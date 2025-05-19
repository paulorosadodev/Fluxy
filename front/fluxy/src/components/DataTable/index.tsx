import { useEffect, useState } from "react";
import { DataTableWrapper, InputWrapper, ActionsWrapper } from "./styles";
import { SearchFilterInput } from "../SearchFilterInput";

import { Square, CheckSquare, Trash, Pencil } from "phosphor-react";

import { AxiosResponse } from "axios";
import { useData } from "../../hooks/useData";
import Loading from "../Loading";

export type FormControllers = {
    add: React.Dispatch<React.SetStateAction<boolean>>;
    edit: React.Dispatch<React.SetStateAction<boolean>>
};

type DataTableProps<T extends Record<string, any>> = {
    data: T[];
    columns: Column<T>[];
    entityName: string;
    formControllers: FormControllers;
    popUpController: React.Dispatch<React.SetStateAction<boolean>>;
    deletePopUpController: React.Dispatch<React.SetStateAction<boolean>>;
    setDeletePopUpType: React.Dispatch<React.SetStateAction<"success" | "error">>;
    setDeletePopUpMessage: React.Dispatch<React.SetStateAction<string>>;
    selectedRowController: React.Dispatch<React.SetStateAction<string>>;
    deleteRow: (data: any) => Promise<AxiosResponse<any>>
};

export type Column<T> = {
    header: string;
    accessor: keyof T;
    formatter?: (value: any) => string | string[];
};

export function DataTable<T extends Record<string, any>>({ data, columns, entityName, formControllers, selectedRowController, popUpController, deletePopUpController, setDeletePopUpMessage, deleteRow, setDeletePopUpType }: DataTableProps<T>) {

    const [filteredData, setFilteredData] = useState<any[]>([]);
    const [selectedsRow, setSelectedsRow] = useState<string[]>([]);

    const {setMadeRequest, isLoading} = useData();

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

    const handleClick = () => {
        setSelectedsRow([]);
        popUpController(false);
        deletePopUpController(false);
        formControllers.add(true);
    };

    const handleEditClick = (data: string) => {
        if (data.length > 0) {
            selectedRowController(data);
            formControllers.edit(true);
            popUpController(false);
            deletePopUpController(false);
        } else {
            if (selectedsRow.length === 1) {
                selectedRowController(selectedsRow[0]);
                formControllers.edit(true);
                popUpController(false);
                deletePopUpController(false);
            }
        }
    };

    const handleDeleteClick = async (data: string) => {
        if (data) {
            try {
                await deleteRow(data.split(",")[0]);
                setMadeRequest((prev) => !prev); 
                setDeletePopUpType("success");
                setDeletePopUpMessage("Excluído com sucesso");
                deletePopUpController(true);
            } catch (error: any) {
                setDeletePopUpType("error");
                setDeletePopUpMessage(error.message);
                deletePopUpController(true);
            }
        } else {
            selectedsRow.forEach(async (row) => {
                try {
                    await deleteRow(row.split(",")[0]);
                    setMadeRequest((prev) => !prev); 
                    setDeletePopUpType("success");
                    setDeletePopUpMessage("Excluído com sucesso");
                    deletePopUpController(true);
                } catch (error: any) {
                    setDeletePopUpType("error");
                    setDeletePopUpMessage(error.message);
                    deletePopUpController(true);
                }
            }
            );
        }
    };

    return (
        <>
            <InputWrapper>
                <ActionsWrapper>
                    <button onClick={handleClick}>Adicionar</button>
                    <Pencil id="edit" onClick={() => handleEditClick("")} className={selectedsRow.length === 1 ? "active" : ""} size={25} />
                    <Trash id="delete" onClick={() => handleDeleteClick("")} className={selectedsRow.length >= 1 ? "active" : ""} size={25} />
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
                                        <Pencil id="edit" onClick={() => handleEditClick(Object.values(row).toString())} className={selectedsRow ? "active" : ""} size={25} />
                                        <Trash id="delete" onClick={() => handleDeleteClick(Object.values(row).toString())} className={selectedsRow ? "active" : ""} size={25} />
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
                                        ? column.accessor == "date" ? column.formatter(row["date"]) : column.formatter(cellValue) ?? "?"
                                        : (typeof cellValue === "object" && cellValue !== null)
                                            ? cellValue[Object.keys(cellValue)[1]]
                                            : cellValue ?? "?";
                                    return <td className={selectedsRow?.includes(Object.values(row).toString()) ? "selected" : ""}  data-label={columns[colIndex].header + ":"} key={colIndex}>{formattedValue}</td>;
                                })}
                            </tr>
                        ))}
                    </tbody>
                </table>
                { isLoading ?
                    <Loading />
                    :
                    filteredData.length < 1 &&
                        <h3 id="not-found">Nenhum registro foi encontrado</h3>
                }
            </DataTableWrapper>
        </>
    );
}
