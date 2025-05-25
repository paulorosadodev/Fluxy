import { ReusableLineChart } from "../ReusableLineChart";
import { formatMoney } from "../../../utils";
import { useState } from "react";
import { fetchTotalPurchaseCostByMonth } from "../../../services/endpoints/purchase/dashboard";
import { fetchTotalSalaries } from "../../../services/endpoints/employee/dashboard";
import { fetchTotalDeliveryCostByMonth } from "../../../services/endpoints/supply/dashboard";
import { useTheme } from "styled-components";
import { SelectContainer, SelectDropdown } from "../ReusableLineChart/styles";

interface ChartDataPoint {
    value: number;
    date: string;
}

const METRIC_OPTIONS = [
    { value: "revenue", label: "Faturamento" },
    { value: "expenses", label: "Despesas" },
    { value: "profit", label: "Lucro" }
];

const PERIOD_OPTIONS = [
    { value: "3", label: "3 meses" },
    { value: "6", label: "6 meses" },
    { value: "12", label: "1 ano" },
    { value: "36", label: "3 anos" }
];

export function FinancialLineChart() {
    const theme = useTheme();
    const [selectedMetric, setSelectedMetric] = useState("revenue");
    const [selectedPeriod, setSelectedPeriod] = useState("3");

    const getLineColor = () => {
        switch (selectedMetric) {
        case "revenue":
            return theme["orange-500"];
        case "expenses":
            return theme["purple-500"];
        case "profit":
            return theme["orange-500"];
        default:
            return theme["teal-500"];
        }
    };

    const getMetricTitle = () => {
        const metricName = METRIC_OPTIONS.find(option => option.value === selectedMetric)?.label || "Faturamento";
        return metricName;
    };

    const generateMonthsArray = (months: number): { month: number; year: number; date: string }[] => {
        const result = [];
        const currentDate = new Date();
        
        for (let i = months - 1; i >= 0; i--) {
            const date = new Date(currentDate.getFullYear(), currentDate.getMonth() - i, 1);
            const month = date.getMonth() + 1;
            const year = date.getFullYear();
            const dateString = `${month.toString().padStart(2, "0")}/${year}`;
            
            result.push({ month, year, date: dateString });
        }
        
        return result;
    };

    const fetchRevenue = async (month: number, year: number): Promise<number> => {
        try {
            return await fetchTotalPurchaseCostByMonth(month, year);
        } catch (error) {
            console.error(`Erro ao buscar faturamento para ${month}/${year}:`, error);
            return 0;
        }
    };

    const fetchExpenses = async (month: number, year: number): Promise<number> => {
        try {
            const [salaries, deliveryCosts] = await Promise.all([
                fetchTotalSalaries(),
                fetchTotalDeliveryCostByMonth(month, year)
            ]);
            return salaries + deliveryCosts;
        } catch (error) {
            console.error(`Erro ao buscar despesas para ${month}/${year}:`, error);
            return 0;
        }
    };

    const fetchProfit = async (month: number, year: number): Promise<number> => {
        try {
            const [revenue, expenses] = await Promise.all([
                fetchRevenue(month, year),
                fetchExpenses(month, year)
            ]);
            return revenue - expenses;
        } catch (error) {
            console.error(`Erro ao buscar lucro para ${month}/${year}:`, error);
            return 0;
        }
    };

    const fetchData = async (): Promise<ChartDataPoint[]> => {
        const months = generateMonthsArray(parseInt(selectedPeriod));
        const chartData: ChartDataPoint[] = [];

        for (const { month, year, date } of months) {
            let value = 0;

            switch (selectedMetric) {
            case "revenue":
                value = await fetchRevenue(month, year);
                break;
            case "expenses":
                value = await fetchExpenses(month, year);
                break;
            case "profit":
                value = await fetchProfit(month, year);
                break;
            }

            chartData.push({
                value,
                date
            });
        }

        return chartData;
    };

    // Componente adicional para o segundo dropdown (será criado como extraHeaderContent)
    const extraHeaderContent = (
        <SelectContainer>
            <SelectDropdown
                value={selectedMetric}
                onChange={(e) => setSelectedMetric(e.target.value)}
            >
                {METRIC_OPTIONS.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.label}
                    </option>
                ))}
            </SelectDropdown>
            <SelectDropdown
                value={selectedPeriod}
                onChange={(e) => setSelectedPeriod(e.target.value)}
            >
                {PERIOD_OPTIONS.map((option) => (
                    <option key={option.value} value={option.value}>
                        {option.label}
                    </option>
                ))}
            </SelectDropdown>
        </SelectContainer>
    );

    return (
        <ReusableLineChart
            title={getMetricTitle()}
            fetchData={fetchData}
            options={[]} // Array vazio para não mostrar dropdown padrão
            valueFormatter={formatMoney}
            dateFormatter={(date) => date}
            lineColor={getLineColor()}
            tooltipLabel={METRIC_OPTIONS.find(option => option.value === selectedMetric)?.label}
            extraHeaderContent={extraHeaderContent}
            isCurrency={true}
        />
    );
}
