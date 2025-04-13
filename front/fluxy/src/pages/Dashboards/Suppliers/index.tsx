import { Column, DataTable } from "../../../components/DataTable";
import { Supplier, ProductSupply } from "../../../@types";
import { formatAddress, formatPhoneNumber, formatCNPJ, formatSupplier, formatProduct, formatStock, formatMoney, formatDate } from "../../../utils";

export default function SuppliersDashboard() {

    const dictionary = {cnpj: "67015726000179", name: "Estudos LTDA", address: {street: "Rua Faustino Porto", number: "200", neighborhood: "Boa Viagem", city: "Recife", cep: "51020270"}, phone: ["81999972730"]};
    const columns: Column<Supplier>[] = [
        { header: "CNPJ", accessor: "cnpj", formatter: formatCNPJ },
        { header: "Nome", accessor: "name" },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber },
    ];

    const array = [];
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);

    const dictionarySupply = {supplier: {cnpj: "67015726000179", name: "Estudos LTDA", address: {street: "Rua Faustino Porto", number: "200", neighborhood: "Boa Viagem", city: "Recife", cep: "51020270"}, phone: ["81999972730"]}, product: {codEa: "001", name: "Maçã", category: "HortiFruti", price: 5, stockQuantity: 304}, productAmount: 580, price: 250, date: new Date()};
    const columnsSupply: Column<ProductSupply>[] = [
        { header: "Fornecedor", accessor: "supplier", formatter: formatSupplier },
        { header: "Produto", accessor: "product", formatter: formatProduct },
        { header: "Quantidade", accessor: "productAmount", formatter: formatStock },
        { header: "Preço", accessor: "price", formatter: formatMoney },
        { header: "Data", accessor: "date", formatter: formatDate },
    ];

    const arraySupply = [];
    arraySupply.push(dictionarySupply);
    arraySupply.push(dictionarySupply);
    arraySupply.push(dictionarySupply);
    arraySupply.push(dictionarySupply);
    arraySupply.push(dictionarySupply);
    arraySupply.push(dictionarySupply);

    return (
        <>
            <h1>Supplier</h1>

            <h2>Fornecedores</h2>
            <DataTable data={array} columns={columns} />

            <h2>Entregas</h2>
            <DataTable data={arraySupply} columns={columnsSupply} />
        </>
    );
}
