import { DataTableWrapper } from "./styles";

type DataTableProps<T extends Record<string, any>> = {
    data: T[];
    columns: Column<T>[];
};

export type Column<T> = {
    header: string;
    accessor: keyof T;
    formatter?: (value: any) => string | string[] ; 
};

export function DataTable<T extends Record<string, any>>({ data, columns }: DataTableProps<T>) {
    return (
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
                    {data.map((row, rowIndex) => (
                        <tr key={rowIndex}>
                            {columns.map((column, colIndex) => {
                                const cellValue = row[column.accessor];
                                const formattedValue = column.formatter
                                    ? column.formatter(cellValue)
                                    : String(cellValue);
                                return <td data-label={columns[colIndex].header + ":"} key={colIndex}>{formattedValue}</td>;
                            })}
                        </tr>
                    ))}
                </tbody>
            </table>
        </DataTableWrapper>
    );
}
