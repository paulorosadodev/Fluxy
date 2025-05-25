import { useEffect, useState } from "react";
import { fetchTotalPurchaseCostByMonth } from "../../../services/endpoints/purchase/dashboard";
import { 
    MonthlyRevenueWrapper, 
    RevenueHeader, 
    RevenueTitle, 
    RevenueAmount, 
    ComparisonWrapper, 
    ComparisonIcon, 
    ComparisonText,
    LoadingWrapper 
} from "./styles";
import { TrendUp, TrendDown, Minus } from "phosphor-react";
import { useData } from "../../../hooks/useData";

type MonthlyRevenueProps = {
    delay?: number;
}

export const MonthlyRevenue = ({ delay = 0 }: MonthlyRevenueProps) => {
    const [currentRevenue, setCurrentRevenue] = useState<number>(0);
    const [loading, setLoading] = useState(true);
    const [percentageChange, setPercentageChange] = useState<number>(0);
    const [changeType, setChangeType] = useState<"increase" | "decrease" | "same">("same");

    useData();

    const fetchRevenueData = async () => {
        try {
            setLoading(true);
            
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

            const [currentData, previousData] = await Promise.all([
                fetchTotalPurchaseCostByMonth(currentMonth, currentYear),
                fetchTotalPurchaseCostByMonth(previousMonth, previousYear)
            ]);

            const currentAmount = currentData;
            const previousAmount = previousData;

            setCurrentRevenue(currentAmount);

            if (previousAmount === 0) {
                if (currentAmount > 0) {
                    setPercentageChange(100);
                    setChangeType("increase");
                } else {
                    setPercentageChange(0);
                    setChangeType("same");
                }
            } else {
                const change = ((currentAmount - previousAmount) / previousAmount) * 100;
                setPercentageChange(Math.abs(change));
                
                if (change > 0) {
                    setChangeType("increase");
                } else if (change < 0) {
                    setChangeType("decrease");
                } else {
                    setChangeType("same");
                }
            }
        } catch (error) {
            console.error("Erro ao buscar dados de faturamento:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRevenueData();
    }, []);

    const formatCurrency = (value: number) => {
        return new Intl.NumberFormat("pt-BR", {
            style: "currency",
            currency: "BRL"
        }).format(value);
    };

    const getCurrentMonthName = () => {
        const currentDate = new Date();
        return currentDate.toLocaleDateString("pt-BR", { month: "long" });
    };

    const getComparisonIcon = () => {
        switch (changeType) {
        case "increase":
            return <TrendUp size={20} weight="fill" />;
        case "decrease":
            return <TrendDown size={20} weight="fill" />;
        default:
            return <Minus size={20} weight="bold" />;
        }
    };

    const getComparisonText = () => {
        if (changeType === "same") {
            return "0.00%";
        }
        const sign = changeType === "increase" ? "+" : "-";
        return `${sign}${percentageChange.toFixed(2)}%`;
    };

    if (loading) {
        return (
            <MonthlyRevenueWrapper delay={delay}>
                <LoadingWrapper>
                    Carregando...
                </LoadingWrapper>
            </MonthlyRevenueWrapper>
        );
    }

    return (
        <MonthlyRevenueWrapper delay={delay}>
            <ComparisonWrapper changeType={changeType}>
                <ComparisonIcon changeType={changeType}>
                    {getComparisonIcon()}
                </ComparisonIcon>
                <ComparisonText changeType={changeType}>
                    {getComparisonText()}
                </ComparisonText>
            </ComparisonWrapper>
            <RevenueHeader>
                <RevenueTitle>Faturamento em {getCurrentMonthName()}</RevenueTitle>
                <RevenueAmount>{formatCurrency(currentRevenue)}</RevenueAmount>
            </RevenueHeader>
        </MonthlyRevenueWrapper>
    );
};
