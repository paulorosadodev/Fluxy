import { Column, DataTable } from "../../../components/DataTable";
import { ManagerEmployee, AssistantManagerEmployee, OperationsEmployee } from "../../../@types";
import { formatMoney, formatCPF } from "../../../utils";

export default function EmployeesDashboard() {

    const dictionaryOperations = {employeeNumber: "001", name: "Paulo Rosado", cpf: "14383252400", role: "Caixa", salary: 3040.50} as OperationsEmployee;
    const columnsOperations: Column<OperationsEmployee>[] = [
        { header: "Matrícula", accessor: "employeeNumber" },
        { header: "Nome", accessor: "name" },
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Cargo", accessor: "role" },
        { header: "Salário", accessor: "salary", formatter: formatMoney },
    ];

    const arrayOperations = [];
    arrayOperations.push(dictionaryOperations);
    arrayOperations.push(dictionaryOperations);
    arrayOperations.push(dictionaryOperations);
    arrayOperations.push(dictionaryOperations);
    arrayOperations.push(dictionaryOperations);
    arrayOperations.push(dictionaryOperations);

    const dictionaryAssistantManager = {employeeNumber: "001", name: "Paulo Rosado", cpf: "14383252400", workShift: "08:00 - 18:00", salary: 3040.50} as AssistantManagerEmployee;

    const columnsAssistantManager: Column<AssistantManagerEmployee>[] = [
        { header: "Matrícula", accessor: "employeeNumber" },
        { header: "Nome", accessor: "name" },
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Turno", accessor: "workShift" },
        { header: "Salário", accessor: "salary", formatter: formatMoney },
    ];

    const arrayAssistantManager = [];
    arrayAssistantManager.push(dictionaryAssistantManager);
    arrayAssistantManager.push(dictionaryAssistantManager);
    arrayAssistantManager.push(dictionaryAssistantManager);
    arrayAssistantManager.push(dictionaryAssistantManager);
    arrayAssistantManager.push(dictionaryAssistantManager);
    arrayAssistantManager.push(dictionaryAssistantManager);

    const dictionaryManager = {employeeNumber: "001", name: "Paulo Rosado", cpf: "14383252400", sectorOfActivity: "Financeiro", salary: 3040.50} as ManagerEmployee;

    const columnsManager: Column<ManagerEmployee>[] = [
        { header: "Matrícula", accessor: "employeeNumber" },
        { header: "Nome", accessor: "name" },
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Setor", accessor: "sectorOfActivity" },
        { header: "Salário", accessor: "salary", formatter: formatMoney },
    ];

    const arrayManager = [];
    arrayManager.push(dictionaryManager);
    arrayManager.push(dictionaryManager);
    arrayManager.push(dictionaryManager);
    arrayManager.push(dictionaryManager);
    arrayManager.push(dictionaryManager);
    arrayManager.push(dictionaryManager);

    return (
        <>
            <h1>Employees</h1>

            <h2>Operacional</h2>
            <DataTable data={arrayOperations} columns={columnsOperations} />

            <h2>Subgerentes</h2>
            <DataTable data={arrayAssistantManager} columns={columnsAssistantManager} />

            <h2>Gerentes</h2>
            <DataTable data={arrayManager} columns={columnsManager} />
        </>
    );
}
