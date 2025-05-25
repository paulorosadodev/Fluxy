import { useEffect, useState } from "react";
import { ReusablePieChart } from "../PieChart";
import { fetchPaymentTypeCounts } from "../../../services/endpoints/purchase/dashboard";
import { useData } from "../../../hooks/useData";

type PaymentTypeData = {
    type: string;
    quantity: number;
};

export const PaymentTypesChart = () => {
    const { purchases } = useData();
    const [data, setData] = useState<PaymentTypeData[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetchPaymentTypeCounts();
                setData(response);
            } catch (error) {
                console.error("Erro ao buscar dados de tipos de pagamento:", error);
                setData([]);
            }
        };

        fetchData();
    }, [purchases]); 

    const formatPaymentType = (type: string) => {
        const typeMapping: { [key: string]: string } = {
            "Crédito": "Cartão de Crédito",
            "Débito": "Cartão de Débito",
            "Pix": "PIX",
            "Dinheiro": "Dinheiro",
            "Vale Alimentação": "Vale Alimentação",
            "Outro": "Outros"
        };
        return typeMapping[type] || type;
    };

    // Não renderizar se não há dados ou se todos os valores são zero
    const hasValidData = data && data.length > 0 && data.some(item => item.quantity > 0);
    
    if (!hasValidData) {
        return null;
    }

    return (
        <ReusablePieChart<PaymentTypeData>
            data={data}
            title="Compras por Tipo de Pagamento"
            dataKey="quantity"
            nameKey="type"
            labelFormatter={formatPaymentType}
        />
    );
};
