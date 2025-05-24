import { GenericTopTier } from "../GenericTopTier";
import { fetchMostExpensiveDeliveries, fetchLeastExpensiveDeliveries } from "../../../services/endpoints/supply/dashboard";
import { Package } from "phosphor-react";
import { useData } from "../../../hooks/useData";
import { formatMoney } from "../../../utils";

export const TopTierDeliveries = () => {
    const { suppliers } = useData();

    const config = {
        title: {
            high: "Entregas Mais Caras",
            low: "Entregas Mais Baratas"
        },
        sortOptions: {
            high: { value: "most-expensive", label: "Mais caras" },
            low: { value: "least-expensive", label: "Mais baratas" }
        },
        icon: Package,
        valueFormatter: (value: string | number) => formatMoney(Number(value)),
        valueKey: "price",
        nameKey: "productName",
        customDisplayFormatter: (item: any) => ({
            leftDisplay: formatMoney(Number(item.price)),
            rightDisplay: `${item.amount}x ${item.productName}`
        })
    };

    return (
        <GenericTopTier
            fetchHighData={fetchMostExpensiveDeliveries}
            fetchLowData={fetchLeastExpensiveDeliveries}
            config={config}
            dependencies={[suppliers]}
        />
    );
};
