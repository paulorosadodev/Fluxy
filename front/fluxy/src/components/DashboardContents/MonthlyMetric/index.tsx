import { useEffect, useState } from "react";
import { 
    MonthlyMetricWrapper, 
    MetricHeader, 
    MetricTitle, 
    MetricAmount, 
    ComparisonWrapper, 
    ComparisonIcon, 
    ComparisonText,
    LoadingWrapper 
} from "./styles";
import { TrendUp, TrendDown, Minus } from "phosphor-react";
import { useData } from "../../../hooks/useData";
import { useTheme } from "styled-components";

type MonthlyMetricProps = {
    title: string;
    fetchCurrentData: () => Promise<number>;
    fetchPreviousData: () => Promise<number>;
    delay?: number;
    color?: string;
    isProfit?: boolean; // Para métricas onde menos negativo = melhor
}

export const MonthlyMetric = ({ 
    title, 
    fetchCurrentData, 
    fetchPreviousData, 
    delay = 0,
    color,
    isProfit = false
}: MonthlyMetricProps) => {
    const theme = useTheme();
    const defaultColor = color || theme["green-500"];
    const [currentValue, setCurrentValue] = useState<number>(0);
    const [loading, setLoading] = useState(true);
    const [percentageChange, setPercentageChange] = useState<number>(0);
    const [changeType, setChangeType] = useState<"increase" | "decrease" | "same">("same");

    useData();

    const fetchMetricData = async () => {
        try {
            setLoading(true);
            
            const [currentData, previousData] = await Promise.all([
                fetchCurrentData(),
                fetchPreviousData()
            ]);

            const currentAmount = currentData;
            const previousAmount = previousData;

            setCurrentValue(currentAmount);

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
                
                if (isProfit && currentAmount < 0 && previousAmount < 0) {
                    if (currentAmount > previousAmount) {
                        setChangeType("increase"); 
                    } else if (currentAmount < previousAmount) {
                        setChangeType("decrease"); 
                    } else {
                        setChangeType("same");
                    }
                } else {
                    if (change > 0) {
                        setChangeType("increase");
                    } else if (change < 0) {
                        setChangeType("decrease");
                    } else {
                        setChangeType("same");
                    }
                }
            }
        } catch (error) {
            console.error(`Erro ao buscar dados de ${title}:`, error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchMetricData();
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
            <MonthlyMetricWrapper delay={delay} color={defaultColor}>
                <LoadingWrapper>
                    Carregando...
                </LoadingWrapper>
            </MonthlyMetricWrapper>
        );
    }

    return (
        <MonthlyMetricWrapper delay={delay} color={defaultColor}>
            <ComparisonWrapper changeType={changeType}>
                <ComparisonIcon changeType={changeType}>
                    {getComparisonIcon()}
                </ComparisonIcon>
                <ComparisonText changeType={changeType}>
                    {getComparisonText()}
                </ComparisonText>
            </ComparisonWrapper>
            <MetricHeader>
                <MetricTitle>{title} em {getCurrentMonthName()}</MetricTitle>
                <MetricAmount>{formatCurrency(currentValue)}</MetricAmount>
            </MetricHeader>
        </MonthlyMetricWrapper>
    );
};
