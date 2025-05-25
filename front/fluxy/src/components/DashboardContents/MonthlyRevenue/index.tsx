import { MonthlyMetric } from "../MonthlyMetric";
import { fetchTotalPurchaseCostByMonth } from "../../../services/endpoints/purchase/dashboard";
import { useTheme } from "styled-components";

type MonthlyRevenueProps = {
    delay?: number;
}

export const MonthlyRevenue = ({ delay = 0 }: MonthlyRevenueProps) => {
    const theme = useTheme();
    const fetchCurrentRevenue = async (): Promise<number> => {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1;
        const currentYear = currentDate.getFullYear();

        try {
            return await fetchTotalPurchaseCostByMonth(currentMonth, currentYear);
        } catch (error) {
            console.error("Erro ao buscar receita atual:", error);
            return 0;
        }
    };

    const fetchPreviousRevenue = async (): Promise<number> => {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1;
        const currentYear = currentDate.getFullYear();

        // Calcular mês anterior
        let previousMonth = currentMonth - 1;
        let previousYear = currentYear;
        
        if (previousMonth === 0) {
            previousMonth = 12;
            previousYear = currentYear - 1;
        }

        try {
            return await fetchTotalPurchaseCostByMonth(previousMonth, previousYear);
        } catch (error) {
            console.error("Erro ao buscar receita anterior:", error);
            return 0;
        }
    };

    return (
        <MonthlyMetric
            title="Faturamento Mensal"
            fetchCurrentData={fetchCurrentRevenue}
            fetchPreviousData={fetchPreviousRevenue}
            delay={delay}
            color={theme["orange-300"]}
        />
    );
};
