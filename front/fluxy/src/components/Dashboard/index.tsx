import { ReactNode, useEffect, useState } from "react";
import { DashboardsWrapper, DashboardWrapper } from "./styles";
import { fetchProductsTotalStock } from "../../services/endpoints/dashboard";
import { Card } from "../DashboardContents/Card";
import { Package } from "phosphor-react";

type DashboardProps = {
    dataDashboards: string[];
};

type DashboardRenderer = {
    fetch: () => Promise<any>;
    render: (data: any) => ReactNode;
};

const dashboardsController: Record<string, DashboardRenderer> = {
    productsTotalStock: {
        fetch: fetchProductsTotalStock,
        render: (data) => (
            <Card icon={Package} data={data} text="produtos em estoque" />
        ),
    },
};

export const Dashboard = ({ dataDashboards }: DashboardProps) => {

    const [dashboards, setDashboards] = useState<ReactNode[]>([]);

    const fetchData = async () => {
        const loadedDashboards: ReactNode[] = [];

        for (const key of dataDashboards) {
            const controller = dashboardsController[key];
            if (!controller) continue;

            try {
                const data = await controller.fetch();
                const component = controller.render(data);
                loadedDashboards.push(component);
            } catch (error: any) {
                console.error(`Erro ao buscar ${key}:`, error);
                const fallbackData = 0;
                const component = controller.render(fallbackData);
                loadedDashboards.push(component);
            }
        }

        setDashboards(loadedDashboards);
    };

    useEffect(() => {
        fetchData();
    }, [dataDashboards]);

    setInterval(() => {
        if (dashboards.length === 0) {
            fetchData();
        }
    }, 2000);

    return (
        <DashboardsWrapper>
            {dashboards.map((component, index) => (
                <DashboardWrapper key={index}>
                    {component}
                </DashboardWrapper>
            ))}
        </DashboardsWrapper>
    );

};
