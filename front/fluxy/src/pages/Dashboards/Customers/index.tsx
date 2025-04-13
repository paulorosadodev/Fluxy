import { Column, DataTable } from "../../../components/DataTable";
import { LegalEntityCustomer, NaturalPersonCustomer } from "../../../@types";
import { formatAddress, formatPhoneNumber, formatStateRegistration } from "../../../utils";

export default function CustomersDashboard() {

    const dictionaryNaturalPerson = {cpf: "14383252400", name: "Paulo Rosado", address: {street: "Rua Faustino Porto", number: "200", neighborhood: "Boa Viagem", city: "Recife", cep: "51020270"}, phone: ["81999972730"]};
    const columnsNaturalPerson: Column<NaturalPersonCustomer>[] = [
        { header: "CPF", accessor: "cpf" },
        { header: "Nome", accessor: "name" },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber }
    ];

    const arrayNaturalPerson = [];
    arrayNaturalPerson.push(dictionaryNaturalPerson);
    arrayNaturalPerson.push(dictionaryNaturalPerson);
    arrayNaturalPerson.push(dictionaryNaturalPerson);
    arrayNaturalPerson.push(dictionaryNaturalPerson);
    arrayNaturalPerson.push(dictionaryNaturalPerson);
    arrayNaturalPerson.push(dictionaryNaturalPerson);

    const dictionaryLegalEntity = {cnpj: "67015726000179", legalName: "Estudos LTDA", stateRegistration: "032141840", address: {street: "Rua Faustino Porto", number: "200", neighborhood: "Boa Viagem", city: "Recife", cep: "51020270"}, phone: ["81999972730"]};
    const columnsLegalEntity: Column<LegalEntityCustomer>[] = [
        { header: "CNPJ", accessor: "cnpj" },
        { header: "Inscrição Estadual", accessor: "stateRegistration", formatter: formatStateRegistration },
        { header: "Razão Social", accessor: "legalName" },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber }
    ];

    const arrayLegalEntity = [];
    arrayLegalEntity.push(dictionaryLegalEntity);
    arrayLegalEntity.push(dictionaryLegalEntity);
    arrayLegalEntity.push(dictionaryLegalEntity);
    arrayLegalEntity.push(dictionaryLegalEntity);
    arrayLegalEntity.push(dictionaryLegalEntity);
    arrayLegalEntity.push(dictionaryLegalEntity);

    return (
        <>
            <h1>Customers</h1>

            <h2>Pessoa Física</h2>
            <DataTable data={arrayNaturalPerson} columns={columnsNaturalPerson} />

            <h2>Pessoa Jurídica</h2>
            <DataTable data={arrayLegalEntity} columns={columnsLegalEntity} />
        </>
    );
}
