import { MonthlyMetric } from "../MonthlyMetric";
import { fetchTotalSalaries } from "../../../services/endpoints/employee/dashboard";
import { fetchTotalDeliveryCostByMonth } from "../../../services/endpoints/supply/dashboard";
import { useTheme } from "styled-components";

type MonthlyExpensesProps = {
    delay?: number;
}

export const MonthlyExpenses = ({ delay = 0 }: MonthlyExpensesProps) => {
    const theme = useTheme();
    const fetchCurrentExpenses = async (): Promise<number> => {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1;
        const currentYear = currentDate.getFullYear();

        try {
            const [salaries, deliveryCosts] = await Promise.all([
                fetchTotalSalaries(),
                fetchTotalDeliveryCostByMonth(currentMonth, currentYear)
            ]);

            return salaries + deliveryCosts;
        } catch (error) {
            console.error("Erro ao buscar despesas atuais:", error);
            return 0;
        }
    };

    const fetchPreviousExpenses = async (): Promise<number> => {
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
            const [salaries, deliveryCosts] = await Promise.all([
                fetchTotalSalaries(),
                fetchTotalDeliveryCostByMonth(previousMonth, previousYear)
            ]);

            return salaries + deliveryCosts;
        } catch (error) {
            console.error("Erro ao buscar despesas anteriores:", error);
            return 0;
        }
    };

    return (
        <MonthlyMetric
            title="Despesas Mensais"
            fetchCurrentData={fetchCurrentExpenses}
            fetchPreviousData={fetchPreviousExpenses}
            delay={delay}
            color={theme["purple-500"]}
        />
    );
};
