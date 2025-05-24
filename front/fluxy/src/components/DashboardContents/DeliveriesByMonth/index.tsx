import { ReusableLineChart } from "../ReusableLineChart";
import { fetchDeliveriesByMonth } from "../../../services/endpoints/supply/dashboard";

interface DeliveriesByMonthProps {
    height?: number;
}

export function DeliveriesByMonth({ height = 320 }: DeliveriesByMonthProps) {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1;

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
            fetchDeliveriesByMonth(month, year)
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

    const formatMonth = (dateString: string) => {
        const [year, month] = dateString.split("-").map(Number);
        const correctedDate = new Date(year, month - 1); 
        return new Intl.DateTimeFormat("pt-BR", { month: "short", year: "numeric" }).format(correctedDate);
    };

    return (
        <ReusableLineChart 
            title="Entregas por Período"
            height={height}
            fetchData={fetchDeliveriesData}
            options={options}
            valueKey="value"
            dateKey="date"
            lineColor="#EE6C4D"
            valueFormatter={(value: number) => `${value}`}
            dateFormatter={formatMonth}
        />
    );
}
