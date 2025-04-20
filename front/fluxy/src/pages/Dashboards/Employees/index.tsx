import { useState } from "react";
import { Employee } from "../../../@types";
import { Column, DataTable } from "../../../components/DataTable";
import { useData } from "../../../hooks/useData";
import { formatMoney, formatCPF, isValidCPF } from "../../../utils";
import { EntityForm } from "../../../components/EntityForm";
import { z } from "zod";
import { PopUp } from "../../../components/PopUp";

export default function EmployeesDashboard() {

    const {employees} = useData();
    const [isAddFormOpened, setIsAddFormOpened] = useState(false);
    const [isEditFormOpened, setIsEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [selectedRow, setSelectedRow] = useState("");

    const columns: Column<Employee>[] = [
        { header: "Matrícula", accessor: "employeeNumber" },
        { header: "Nome", accessor: "name" },
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Cargo", accessor: "role" },
        { header: "Setor", accessor: "sectorOfActivity" },
        { header: "Turno", accessor: "workShift" },
        { header: "Salário", accessor: "salary", formatter: formatMoney },
    ];

    const roles = [
        "Caixa",
        "Repositor",
        "Açougueiro",
        "Padeiro",
        "Vigilante",
        "Gerente de Setor",
        "Gerente de Loja",
        "Auxiliar",
        "Estagiário"
    ];
    
    const sectors = [
        "Padaria",
        "Açougue",
        "Hortifruti",
        "Caixa",
        "Limpeza",
        "Estoque",
        "Administração",
        "Atendimento ao Cliente"
    ];
    
    const workShifts = [
        "Manhã",
        "Tarde",
        "Noite",
        "Integral",
        "Turno Flexível"
    ];

    const fields = [
        [
            {
                label: "Nome",
                type: "text",
                value: "name",
                placeholder: "Digite o nome",
                validation: z.string().min(2, { message: "Nome deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "CPF",
                type: "text",
                value: "cpf",
                placeholder: "Digite o CPF",
                validation: z.string().refine(isValidCPF, {
                    message: "CPF inválido",
                }),
            },
        ],
        [
            {
                label: "Telefone",
                type: "text",
                value: "phone",
                placeholder: "Digite o número de telefone",
                validation: z.array(z.string().min(10, { message: "Número de telefone inválido" })),
            },
            {
                label: "CEP",
                type: "text",
                value: "address.cep",
                placeholder: "Digite o CEP",
                validation: z.string().length(8, { message: "CEP deve ter 8 caracteres" }).regex(/^\d+$/, { message: "CEP deve conter apenas números" }),
            },
        ],
        [
            {
                label: "Cidade",
                type: "text",
                value: "address.city",
                placeholder: "Digite a cidade",
                validation: z.string().min(2, { message: "Cidade deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "Bairro",
                type: "text",
                value: "address.neighborhood",
                placeholder: "Digite o bairro",
                validation: z.string().min(2, { message: "Bairro deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "Endereço",
                type: "text",
                value: "address.street",
                placeholder: "Digite a rua",
                validation: z.string().min(2, { message: "Rua deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "Número",
                type: "text",
                value: "address.number",
                placeholder: "Digite o número",
                validation: z.string().min(1, { message: "Número não pode ser vazio" }),
            },
        ],
        [
            {
                label: "Cargo",
                type: "select",
                value: "role",
                placeholder: "Selecione o cargo",
                options: roles,
                validation: z.string().min(1, { message: "Selecione um cargo" }),
            },
            {
                label: "Setor",
                type: "select",
                value: "sectorOfActivity",
                placeholder: "Selecione o setor",
                options: sectors,
                validation: z.string().min(1, { message: "Selecione um setor" }),
            },
        ],
        [
            {
                label: "Turno",
                type: "select",
                value: "workShift",
                placeholder: "Selecione o turno",
                options: workShifts,
                validation: z.string().min(1, { message: "Selecione um turno" }),
            },
            {
                label: "Salário",
                type: "number",
                value: "salary",
                placeholder: "Digite o salário",
                validation: z.coerce.number().positive({ message: "Salário deve ser positivo" }),
            },
        ],
    ];
    

    const formControllers = {
        add: setIsAddFormOpened, 
        edit: setIsEditFormOpened
    };


    return (
        <>  
            <EntityForm type="Adicionar" title="Funcionário" fields={fields} open={isAddFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} />
            <EntityForm type="Editar" title="Funcionário" fields={fields} open={isEditFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={selectedRow.split(",").slice(1)} />
            <div id="main">
                <h1>Funcionários</h1>
                <DataTable data={employees} columns={columns} entityName="funcionários" popUpController={setShowPopUp} formControllers={formControllers} selectedRowController={setSelectedRow}/>
                {showPopUp &&
                    <PopUp type="success" message={popUpMessage} show={showPopUp} onClose={() => setShowPopUp(false)} />
                }
            </div>
        </>
    );
}
