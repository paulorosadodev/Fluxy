import { useEffect, useState } from "react";
import { Customer } from "../../../@types";
import { Column, DataTable } from "../../../components/DataTable";
import { useData } from "../../../hooks/useData";
import { formatAddress, formatCNPJ, formatCPF, formatPhoneNumber, formatStateRegistration, isValidCNPJ, isValidCPF, isValidPhoneNumber, isValidStateRegistration } from "../../../utils";
import { z } from "zod";
import { EntityForm } from "../../../components/EntityForm";
import { PopUp } from "../../../components/PopUp";

export default function CustomersDashboard() {

    const {customers} = useData();

    const [naturalPersonCustomers, setNaturalPersonCustumers] = useState<Customer[]>([]);
    const [legalEntityCustomers, setLegalEntityCustomers] = useState<Customer[]>([]);
    const [isNaturalPersonAddFormOpened, setIsNaturalPersonAddFormOpened] = useState(false);
    const [isNaturalPersonEditFormOpened, setIsNaturalPersonEditFormOpened] = useState(false);
    const [isLegalEntityAddFormOpened, setIsLegalEntityAddFormOpened] = useState(false);
    const [isLegalEntityEditFormOpened, setIsLegalEntityEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [naturalPersonSelectedRow, setNaturalPersonSelectedRow] = useState("");
    const [legalEntitySelectedRow, setLegalEntitySelectedRow] = useState("");

    useEffect(() => {
        const naturalPerson = customers.filter(customer => customer.cpf);
        const legalEntity = customers.filter(customer => customer.cnpj);
    
        setNaturalPersonCustumers(naturalPerson);
        setLegalEntityCustomers(legalEntity);
    }, [customers]);
    
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
        const selectedNaturalPerson = naturalPersonCustomers.filter((Customer) => Customer.cpf === naturalPersonSelectedRow.split(",")[0])[0];
    
        editNaturalPersonData = [
            selectedNaturalPerson.name ?? "", selectedNaturalPerson.cpf ?? "", ...selectedNaturalPerson.phone, 
            selectedNaturalPerson.address.cep, selectedNaturalPerson.address.city, selectedNaturalPerson.address.neighborhood, selectedNaturalPerson.address.street, selectedNaturalPerson.address.number
        ];
    }

    if (legalEntitySelectedRow.length > 1) {
        const selectedLegalEntity = legalEntityCustomers.filter((Customer) => Customer.cnpj === legalEntitySelectedRow.split(",")[0])[0];
    
        editLegalEntityData = [
            selectedLegalEntity.legalName ?? "", selectedLegalEntity.cnpj ?? "", selectedLegalEntity.stateRegistration ?? "", 
            ...selectedLegalEntity.phone, selectedLegalEntity.address.cep, selectedLegalEntity.address.city, 
            selectedLegalEntity.address.neighborhood, selectedLegalEntity.address.street, selectedLegalEntity.address.number
        ];
    }

    return (
        <>  
            <EntityForm type="Adicionar" title="Pessoa Física" fields={naturalPersonFields} open={isNaturalPersonAddFormOpened} formControllers={naturalPersonFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} />
            {editNaturalPersonData.length > 1 && 
                <EntityForm type="Editar" title="Pessoa Física" fields={naturalPersonFields} open={isNaturalPersonEditFormOpened} formControllers={naturalPersonFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editNaturalPersonData} />
            }
            <EntityForm type="Adicionar" title="Pessoa Jurídica" fields={legalEntityFields} open={isLegalEntityAddFormOpened} formControllers={legalEntityFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} />
            {editLegalEntityData.length > 1 && 
                <EntityForm type="Editar" title="Pessoa Jurídica" fields={legalEntityFields} open={isLegalEntityEditFormOpened} formControllers={legalEntityFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editLegalEntityData} />
            }
            <div id="main">
                <h1>Clientes Pessoas Físicas</h1>
                <DataTable data={naturalPersonCustomers} columns={columnsNaturalPerson} entityName="pessoas físicas" popUpController={setShowPopUp} formControllers={naturalPersonFormControllers} selectedRowController={setNaturalPersonSelectedRow} />

                <h1>Clientes Pessoas Jurídicas</h1>
                <DataTable data={legalEntityCustomers} columns={columnsLegalEntity} entityName="pessoas jurídicas" popUpController={setShowPopUp} formControllers={legalEntityFormControllers} selectedRowController={setLegalEntitySelectedRow} />

                {showPopUp &&
                    <PopUp type="success" message={popUpMessage} show={showPopUp} onClose={() => setShowPopUp(false)} />
                }
            </div>
        </>
    );
}
