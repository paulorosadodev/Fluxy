import { ReusableLineChart } from "../ReusableLineChart";
import { ExtraSelect } from "../ReusableLineChart/styles";
import { useState } from "react";
import { fetchDeliveriesByMonth, fetchTotalDeliveryCostByMonth } from "../../../services/endpoints/supply/dashboard";
import { formatMoney } from "../../../utils";

interface DeliveriesByMonthProps {
    height?: number;
}

export function DeliveriesByMonth({ height = 320 }: DeliveriesByMonthProps) {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1;
    const [chartType, setChartType] = useState("quantity"); 

    const fetchDeliveriesData = async (period: string) => {
        const monthsToFetch = [];
        const startMonth = { month: currentMonth, year: currentYear };
        let monthsCount = 0;

        switch (period) {
        case "3-months":
            monthsCount = 3;
            break;
        case "6-months":
            monthsCount = 6;
            break;
        case "1-year":
            monthsCount = 12;
            break;
        case "2-years":
            monthsCount = 24;
            break;
        case "3-years":
            monthsCount = 36;
            break;
        default:
            monthsCount = 3;
        }

        for (let i = 0; i < monthsCount; i++) {
            let targetMonth = startMonth.month - i; 
            let targetYear = startMonth.year;

            while (targetMonth <= 0) {
                targetMonth += 12;
                targetYear -= 1;
            }

            monthsToFetch.push({ month: targetMonth, year: targetYear });
        }

        const promises = monthsToFetch.map(({ month, year }) => 
            (chartType === "quantity" ? fetchDeliveriesByMonth(month, year) : fetchTotalDeliveryCostByMonth(month, year))
                .then(count => ({
                    value: count,
                    date: `${year}-${month.toString().padStart(2, "0")}-01` 
                }))
                .catch(() => ({
                    value: 0,
                    date: `${year}-${month.toString().padStart(2, "0")}-01`
                }))
        );

        const results = await Promise.all(promises);

        return results.sort((a, b) => a.date.localeCompare(b.date));
    };

    const options = [
        { value: "3-months", label: "3 meses" },
        { value: "6-months", label: "6 meses" },
        { value: "1-year", label: "1 ano" },
        { value: "2-years", label: "2 anos" },
        { value: "3-years", label: "3 anos" }
    ];

    const chartTypeOptions = [
        { value: "quantity", label: "Quantidade de Entregas" },
        { value: "totalCost", label: "Valor Total de Entregas" }
    ];

    const formatMonth = (dateString: string) => {
        const [year, month] = dateString.split("-").map(Number);
        const correctedDate = new Date(year, month - 1); 
        return new Intl.DateTimeFormat("pt-BR", { month: "short", year: "numeric" }).format(correctedDate);
    };

    const handleChartTypeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        e.preventDefault();
        setChartType(e.target.value);
    };

    // Configurações dinâmicas baseadas no tipo de chart
    const chartConfig = {
        quantity: {
            title: "Quantidade de Entregas por Período",
            valueFormatter: (value: number) => `${value}`,
            tooltipLabel: "Quantidade de Entregas"
        },
        totalCost: {
            title: "Valor Total de Entregas por Período",
            valueFormatter: (value: number) => formatMoney(value),
            tooltipLabel: "Valor Total"
        }
    };

    const currentConfig = chartConfig[chartType as keyof typeof chartConfig];

    return (
        <ReusableLineChart 
            title={currentConfig.title}
            height={height}
            fetchData={fetchDeliveriesData}
            options={options}
            valueKey="value"
            dateKey="date"
            lineColor="#EE6C4D"
            valueFormatter={currentConfig.valueFormatter}
            dateFormatter={formatMonth}
            tooltipLabel={currentConfig.tooltipLabel}
            extraHeaderContent={
                <ExtraSelect value={chartType} onChange={handleChartTypeChange}>
                    {chartTypeOptions.map(option => (
                        <option key={option.value} value={option.value}>{option.label}</option>
                    ))}
                </ExtraSelect>
            }
        />
    );
}
