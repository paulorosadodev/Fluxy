import { GenericTopTier } from "../GenericTopTier";
import { fetchMostExpensivePurchases, fetchLeastExpensivePurchases } from "../../../services/endpoints/purchase/dashboard";
import { formatMoney } from "../../../utils";
import { ShoppingCart } from "phosphor-react";
import { useData } from "../../../hooks/useData";

export const TopTierPurchases = () => {
    const { purchases, products, naturalPersonCustomers, legalEntityCustomers } = useData();

    const config = {
        title: {
            high: "Compras mais caras",
            low: "Compras mais baratas"
        },
        sortOptions: {
            high: { value: "most-expensive", label: "Mais cara" },
            low: { value: "least-expensive", label: "Mais barata" }
        },
        icon: ShoppingCart,
        customDisplayFormatter: (item: any) => {
            const product = products.find(p => p.id === item.productId);
            const totalCost = item.productAmount * (product?.price || 0);

            const allCustomers = [...(naturalPersonCustomers ?? []), ...(legalEntityCustomers ?? [])];
            const customer = allCustomers.find(c => c.id === item.clientId);
            
            const customerName = customer?.name || customer?.legalName || "Cliente não encontrado";
            const productName = product?.name || "Produto não encontrado";
            
            return {
                leftDisplay: formatMoney(totalCost),
                rightDisplay: `${customerName} - ${item.productAmount}x ${productName}`
            };
        }
    };

    return (
        <GenericTopTier
            fetchHighData={fetchMostExpensivePurchases}
            fetchLowData={fetchLeastExpensivePurchases}
            config={config}
            dependencies={[purchases, products, naturalPersonCustomers, legalEntityCustomers]}
        />
    );
};
