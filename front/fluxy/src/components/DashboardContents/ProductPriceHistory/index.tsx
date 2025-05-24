import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";
import { 
    ChartWrapper, 
    Title, 
    HeaderContainer, 
    LoadingOverlay, 
    StyledResponsiveContainer,
    ContainerWithSpinner, 
    SelectContainer, 
    SelectDropdown, 
    Spinner 
} from "./styles";
import { formatDate } from "../../../utils";
import { useEffect, useState } from "react";
import { fetchProducts } from "../../../services/endpoints/product";
import { fetchProductPriceHistory } from "../../../services/endpoints/product/dashboard";
import { useData } from "../../../hooks/useData";

interface PriceHistoryPoint {
    price: number;
    date: string;
}

interface Product {
    id: string | number;
    name: string;
}

interface PriceHistoryProps {
    height?: number;
}

export function ProductPriceHistory({ height = 320 }: PriceHistoryProps) {
    const { products: contextProducts } = useData(); 
    const [products, setProducts] = useState<Product[]>([]);
    const [selectedId, setSelectedId] = useState<string>("");
    const [history, setHistory] = useState<PriceHistoryPoint[]>([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (contextProducts && contextProducts.length > 0) {
            const mappedProducts = contextProducts
                .filter(product => product.id !== undefined) 
                .map(product => ({
                    id: product.id!,
                    name: product.name
                }));
            setProducts(mappedProducts);

            if (!selectedId && mappedProducts.length > 0) {
                setSelectedId(String(mappedProducts[0].id));
            }
        } else {
            fetchProducts().then((data) => {
                const mappedProducts = data.map((product: any) => ({
                    id: product.id,
                    name: product.name
                }));
                setProducts(mappedProducts);
                if (!selectedId && mappedProducts.length > 0) {
                    setSelectedId(String(mappedProducts[0].id));
                }
            });
        }
    }, [contextProducts, selectedId]);

    useEffect(() => {
        if (!selectedId) return;
        setLoading(true);
        fetchProductPriceHistory(selectedId).then((data) => {
            setHistory(data);
            setLoading(false);
        });
    }, [selectedId, contextProducts]); 

    const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        e.preventDefault();
        setSelectedId(e.target.value);
        e.currentTarget.blur();
    };

    const selectInput = (
        <SelectContainer>
            <SelectDropdown
                id="product-select"
                value={selectedId}
                onChange={handleSelectChange}
            >
                {products.map(product => (
                    <option key={product.id} value={product.id}>{product.name}</option>
                ))}
            </SelectDropdown>
        </SelectContainer>
    );

    return (
        <ContainerWithSpinner $loading={loading}>
            {loading && (
                <LoadingOverlay>
                    <Spinner />
                    <span>Carregando...</span>
                </LoadingOverlay>
            )}
            <ChartWrapper>
                <HeaderContainer>
                    <Title>Histórico de Preço</Title>
                    {selectInput}
                </HeaderContainer>
                <StyledResponsiveContainer width="100%" height={height} $loading={loading}>
                    <LineChart 
                        data={history} 
                        margin={{ top: 20, right: 0, left: 0, bottom: 20 }}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis 
                            dataKey="date" 
                            tickFormatter={date => formatDate(date)} 
                            height={50}
                            tick={{ fontSize: 12 }}
                            padding={{ left: 10, right: 10 }}
                        />
                        <YAxis 
                            dataKey="price" 
                            tickFormatter={price => `R$ ${price.toFixed(2)}`}
                            width={70}
                            tick={{ fontSize: 12 }}
                            domain={["auto", "dataMax + 5"]}
                            allowDataOverflow={false}
                        />
                        <Tooltip 
                            formatter={(value: number) => [`R$ ${value.toFixed(2)}`, "Preço"]} 
                            labelFormatter={label => `Data: ${formatDate(label)}`} 
                        />
                        <Line 
                            type="monotone" 
                            dataKey="price" 
                            stroke="#8884d8" 
                            strokeWidth={2} 
                            dot={{ r: 3 }} 
                            activeDot={{ r: 6 }} 
                        />
                    </LineChart>
                </StyledResponsiveContainer>
            </ChartWrapper>
        </ContainerWithSpinner>
    );
}
