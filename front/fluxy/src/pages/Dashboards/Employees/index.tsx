import { Employee } from "../../../@types";
import { Column, DataTable } from "../../../components/DataTable";
import { useData } from "../../../hooks/useData";
import { formatMoney, formatCPF } from "../../../utils";

export default function EmployeesDashboard() {

    const {employees} = useData();

    const columns: Column<Employee>[] = [
        { header: "Matrícula", accessor: "employeeNumber" },
        { header: "Nome", accessor: "name" },
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Cargo", accessor: "role" },
        { header: "Setor", accessor: "sectorOfActivity" },
        { header: "Turno", accessor: "workShift" },
        { header: "Salário", accessor: "salary", formatter: formatMoney },
    ];

    return (
        <>
            <h1>Funcionários</h1>

            <DataTable data={employees} columns={columns} entityName="funcionários"/>
        </>
    );
}
