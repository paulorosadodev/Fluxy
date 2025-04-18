import { Column, DataTable } from "../../../components/DataTable";
import { Supplier, ProductSupply } from "../../../@types";
import { formatAddress, formatPhoneNumber, formatCNPJ, formatSupplier, formatProduct, formatStock, formatMoney, formatDate } from "../../../utils";
import { useData } from "../../../hooks/useData";

export default function SuppliersDashboard() {

    const {suppliers, productSupplies} = useData();

    const columnsSuppliers: Column<Supplier>[] = [
        { header: "CNPJ", accessor: "cnpj", formatter: formatCNPJ },
        { header: "Nome", accessor: "name" },
        { header: "Endereço", accessor: "address", formatter: formatAddress },
        { header: "Telefone", accessor: "phone", formatter: formatPhoneNumber },
    ];

    const columnsSupply: Column<ProductSupply>[] = [
        { header: "Fornecedor", accessor: "supplier", formatter: formatSupplier },
        { header: "Produto", accessor: "product", formatter: formatProduct },
        { header: "Quantidade", accessor: "productAmount", formatter: formatStock },
        { header: "Preço", accessor: "price", formatter: formatMoney },
        { header: "Hora e Data", accessor: "date", formatter: formatDate },
    ];

    return (
        <>
            <h1>Supplier</h1>

            <h2>Fornecedores</h2>
            <DataTable data={suppliers} columns={columnsSuppliers} entityName="fornecedores" />

            <h2>Entregas</h2>
            <DataTable data={productSupplies} columns={columnsSupply} entityName="entregas" />
        </>
    );
}
