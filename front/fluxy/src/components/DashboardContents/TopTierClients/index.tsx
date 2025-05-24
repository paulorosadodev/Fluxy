import { GenericTopTier } from "../GenericTopTier";
import { fetchTopTierClients, fetchLowTierClients } from "../../../services/endpoints/customer/dashboard";
import { Users } from "phosphor-react";
import { useData } from "../../../hooks/useData";

export const TopTierClients = () => {

    const { legalEntityCustomers, naturalPersonCustomers } = useData();

    const customers = [...legalEntityCustomers, ...naturalPersonCustomers];

    const config = {
        title: {
            high: "Clientes Top",
            low: "Clientes com Menos Compras"
        },
        sortOptions: {
            high: { value: "top-tier", label: "Mais compras" },
            low: { value: "low-tier", label: "Menos compras" }
        },
        icon: Users,
        valueFormatter: (value: string | number) => `${value} compras`,
        valueKey: "totalPurchases",
        nameKey: "name"
    };

    return (
        <GenericTopTier
            fetchHighData={fetchTopTierClients}
            fetchLowData={fetchLowTierClients}
            config={config}
            dependencies={[customers]}
        />
    );
};
