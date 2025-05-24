import { ReusablePieChart } from "../PieChart";

type EmployeesByRoleChartProps = {
    data?: Array<{
        role: string;
        employeeCount: number;
    }>;
};

export const EmployeesByRoleChart = ({ data }: EmployeesByRoleChartProps) => {
    const roleLabels: Record<string, string> = {
        "manager": "Gerente",
        "cashier": "Caixa",
        "sales": "Vendedor", 
        "stock": "Estoquista",
        "supervisor": "Supervisor",
        "assistant": "Assistente",
        "repositor": "Repositor",
        "butcher": "Açougueiro",
        "baker": "Padeiro",
        "security": "Vigilante",
        "sector_manager": "Gerente de Setor",
        "store_manager": "Gerente de Loja",
        "auxiliary": "Auxiliar",
        "intern": "Estagiário"
    };

    const formatRoleLabel = (role: string) => {
        return roleLabels[role] || role;
    };

    return (
        <ReusablePieChart
            data={data}
            title="Funcionários por Cargo"
            dataKey="quantity"
            nameKey="role"
            labelFormatter={formatRoleLabel}
        />
    );
};
