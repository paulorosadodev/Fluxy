import { GenericTopTier } from "../GenericTopTier";
import { fetchLeastExpensiveProducts, fetchMostExpensiveProducts } from "../../../services/endpoints/product/dashboard";
import { formatMoney } from "../../../utils";
import { CurrencyCircleDollar } from "phosphor-react";
import { useData } from "../../../hooks/useData";

export const TopTierProducts = () => {
    const { products } = useData();

    const config = {
        title: {
            high: "Maiores preços",
            low: "Menores preços"
        },
        sortOptions: {
            high: { value: "most-expensive", label: "Maior preço" },
            low: { value: "least-expensive", label: "Menor preço" }
        },
        icon: CurrencyCircleDollar,
        valueFormatter: (value: string | number) => formatMoney(Number(value)),
        valueKey: "price",
        nameKey: "name"
    };

    return (
        <GenericTopTier
            fetchHighData={fetchMostExpensiveProducts}
            fetchLowData={fetchLeastExpensiveProducts}
            config={config}
            dependencies={[products]}
        />
    );
};