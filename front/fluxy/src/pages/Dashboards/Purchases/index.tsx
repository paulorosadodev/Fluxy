import { Column, DataTable } from "../../../components/DataTable";
import { Purchase } from "../../../@types";
import { formatCustomer, formatDate, formatEmployee, formatMoney, formatPaymentMethod, formatProduct, formatStock } from "../../../utils";
import { useData } from "../../../hooks/useData";
import { useEffect, useState } from "react";

export default function PurchasesDashboard() {

    const {purchases} = useData();

    const [formattedPurchases, setFormattedPurchases] = useState<Purchase[]>([]);

    useEffect(() => {

        setFormattedPurchases(purchases.map((purchase) => {
            return {...purchase, total: purchase.product.price * purchase.productAmount};
        }));

    }, [purchases]);

    const columns: Column<Purchase>[] = [
        { header: "Número", accessor: "number" },
        { header: "Cliente", accessor: "customer", formatter: formatCustomer },
        { header: "Produto", accessor: "product", formatter: formatProduct },
        { header: "Quantidade", accessor: "productAmount", formatter: formatStock },
        { header: "Total", accessor: "total", formatter: formatMoney },
        { header: "Pagamento", accessor: "paymentMethod", formatter: formatPaymentMethod },
        { header: "Funcionário", accessor: "employee", formatter: formatEmployee },
        { header: "Hora e Data", accessor: "date", formatter: formatDate },
    ];

    return (
        <>
            <h1>Purchases</h1>
            <DataTable data={formattedPurchases} columns={columns} />
        </>
    );
}


