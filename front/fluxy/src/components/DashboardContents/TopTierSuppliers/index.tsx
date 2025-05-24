import { GenericTopTier } from "../GenericTopTier";
import { fetchSuppliersMostDeliveries, fetchSuppliersLeastDeliveries } from "../../../services/endpoints/supplier/dashboard";
import { Truck } from "phosphor-react";
import { useData } from "../../../hooks/useData";

export const TopTierSuppliers = () => {
    const { suppliers } = useData();

    const config = {
        title: {
            high: "Fornecedores com Mais Entregas",
            low: "Fornecedores com Menos Entregas"
        },
        sortOptions: {
            high: { value: "most-deliveries", label: "Mais entregas" },
            low: { value: "least-deliveries", label: "Menos entregas" }
        },
        icon: Truck,
        valueFormatter: (value: string | number) => `${value} ${Number(value) === 1 ? "entrega" : "entregas"}`,
        valueKey: "totalEntregas",
        nameKey: "name"
    };

    return (
        <GenericTopTier
            fetchHighData={fetchSuppliersMostDeliveries}
            fetchLowData={fetchSuppliersLeastDeliveries}
            config={config}
            dependencies={[suppliers]}
        />
    );
};
