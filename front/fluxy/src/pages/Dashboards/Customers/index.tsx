import { useEffect, useState } from "react";
import { Customer } from "../../../@types";
import { Column, DataTable } from "../../../components/DataTable";
import { useData } from "../../../hooks/useData";
import { formatAddress, formatCNPJ, formatCPF, formatPhoneNumber, formatStateRegistration } from "../../../utils";

export default function CustomersDashboard() {

    const {customers} = useData();

    const [naturalPersonCustomers, setNaturalPersonCustumers] = useState<Customer[]>([]);
    const [legalEntityCustomers, setLegalEntityCustomers] = useState<Customer[]>([]);

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

    return (
        <>
            <h1>Clientes Pessoas Físicas</h1>
            <DataTable data={naturalPersonCustomers} columns={columnsNaturalPerson} entityName="pessoas físicas" />

            <h1>Clientes Pessoas Jurídicas</h1>
            <DataTable data={legalEntityCustomers} columns={columnsLegalEntity} entityName="pessoas jurídicas" />
        </>
    );
}
