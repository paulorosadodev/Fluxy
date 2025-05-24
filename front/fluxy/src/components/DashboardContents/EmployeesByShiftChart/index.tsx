import { ReusablePieChart } from "../PieChart";

type EmployeesByShiftChartProps = {
    data?: Array<{
        shift: string;
        employeeCount: number;
    }>;
};

export const EmployeesByShiftChart = ({ data }: EmployeesByShiftChartProps) => {
    const shiftLabels: Record<string, string> = {
        "morning": "Manhã",
        "afternoon": "Tarde", 
        "night": "Noite",
        "full-time": "Integral",
        "flexible": "Turno Flexível"
    };

    const formatShiftLabel = (shift: string) => {
        return shiftLabels[shift] || shift;
    };

    return (
        <ReusablePieChart
            data={data}
            title="Funcionários por Turno"
            dataKey="quantity"
            nameKey="shift"
            labelFormatter={formatShiftLabel}
        />
    );
};
