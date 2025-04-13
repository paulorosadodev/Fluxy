import { Column, DataTable } from "../../../components/DataTable";
import { Product } from "../../../@types";
import { formatMoney, formatStock } from "../../../utils";

export default function ProductsDashboard() {

    const dictionary = {codEa: "001", name: "Maçã", category: "HortiFruti", price: 5, stockQuantity: 304};
    const columns: Column<Product>[] = [
        { header: "Código EA", accessor: "codEa" },
        { header: "Nome", accessor: "name" },
        { header: "Categoria", accessor: "category" },
        { header: "Preço", accessor: "price", formatter: formatMoney },
        { header: "Quantidade", accessor: "stockQuantity", formatter: formatStock },
    ];

    const array = [];
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);
    array.push(dictionary);

    return (
        <>
            <h1>Products</h1>
            <DataTable data={array} columns={columns} />
        </>
    );
}
