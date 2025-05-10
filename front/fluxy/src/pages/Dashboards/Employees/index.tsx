import { useState } from "react";
import { Employee } from "../../../@types";
import { Column, DataTable } from "../../../components/DataTable";
import { useData } from "../../../hooks/useData";
import { formatMoney, formatCPF, isValidCPF, formatAddress, formatPhoneNumber, isValidPhoneNumber } from "../../../utils";
import { EntityForm } from "../../../components/EntityForm";
import { z } from "zod";
import { PopUp } from "../../../components/PopUp";
import { addEmployee, deleteEmployee, editEmployee } from "../../../services/endpoints/employee";
import { useAuth } from "../../../hooks/useAuth";
import { Lock } from "phosphor-react";

export default function EmployeesDashboard() {

    const {employees} = useData();
    const {role} = useAuth();
    const [isAddFormOpened, setIsAddFormOpened] = useState(false);
    const [isEditFormOpened, setIsEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [selectedRow, setSelectedRow] = useState("");
    const [showDeletePopUp, setShowDeletePopUp] = useState(false);
    const [deletePopUpMessage, setDeletePopUpMessage] = useState("");
    const [deletePopUpType, setDeletePopUpType] = useState<"success" | "error">("error");

    const columns: Column<Employee>[] = [
        { header: "Matrícula", accessor: "employeeNumber" },
        { header: "Nome", accessor: "name" },
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber },
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
                validation: z.array(z.string().refine(isValidPhoneNumber, {
                    message: "Telefone inválido",
                })),
            },
            {
                label: "CEP",
                type: "text",
                value: "cep",
                placeholder: "Digite o CEP",
                validation: z
                    .string()
                    .transform((val) => val.replace(/\D/g, "")) 
                    .refine((val) => /^\d{8}$/.test(val), { message: "CEP deve conter exatamente 8 números" }),
            },
        ],
        [
            {
                label: "Cidade",
                type: "text",
                value: "city",
                placeholder: "Digite a cidade",
                validation: z.string().min(2, { message: "Cidade deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "Bairro",
                type: "text",
                value: "neighborhood",
                placeholder: "Digite o bairro",
                validation: z.string().min(2, { message: "Bairro deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "Endereço",
                type: "text",
                value: "street",
                placeholder: "Digite a rua",
                validation: z.string().min(2, { message: "Rua deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "Número",
                type: "text",
                value: "number",
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

    let editData = [""];

    if (selectedRow.length > 1) {
        const selectedEmployee = employees.filter((employee) => String(employee.id) === selectedRow.split(",")[0])[0];

        editData = [
            String(selectedEmployee.id), selectedEmployee.name, selectedEmployee.cpf, ...selectedEmployee.phone, 
            selectedEmployee.address.cep, selectedEmployee.address.city, selectedEmployee.address.neighborhood, selectedEmployee.address.street, selectedEmployee.address.number, selectedEmployee.role, selectedEmployee.sectorOfActivity, selectedEmployee.workShift, String(selectedEmployee.salary)
        ];

    }

    return (
        <>  
            {
                role.includes("employees") ? (
                    <>
                        <EntityForm type="Adicionar" title="Funcionário" fields={fields} open={isAddFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addEmployee} />
                        {editData.length > 1 && 
                        <EntityForm type="Editar" title="Funcionário" fields={fields} open={isEditFormOpened} formControllers={formControllers} selectedRowController={setSelectedRow} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editData} onSubmitAPI={editEmployee} />
                        }
                        <div id="main">
                            <h1>Funcionários</h1>
                            <DataTable deleteRow={deleteEmployee} data={employees} columns={columns} entityName="funcionários" popUpController={setShowPopUp} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} formControllers={formControllers} selectedRowController={setSelectedRow}/>
                            {showPopUp &&
                            <PopUp type="success" message={popUpMessage} show={showPopUp} onClose={() => setShowPopUp(false)} />
                            }
                            {showDeletePopUp &&
                            <PopUp type={deletePopUpType} message={deletePopUpMessage} show={showDeletePopUp} onClose={() => setShowDeletePopUp(false)} />
                            }
                        </div>
                    </>
                ) : (
                    <div id="main">
                        <div className="unauthorized-container">
                            <Lock size={64} weight="duotone" className="unauthorized-icon" />
                            <h2 className="unauthorized-title">Acesso negado</h2>
                            <p className="unauthorized-text">Você não tem permissão para visualizar esta página.</p>
                        </div>
                    </div>
                )
            }
        </>
    );
}
