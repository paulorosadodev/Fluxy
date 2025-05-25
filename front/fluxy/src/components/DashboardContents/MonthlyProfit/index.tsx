import { MonthlyMetric } from "../MonthlyMetric";
import { fetchTotalPurchaseCostByMonth } from "../../../services/endpoints/purchase/dashboard";
import { fetchTotalSalaries } from "../../../services/endpoints/employee/dashboard";
import { fetchTotalDeliveryCostByMonth } from "../../../services/endpoints/supply/dashboard";
import { useTheme } from "styled-components";
import { useState } from "react";

type MonthlyProfitProps = {
    delay?: number;
}

export const MonthlyProfit = ({ delay = 0 }: MonthlyProfitProps) => {
    const theme = useTheme();
    const [borderColor, setBorderColor] = useState(theme["gray-200"]);
    const fetchCurrentProfit = async (): Promise<number> => {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1;
        const currentYear = currentDate.getFullYear();

        try {
            const [revenue, salaries, deliveryCosts] = await Promise.all([
                fetchTotalPurchaseCostByMonth(currentMonth, currentYear),
                fetchTotalSalaries(),
                fetchTotalDeliveryCostByMonth(currentMonth, currentYear)
            ]);

            const totalExpenses = salaries + deliveryCosts;
            const profit = revenue - totalExpenses;
            
            // Atualizar cor da borda baseada no lucro
            if (profit === 0) {
                setBorderColor(theme["gray-200"]);
            } else if (profit > 0) {
                setBorderColor(theme["teal-500"]);
            } else {
                setBorderColor(theme["red-500"]);
            }
            
            return profit;
        } catch (error) {
            console.error("Erro ao buscar lucro atual:", error);
            return 0;
        }
    };

    const fetchPreviousProfit = async (): Promise<number> => {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1;
        const currentYear = currentDate.getFullYear();

        let previousMonth = currentMonth - 1;
        let previousYear = currentYear;
        
        if (previousMonth === 0) {
            previousMonth = 12;
            previousYear = currentYear - 1;
        }

        try {
            const [revenue, salaries, deliveryCosts] = await Promise.all([
                fetchTotalPurchaseCostByMonth(previousMonth, previousYear),
                fetchTotalSalaries(),
                fetchTotalDeliveryCostByMonth(previousMonth, previousYear)
            ]);

            const totalExpenses = salaries + deliveryCosts;
            const profit = revenue - totalExpenses;
            
            return profit;
        } catch (error) {
            console.error("Erro ao buscar lucro anterior:", error);
            return 0;
        }
    };

    return (
        <MonthlyMetric
            title="Lucro Mensal"
            fetchCurrentData={fetchCurrentProfit}
            fetchPreviousData={fetchPreviousProfit}
            delay={delay}
            color={borderColor}
            isProfit={true}
        />
    );
};
