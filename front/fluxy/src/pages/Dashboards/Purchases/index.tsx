import { Column, DataTable } from "../../../components/DataTable";
import { Customer, Purchase } from "../../../@types";
import { formatCustomer, formatDate, formatEmployee, formatMoney, formatPaymentMethod, formatProduct, formatPurchaseProduct, formatStock } from "../../../utils";
import { useData } from "../../../hooks/useData";
import { useEffect, useState } from "react";
import { PopUp } from "../../../components/PopUp";
import { EntityForm } from "../../../components/EntityForm";

import { addPurchase, deletePurchase, editPurchase } from "../../../services/endpoints/purchase";
import { z } from "zod";
import { Lock } from "phosphor-react";
import { useAuth } from "../../../hooks/useAuth";
import { Dashboard } from "../../../components/Dashboard";

export default function PurchasesDashboard() {

    const {purchases, products, employees, naturalPersonCustomers, legalEntityCustomers} = useData();
    const {role} = useAuth();
    const [formattedPurchases, setFormattedPurchases] = useState<Purchase[]>([]);
    const [customers, setCustomers] = useState<Customer[]>([]);
    const [isAddFormOpened, setIsAddFormOpened] = useState(false);
    const [isEditFormOpened, setIsEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [showDeletePopUp, setShowDeletePopUp] = useState(false);
    const [deletePopUpMessage, setDeletePopUpMessage] = useState("");
    const [deletePopUpType, setDeletePopUpType] = useState<"success" | "error">("error");
    const [selectedRow, setSelectedRow] = useState(""); 

    useEffect(() => {

        setFormattedPurchases(purchases.map((purchase) => {
            return {...purchase, total: purchase.product.price * purchase.productAmount};
        }));

        const combinedCustomers = [
            ...(naturalPersonCustomers ?? []),
            ...(legalEntityCustomers ?? [])
        ];
    
        setCustomers(combinedCustomers);

    }, [purchases, naturalPersonCustomers, legalEntityCustomers]);

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

    const fields = [
        [
            {
                label: "Cliente",
                type: "select",
                value: "customerId",
                placeholder: "Selecione o cliente",
                validation: z.string().min(1, { message: "Cliente é obrigatório" }),
                options: customers.map((customer) => formatCustomer(customer)),
            }
        ],
        [
            {
                label: "Produto",
                type: "select",
                value: "productId",
                placeholder: "Selecione o produto",
                validation: z.string().min(1, { message: "Produto é obrigatório" }),
                options: products.map((product) => formatPurchaseProduct(product)),
            },
            {
                label: "Quantidade",
                type: "number",
                value: "productAmount",
                placeholder: "Digite a quantidade",
                validation: z.coerce.number().int().min(1, { message: "Quantidade deve ser pelo menos 1" }),
            }
        ],
        [
            {
                label: "Forma de pagamento",
                type: "select",
                value: "paymentType",
                placeholder: "Selecione a forma de pagamento",
                validation: z.string().min(1, { message: "Forma de pagamento é obrigatória" }),
                options: ["Crédito", "Débito", "Pix", "Dinheiro", "Vale Alimentação", "Outro"]
            },
            {
                label: "Parcelas",
                type: "number",
                value: "installments",
                defaultValue: 1,
                placeholder: "Digite a quantidade de parcelas",
                validation: z.coerce.number().int().min(1, { message: "Quantidade de parcelas deve ser pelo menos 1" }),
            }
        ],
        [
            {
                label: "Funcionário",
                type: "select",
                value: "employeeId",
                placeholder: "Selecione o funcionário",
                validation: z.string().min(1, { message: "Funcionário é obrigatório" }),
                options: employees.filter((employee) => !employee.role.includes("Gerente")).map((employee) => formatEmployee(employee)),
            }
        ]
    ];

    const formControllers = {
        add: setIsAddFormOpened, 
        edit: setIsEditFormOpened
    };

    let editData = [""];

    if (selectedRow.length > 1 && formattedPurchases) {
        const selectedPurchase = formattedPurchases.filter((purchase) => String(purchase.number) === selectedRow.split(",")[0])[0];
        const selectedPurchaseProduct = products.filter((purchase) => String(purchase.codEa) === selectedPurchase.product.codEa)[0];
        
        
        if (selectedPurchase) {
            editData = [String(selectedPurchase.number), String(formatCustomer(selectedPurchase.customer)), String(formatPurchaseProduct(selectedPurchaseProduct)), 
                String(selectedPurchase.productAmount), String(selectedPurchase.paymentMethod.type), String(selectedPurchase.paymentMethod.installments), String(formatEmployee(selectedPurchase.employee))];
        }
    }

    return (
        <>
            {
                role.includes("purchases") ? (
                    <>
                        <EntityForm type="Adicionar" title="Compra" fields={fields} open={isAddFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addPurchase} />
                        {editData.length > 1 && 
                        <EntityForm type="Editar" title="Compra" fields={fields} open={isEditFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editData} onSubmitAPI={editPurchase} />
                        }
                        <div id="main">
                            <h1>Compras</h1>
                            <Dashboard dataDashboards={
                                [
                                    ["purchasesTotalCount"]
                                ]
                            } />
                            <DataTable deleteRow={deletePurchase} data={formattedPurchases} columns={columns} entityName="compras" popUpController={setShowPopUp} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} formControllers={formControllers} selectedRowController={setSelectedRow}/>
                            <Dashboard dataDashboards={
                                [
                                    ["paymentTypesChart", "topTierPurchases"],
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


