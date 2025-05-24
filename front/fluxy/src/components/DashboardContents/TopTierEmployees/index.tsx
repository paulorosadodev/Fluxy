import { GenericTopTier } from "../GenericTopTier";
import { fetchEmployeesMostPurchases, fetchEmployeesLeastPurchases } from "../../../services/endpoints/employee/dashboard";
import { ShoppingCart } from "phosphor-react";
import { useData } from "../../../hooks/useData";

export const TopTierEmployees = () => {
    const { employees } = useData();

    const config = {
        title: {
            high: "Funcionários que Venderam Mais",
            low: "Funcionários que Venderam Menos"
        },
        sortOptions: {
            high: { value: "most-purchases", label: "Mais compras" },
            low: { value: "least-purchases", label: "Menos compras" }
        },
        icon: ShoppingCart,
        valueFormatter: (value: string | number) => `${value} ${Number(value) === 1 ? "compra" : "compras"}`,
        valueKey: "totalPurchases",
        nameKey: "name"
    };

    return (
        <GenericTopTier
            fetchHighData={fetchEmployeesMostPurchases}
            fetchLowData={fetchEmployeesLeastPurchases}
            config={config}
            dependencies={[employees]}
        />
    );
};
