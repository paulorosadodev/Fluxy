import { useEffect, useState } from "react";
import { fetchProducts } from "../../../services/endpoints/product";
import { fetchProductPriceHistory } from "../../../services/endpoints/product/dashboard";
import { useData } from "../../../hooks/useData";
import { ReusableLineChart } from "../ReusableLineChart";

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

    useEffect(() => {
        if (contextProducts && contextProducts.length > 0) {
            const mappedProducts = contextProducts
                .filter(product => product.id !== undefined) 
                .map(product => ({
                    id: product.id!,
                    name: product.name
                }));
            setProducts(mappedProducts);
        } else {
            fetchProducts().then((data) => {
                const mappedProducts = data.map((product: any) => ({
                    id: product.id,
                    name: product.name
                }));
                setProducts(mappedProducts);
            });
        }
    }, [contextProducts]);

    const fetchPriceHistory = async (productId: string) => {
        const data = await fetchProductPriceHistory(productId);
        return data.map((item: any) => ({
            value: item.price,
            date: item.date
        }));
    };

    const options = products.map(product => ({
        value: String(product.id),
        label: product.name
    }));

    if (products.length === 0) {
        return null;
    }

    return (
        <ReusableLineChart 
            title="Histórico de Preço"
            height={height}
            fetchData={fetchPriceHistory}
            options={options}
            valueKey="value"
            dateKey="date"
            lineColor="#8884d8"
            valueFormatter={(value: number) => `R$ ${value.toFixed(2)}`}
            isCurrency={true}
        />
    );
}
