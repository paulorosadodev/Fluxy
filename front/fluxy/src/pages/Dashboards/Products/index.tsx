import { Column, DataTable } from "../../../components/DataTable";
import { Product } from "../../../@types";
import { formatMoney, formatStock } from "../../../utils";

import { useData } from "../../../hooks/useData";
import { EntityForm } from "../../../components/EntityForm";
import { useEffect, useState } from "react";
import { z } from "zod";
import { PopUp } from "../../../components/PopUp";

import { addProduct, deleteProduct, editProduct } from "../../../services/endpoints/product";
import { useAuth } from "../../../hooks/useAuth";

import { Lock } from "phosphor-react";
import { Dashboard } from "../../../components/Dashboard";

export default function ProductsDashboard() {
    
    const {role} = useAuth();
    const { products, categories } = useData();
    const [isAddFormOpened, setIsAddFormOpened] = useState(false);
    const [isEditFormOpened, setIsEditFormOpened] = useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [showDeletePopUp, setShowDeletePopUp] = useState(false);
    const [deletePopUpMessage, setDeletePopUpMessage] = useState("");
    const [deletePopUpType, setDeletePopUpType] = useState<"success" | "error">("error");
    const [selectedRow, setSelectedRow] = useState(""); 
    const [showGraphs, setShowGraphs] = useState(false);

    useEffect(() => {
        if (products.length > 0) {
            const timeout = setTimeout(() => setShowGraphs(true), 500); 
            return () => clearTimeout(timeout);
        }
        setShowGraphs(false);
    }, [products.length]);

    const columns: Column<Product>[] = [
        { header: "Código EA", accessor: "codEa" },
        { header: "Nome", accessor: "name" },
        { header: "Categoria", accessor: "category" },
        { header: "Preço", accessor: "price", formatter: formatMoney },
        { header: "Quantidade", accessor: "stockQuantity", formatter: formatStock },
    ];

    const fields = [
        [
            {
                label: "Código EA",
                type: "text",
                value: "codEa",
                placeholder: "Digite o código EA",
                validation: z.string().min(5, { message: "Código EA deve ter pelo menos 5 caracteres" }).max(13, { message: "Código EA deve ter até 13 caracteres" }),
            }
        ],
        [
            {
                label: "Nome",
                type: "text",
                value: "name",
                placeholder: "Digite o nome",
                validation: z.string().min(2, { message: "Nome deve ter pelo menos 2 caracteres" }),
            }
        ],
        [
            {
                label: "Categoria",
                type: "select",
                value: "categoryCode",
                placeholder: "Selecione a categoria",
                validation: z.string().min(1, { message: "Categoria é obrigatória" }),
                options: categories.map((categories) => categories.name),
            }
        ],
        [
            {
                label: "Preço",
                type: "number",
                value: "price",
                placeholder: "Digite o valor",
                validation: z.coerce.number().min(0.01, { message: "Preço deve ser maior que 0" }),
            },
            {
                label: "Quantidade",
                type: "number",
                value: "stockQuantity",
                placeholder: "Digite a quantidade",
                validation: z.coerce.number().int().min(1, { message: "Quantidade deve ser pelo menos 1" }),
            }
        ]
    ];

    const formControllers = {
        add: setIsAddFormOpened, 
        edit: setIsEditFormOpened
    };

    let editData = [""];

    if (selectedRow.length > 1 && products) {

        const selectedProduct = products.filter((product) => String(product.id) === selectedRow.split(",")[0])[0];
        if (selectedProduct) {
            editData = [String(selectedProduct.id), selectedProduct.codEa, selectedProduct.name, selectedProduct.category.name, String(selectedProduct.price), String(selectedProduct.stockQuantity)];
        }
    }

    return (
        <>  
            {
                role.includes("products") ? (
                    <>
                        <EntityForm type="Adicionar" title="Produto" fields={fields} open={isAddFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} onSubmitAPI={addProduct} />
                        {editData.length > 1 && 
                        <EntityForm type="Editar" title="Produto" fields={fields} open={isEditFormOpened} formControllers={formControllers} popUpController={setShowPopUp} popUpMessage={setPopUpMessage} data={editData} onSubmitAPI={editProduct} />
                        }
                        <div id="main">
                            <h1>Produtos</h1>
                            <Dashboard dataDashboards={
                                [
                                    ["productsTotalCount", "productsCategoriesCount", "productsTotalStock"],
                                    ["productsAveragePrice", "productsTotalPrice"],
                                ]
                            } />
                            <DataTable deleteRow={deleteProduct} data={products} columns={columns} entityName="produtos" popUpController={setShowPopUp} deletePopUpController={setShowDeletePopUp} setDeletePopUpMessage={setDeletePopUpMessage} setDeletePopUpType={setDeletePopUpType} formControllers={formControllers} selectedRowController={setSelectedRow}/>
                            {showGraphs && 
                            <Dashboard graphs={true} dataDashboards={
                                [   
                                    ["productsCountByCategory", "topTierProducts", "lowStockProducts"],
                                    ["mostBoughtProductsChart", "leastBoughtProductsChart"],
                                    ["productPriceHistory"],
                                    ["mostPurchasedCategories"]
                                ]
                            } />
                            }
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
