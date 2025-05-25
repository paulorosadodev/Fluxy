import { useState } from "react";
import { Customer } from "../../../@types";
import { Column, DataTable } from "../../../components/DataTable";
import { useData } from "../../../hooks/useData";
import { formatAddress, formatCNPJ, formatCPF, formatPhoneNumber, formatStateRegistration, isValidCNPJ, isValidCPF, isValidPhoneNumber, isValidStateRegistration } from "../../../utils";
import { z } from "zod";
import { EntityForm } from "../../../components/EntityForm";
import { PopUp } from "../../../components/PopUp";
import { addNaturalPersonCustomer, deleteNaturalPersonCustomer, editNaturalPersonCustomer } from "../../../services/endpoints/naturalPersonCustomer";
import { addLegalEntityCustomer, deleteLegalEntityCustomer, editLegalEntityCustomer } from "../../../services/endpoints/legalEntityCustomer";
import { Lock } from "phosphor-react";
import { useAuth } from "../../../hooks/useAuth";
import { Dashboard } from "../../../components/Dashboard";

export default function CustomersDashboard() {

    const {role} = useAuth();
    const {naturalPersonCustomers, legalEntityCustomers} = useData();
    const [isNaturalPersonAddFormOpened, setIsNaturalPersonAddFormOpened] = useState(false);
    const [isNaturalPersonEditFormOpened, setIsNaturalPersonEditFormOpened] = useState(false);
    const [isLegalEntityAddFormOpened, setIsLegalEntityAddFormOpened] = useState(false);
    const [isLegalEntityEditFormOpened, setIsLegalEntityEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [naturalPersonSelectedRow, setNaturalPersonSelectedRow] = useState("");
    const [legalEntitySelectedRow, setLegalEntitySelectedRow] = useState("");
    const [showDeletePopUp, setShowDeletePopUp] = useState(false);
    const [deletePopUpMessage, setDeletePopUpMessage] = useState("");
    const [deletePopUpType, setDeletePopUpType] = useState<"success" | "error">("error");

    const columnsNaturalPerson: Column<Customer>[] = [
        { header: "CPF", accessor: "cpf", formatter: formatCPF },
        { header: "Nome", accessor: "name" },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber }
    ];

    const columnsLegalEntity: Column<Customer>[] = [
        { header: "CNPJ", accessor: "cnpj", formatter: formatCNPJ },
        { header: "Inscrição Estadual", accessor: "stateRegistration", formatter: formatStateRegistration },
        { header: "Razão Social", accessor: "legalName" },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber }
    ];

    const naturalPersonFields = [
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
        ],
        [
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
    ];

    const legalEntityFields = [
        [
            {
                label: "Razão Social",
                type: "text",
                value: "legalName",
                placeholder: "Digite a razão social",
                validation: z.string().min(2, { message: "Razão Social deve ter pelo menos 2 caracteres" }),
            }
        ],
        [
            {
                label: "CNPJ",
                type: "text",
                value: "cnpj",
                placeholder: "Digite o CNPJ",
                validation: z.string().refine(isValidCNPJ, {
                    message: "CNPJ inválido",
                }),
            },
            {
                label: "Inscrição Estadual",
                type: "text",
                value: "stateRegistration",
                placeholder: "Digite a inscrição estadual",
                validation: z.string().refine(isValidStateRegistration, {
                    message: "Inscrição Estadual inválida",
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
        ],
        [
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
    ];

    const naturalPersonFormControllers = {
        add: setIsNaturalPersonAddFormOpened, 
        edit: setIsNaturalPersonEditFormOpened
    };

    const legalEntityFormControllers = {
        add: setIsLegalEntityAddFormOpened, 
        edit: setIsLegalEntityEditFormOpened
    };

    let editNaturalPersonData = [""];
    let editLegalEntityData = [""];

    if (naturalPersonSelectedRow.length > 1) {

        const selectedNaturalPerson = naturalPersonCustomers.filter((Customer) => String(Customer.id) === naturalPersonSelectedRow.split(",")[0])[0];
    
        editNaturalPersonData = [
            String(selectedNaturalPerson.id), selectedNaturalPerson.name ?? "", selectedNaturalPerson.cpf ?? "", ...selectedNaturalPerson.phone, 
            selectedNaturalPerson.address.cep, selectedNaturalPerson.address.city, selectedNaturalPerson.address.neighborhood, selectedNaturalPerson.address.street, selectedNaturalPerson.address.number
        ];
    }

    if (legalEntitySelectedRow.length > 1) {
        const selectedLegalEntity = legalEntityCustomers.filter((Customer) => String(Customer.id) === legalEntitySelectedRow.split(",")[0])[0];
        editLegalEntityData = [
            String(selectedLegalEntity.id), selectedLegalEntity.legalName ?? "", selectedLegalEntity.cnpj ?? "", selectedLegalEntity.stateRegistration ?? "", 
            ...selectedLegalEntity.phone, selectedLegalEntity.address.cep, selectedLegalEntity.address.city, 
            selectedLegalEntity.address.neighborhood, selectedLegalEntity.address.street, selectedLegalEntity.address.number
        ];
    }

    return (
        <>  
            {
                role.includes("customers") ? (
                    <>
                        <EntityForm type="Adicionar" title="Pessoa Física" fields={naturalPersonFields} open={isNaturalPersonAddFormOpened} formControllers={naturalPersonFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addNaturalPersonCustomer}/>
                        {editNaturalPersonData.length > 1 && 
                    <EntityForm type="Editar" title="Pessoa Física" fields={naturalPersonFields} open={isNaturalPersonEditFormOpened} formControllers={naturalPersonFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editNaturalPersonData} onSubmitAPI={editNaturalPersonCustomer}/>
                        }

                        <EntityForm type="Adicionar" title="Pessoa Jurídica" fields={legalEntityFields} open={isLegalEntityAddFormOpened} formControllers={legalEntityFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addLegalEntityCustomer} />
                        {editLegalEntityData.length > 1 && 
                    <EntityForm type="Editar" title="Pessoa Jurídica" fields={legalEntityFields} open={isLegalEntityEditFormOpened} formControllers={legalEntityFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editLegalEntityData} onSubmitAPI={editLegalEntityCustomer} />
                        }

                        <div id="main">
                            <h1>Clientes</h1>
                            <Dashboard dataDashboards={
                                [
                                    ["clientsTotalCount", "clientsPhysicalCount", "clientsJuridicalCount"],
                                ]
                            } />

                            <h2>Pessoas Físicas</h2>
                            <DataTable deleteRow={deleteNaturalPersonCustomer} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} data={naturalPersonCustomers} columns={columnsNaturalPerson} entityName="pessoas físicas" popUpController={setShowPopUp} formControllers={naturalPersonFormControllers} selectedRowController={setNaturalPersonSelectedRow} />

                            <h2>Pessoas Jurídicas</h2>
                            <DataTable deleteRow={deleteLegalEntityCustomer} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} data={legalEntityCustomers} columns={columnsLegalEntity} entityName="pessoas jurídicas" popUpController={setShowPopUp} formControllers={legalEntityFormControllers} selectedRowController={setLegalEntitySelectedRow} />

                            <Dashboard dataDashboards={
                                [
                                    ["topTierClients", "clientsByCity", "topTierSpendibleClients"]
                                ]
                            } />

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
