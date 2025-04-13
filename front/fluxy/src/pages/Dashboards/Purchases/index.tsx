import { Column, DataTable } from "../../../components/DataTable";
import { OperationsEmployee, Purchase } from "../../../@types";
import { formatCustomer, formatDate, formatEmployee, formatPaymentMethod, formatProduct, formatStock } from "../../../utils";

export default function PurchasesDashboard() {

    const dictionary = {number: 1, customer: {cpf: "14383252400", name: "Paulo Rosado", address: {street: "Rua Faustino Porto", number: "200", neighborhood: "Boa Viagem", city: "Recife", cep: "51020270"}, phone: ["81999972730"]}, product: {codEa: "001", name: "Maçã", category: "HortiFruti", price: 5, stockQuantity: 304}, employee: [{employeeNumber: "001", name: "Paulo Rosado", cpf: "14383252400", role: "Caixa", salary: 3040.50} as OperationsEmployee], productAmount: 10, date: new Date(), paymentMethod: {type: "Credit Card", installments: 1}};
    const columns: Column<Purchase>[] = [
        { header: "Número", accessor: "number" },
        { header: "Cliente", accessor: "customer", formatter: formatCustomer },
        { header: "Produto", accessor: "product", formatter: formatProduct },
        { header: "Quantidade", accessor: "productAmount", formatter: formatStock },
        { header: "Forma de Pagamento", accessor: "paymentMethod", formatter: formatPaymentMethod },
        { header: "Funcionário", accessor: "employee", formatter: formatEmployee },
        { header: "Data", accessor: "date", formatter: formatDate },
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
            <h1>Orders</h1>
            <DataTable data={array} columns={columns} />
        </>
    );
}


