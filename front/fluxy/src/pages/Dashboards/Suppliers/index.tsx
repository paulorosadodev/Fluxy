import { Column, DataTable } from "../../../components/DataTable";
import { Supplier, ProductSupply } from "../../../@types";
import { formatAddress, formatPhoneNumber, formatCNPJ, formatSupplier, formatProduct, formatStock, formatMoney, formatDate, isValidCNPJ, isValidPhoneNumber, formatPurchaseProduct } from "../../../utils";
import { useData } from "../../../hooks/useData";
import { PopUp } from "../../../components/PopUp";
import { useState } from "react";
import { z } from "zod";
import { EntityForm } from "../../../components/EntityForm";

import { addSupplier, deleteSupplier, editSupplier } from "../../../services/endpoints/supplier";
import { addSupply, deleteSupply, editSupply } from "../../../services/endpoints/supply";
import { useAuth } from "../../../hooks/useAuth";
import { Lock } from "phosphor-react";
import { Dashboard } from "../../../components/Dashboard";

export default function SuppliersDashboard() {

    const {suppliers, productSupplies, products} = useData();
    const {role} = useAuth();
    const [isSupplierAddFormOpened, setIsSupplierAddFormOpened] = useState(false);
    const [isSupplierEditFormOpened, setIsSupplierEditFormOpened] = useState(false);
    const [isSupplyAddFormOpened, setIsSupplyAddFormOpened] = useState(false);
    const [isSupplyEditFormOpened, setIsSupplyEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [showDeletePopUp, setShowDeletePopUp] = useState(false);
    const [deletePopUpMessage, setDeletePopUpMessage] = useState("");
    const [deletePopUpType, setDeletePopUpType] = useState<"success" | "error">("error");
    const [supplierSelectedRow, setSupplierSelectedRow] = useState("");
    const [supplySelectedRow, setSupplySelectedRow] = useState("");

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
        { header: "Data", accessor: "date", formatter: formatDate },
    ];

    const supplierFields = [
        [
            {
                label: "Nome",
                type: "text",
                value: "name",
                placeholder: "Digite o nome",
                validation: z.string().min(2, { message: "Nome deve ter pelo menos 2 caracteres" }),
            },
            {
                label: "CNPJ",
                type: "text",
                value: "cnpj",
                placeholder: "Digite o CNPJ",
                validation: z.string().refine(isValidCNPJ, {
                    message: "CNPJ inválido",
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

    const supplyFields = [
        [
            {
                label: "Fornecedor",
                type: "select",
                value: "supplier",
                placeholder: "Selecione o fornecedor",
                validation: z.string().min(1, { message: "Fornecedor é obrigatório" }),
                options: suppliers.filter(supplier => supplier && supplier.name && supplier.cnpj).map((supplier) => formatSupplier(supplier)),
            },
        ],
        [
            {
                label: "Produto",
                type: "select",
                value: "product",
                placeholder: "Selecione o produto",
                validation: z.string().min(1, { message: "Produto é obrigatório" }),
                options: products.filter(product => product && product.id !== undefined && product.name !== undefined).map((product) => formatPurchaseProduct(product)),
            },
            {
                label: "Quantidade",
                type: "number",
                value: "productAmount",
                placeholder: "Digite a quantidade",
                validation: z.coerce.number().min(1, { message: "Quantidade deve ser maior que 0" }),
            }
        ],
        [
            {
                label: "Preço",
                type: "number",
                value: "price",
                placeholder: "Digite o preço",
                validation: z.coerce.number().min(0.01, { message: "Preço deve ser maior que 0" }),
            },
            {
                label: "Data",
                type: "date",
                value: "date",
                placeholder: "Selecione a data",
                validation: z.string().min(1, { message: "Data é obrigatória" }),
            },
        ],
    ];
    

    const suppliersFormControllers = {
        add: setIsSupplierAddFormOpened, 
        edit: setIsSupplierEditFormOpened
    };

    const suppliesFormControllers = {
        add: setIsSupplyAddFormOpened, 
        edit: setIsSupplyEditFormOpened
    };

    let editSupplierData = [""];
    let editSupplyData = [""];

    if (supplierSelectedRow.length > 1) {
        const selectedSupplier = suppliers.filter((Supplier) => Supplier.cnpj === supplierSelectedRow.split(",")[1])[0];

        if (selectedSupplier) {
            editSupplierData = [
                String(selectedSupplier.id), selectedSupplier.name, selectedSupplier.cnpj, ...selectedSupplier.phone, selectedSupplier.address.cep, 
                selectedSupplier.address.city, selectedSupplier.address.neighborhood, selectedSupplier.address.street,
                selectedSupplier.address.number
            ];
        }
    }

    if (supplySelectedRow.length > 1) {
        const selectedSupply = productSupplies.filter((Supply) => String(Supply.id) === supplySelectedRow.split(",")[0])[0];
        
        if (selectedSupply) {
            editSupplyData = [
                String(selectedSupply.id), formatSupplier(selectedSupply.supplier), formatPurchaseProduct(selectedSupply.product), String(selectedSupply.productAmount), String(selectedSupply.price), selectedSupply.date
            ];
        }
    }

    return (
        <>
            {
                role.includes("suppliers") ? (
                    <>
                        <EntityForm type="Adicionar" title="Fornecedor" fields={supplierFields} open={isSupplierAddFormOpened} formControllers={suppliersFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addSupplier} />
                        {editSupplierData.length > 1 && 
                        <EntityForm type="Editar" title="Fornecedor" fields={supplierFields} open={isSupplierEditFormOpened} formControllers={suppliersFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editSupplierData} onSubmitAPI={editSupplier} />
                        }
                        <EntityForm type="Adicionar" title="Entrega" fields={supplyFields} open={isSupplyAddFormOpened} formControllers={suppliesFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addSupply} />
                        {editSupplyData.length > 1 && 
                        <EntityForm type="Editar" title="Entrega" fields={supplyFields} open={isSupplyEditFormOpened} formControllers={suppliesFormControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editSupplyData} onSubmitAPI={editSupply} />
                        }
                        <div id="main">
                            <h1>Abastecimento</h1>

                            <Dashboard dataDashboards={
                                [
                                    ["suppliersTotalCount",  "deliveriesMonthlyCount", "deliveriesTotalCount"],
                                    ["deliveriesMonthlyCost", "deliveriesMonthlyAverageCost"],
                                    ["deliveriesTotalCost", "deliveriesTotalAverageCost"]
                                ]
                            } />

                            <h2>Fornecedores</h2>
                            <DataTable deleteRow={deleteSupplier} data={suppliers} columns={columnsSuppliers} entityName="fornecedores" popUpController={setShowPopUp} formControllers={suppliersFormControllers} selectedRowController={setSupplierSelectedRow} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} />

                            <h2>Entregas</h2>
                            <DataTable deleteRow={deleteSupply} data={productSupplies} columns={columnsSupply} entityName="entregas" popUpController={setShowPopUp} formControllers={suppliesFormControllers} selectedRowController={setSupplySelectedRow} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} />

                            <Dashboard dataDashboards={
                                [
                                    ["topTierSuppliers", "topTierDeliveries"],
                                    ["deliveriesByMonth"],
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
                ): (
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
