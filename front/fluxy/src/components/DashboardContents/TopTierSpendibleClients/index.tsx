import { GenericTopTier } from "../GenericTopTier";
import { fetchTopTierSpendibleClients, fetchLowTierSpendibleClients } from "../../../services/endpoints/customer/dashboard";
import { CurrencyDollar } from "phosphor-react";
import { useData } from "../../../hooks/useData";
import { formatMoney } from "../../../utils";

export const TopTierSpendibleClients = () => {

    const { legalEntityCustomers, naturalPersonCustomers } = useData();

    const customers = [...legalEntityCustomers, ...naturalPersonCustomers];

    const config = {
        title: {
            high: "Clientes que Mais Gastaram",
            low: "Clientes que Menos Gastaram"
        },
        sortOptions: {
            high: { value: "top-tier-spending", label: "Mais gastaram" },
            low: { value: "low-tier-spending", label: "Menos gastaram" }
        },
        icon: CurrencyDollar,
        valueFormatter: (value: string | number) => formatMoney(Number(value)),
        valueKey: "totalSpending",
        nameKey: "name"
    };

    return (
        <GenericTopTier
            fetchHighData={fetchTopTierSpendibleClients}
            fetchLowData={fetchLowTierSpendibleClients}
            config={config}
            dependencies={[customers]}
        />
    );
};
