import { Column, DataTable } from "../../../components/DataTable";
import { Product } from "../../../@types";
import { formatMoney, formatStock } from "../../../utils";

import { useData } from "../../../hooks/useData";

export default function ProductsDashboard() {

    const { products } = useData();

    const columns: Column<Product>[] = [
        { header: "Código EA", accessor: "codEa" },
        { header: "Nome", accessor: "name" },
        { header: "Categoria", accessor: "category" },
        { header: "Preço", accessor: "price", formatter: formatMoney },
        { header: "Quantidade", accessor: "stockQuantity", formatter: formatStock },
    ];

    return (
        <>
            <h1>Products</h1>
            <DataTable data={products} columns={columns} entityName="produtos"/>
        </>
    );
}
