import { useEffect, useState } from "react";
import { ProductPriceHistory } from ".";
import { fetchProducts } from "../../../services/endpoints/product";

import { ContainerWithSpinner, SelectContainer, SelectDropdown, LoadingOverlay, Spinner } from "./styles";
import { fetchProductPriceHistory } from "../../../services/endpoints/product/dashboard";
import { useData } from "../../../hooks/useData";

interface Product {
    id: string | number;
    name: string;
}

export function ProductPriceHistoryWithSelect() {
    const { products: contextProducts } = useData(); 
    const [products, setProducts] = useState<Product[]>([]);
    const [selectedId, setSelectedId] = useState<string>("");
    const [history, setHistory] = useState<{ price: number; date: string }[]>([]);
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
            <ProductPriceHistory 
                data={history} 
                selectInput={selectInput} 
                height={320}
                loading={false} 
            />
        </ContainerWithSpinner>
    );
}
